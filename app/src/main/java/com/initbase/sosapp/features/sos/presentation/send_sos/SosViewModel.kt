package com.initbase.sosapp.features.sos.presentation.send_sos

import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.initbase.sosapp.SosContacts
import com.initbase.sosapp.core.data.CallState
import com.initbase.sosapp.core.data.source.RemoteSource
import com.initbase.sosapp.features.sos.data.repository.SosRepository
import com.initbase.sosapp.features.sos.data.repository.SosRepositoryImp
import com.initbase.sosapp.features.sos.data.source.local.SosLocalDataSource
import com.initbase.sosapp.features.sos.data.source.remote.SosRemoteDataSource
import com.initbase.sosapp.features.sos.domain.use_cases.Location
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosBody
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
class SosViewModel(private val repository: SosRepository) : ViewModel() {
    private val _screenState = MutableStateFlow(SendSosScreenState())
    val screenState: StateFlow<SendSosScreenState> = _screenState

    private val _contactTextFieldState = MutableStateFlow("")
    val contactTextFieldState: StateFlow<String> = _contactTextFieldState

    private fun updateScreenState(value: SendSosScreenState) {
        _screenState.update { value }
    }

    init {
        onEvent(SendSosEvent.GetContacts)
    }

    fun onEvent(event: SendSosEvent) {
        when (event) {
            is SendSosEvent.AddNumber ->
                viewModelScope.launch {
                    repository.addContact(event.number)
                    onEvent(SendSosEvent.ContactTextFieldChanged(""))
                }
            SendSosEvent.SendSos -> handleSendSosEvent()
            is SendSosEvent.UpdateContactsSheetState -> {
                updateScreenState(_screenState.value.copy(showBottomSheet = event.showBottomSheet))
            }
            is SendSosEvent.ContactTextFieldChanged -> {
                _contactTextFieldState.update { event.text }
            }
            SendSosEvent.GetContacts ->
                viewModelScope.launch {
                    repository.getContacts().collect {
                        updateScreenState(_screenState.value.copy(contacts = it))
                    }
                }
            is SendSosEvent.RemoveNumber -> viewModelScope.launch { repository.removeContact(event.index) }
            is SendSosEvent.CheckPermissionStatus -> {
                when (val status = event.permissionState.status) {
                    is PermissionStatus.Denied -> updateScreenState(_screenState.value.copy(permissionStatus = status))
                    PermissionStatus.Granted -> updateScreenState(_screenState.value.copy(permissionStatus = status))
                }
                viewModelScope.launch {
                    delay(100)
                    updateScreenState(_screenState.value.copy(permissionStatus = null))
                }
            }
            is SendSosEvent.CaptureImage -> {
                handleCaptureImageEvent(event)
            }
            SendSosEvent.LaunchCamera -> updateScreenState(_screenState.value.copy(showCamera = true))
        }
    }

    private fun handleCaptureImageEvent(event: SendSosEvent.CaptureImage) {
        val photoFile = event.outputFile
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        event.imageCapture.takePicture(outputOptions, event.executor, object : ImageCapture.OnImageSavedCallback {
            override fun onError(exception: ImageCaptureException) =
                updateScreenState(_screenState.value.copy(imageCaptureError = exception))

            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) =
                updateScreenState(_screenState.value.copy(capturedImage = Uri.fromFile(photoFile), showCamera = false))
        })
    }

    private fun handleSendSosEvent() {
        viewModelScope.launch {
            repository.sendSOS(SendSosBody(phoneNumbers = screenState.value.contacts,
                image = "",
                Location(longitude = "", latitude = ""))).collect {
                updateScreenState(_screenState.value.copy(sendSosCallState = it))
            }
        }
    }
}

class SosViewModelFactory(private val dataStore: DataStore<SosContacts>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SosViewModel(repository = SosRepositoryImp(
            remoteDataSource = SosRemoteDataSource(RemoteSource.sosService),
            localDataSource = SosLocalDataSource(dataStore))) as T
    }
}