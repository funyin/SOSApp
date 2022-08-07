package com.initbase.sosapp.features.sos.presentation.send_sos

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.initbase.sosapp.SosContacts
import com.initbase.sosapp.core.data.source.RemoteSource
import com.initbase.sosapp.features.sos.data.repository.SosRepository
import com.initbase.sosapp.features.sos.data.repository.SosRepositoryImp
import com.initbase.sosapp.features.sos.data.source.local.SosLocalDataSource
import com.initbase.sosapp.features.sos.data.source.remote.SosRemoteDataSource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SosViewModel(private val repository: SosRepository):ViewModel() {
    private val _screenState = MutableStateFlow(SendSosScreenState())
    val screenState: StateFlow<SendSosScreenState> = _screenState

    private val _contactTextFieldState = MutableStateFlow("")
    val contactTextFieldState: StateFlow<String> = _contactTextFieldState

    private fun updateScreenState(value:SendSosScreenState){
        _screenState.update { value }
    }

    init {
        onEvent(SendSosEvent.GetContacts)
    }

    fun onEvent(event:SendSosEvent){
        when(event){
            is SendSosEvent.AddNumber -> {
                viewModelScope.launch {
                    repository.addContact(event.number)
                    onEvent(SendSosEvent.ContactTextFieldChanged(""))
                }
            }
            is SendSosEvent.SendSos -> handleSendSosEvent(event)
            is SendSosEvent.UpdateContactsSheetState -> {
                updateScreenState(_screenState.value.copy(showBottomSheet = event.showBottomSheet))
            }
            is SendSosEvent.ContactTextFieldChanged -> {
                _contactTextFieldState.update { event.text }
            }
            SendSosEvent.GetContacts -> {
                viewModelScope.launch {
                    repository.getContacts().collect {
                        updateScreenState(_screenState.value.copy(contacts = it))
                    }
                }
            }
        }
    }

    private fun handleSendSosEvent(event: SendSosEvent.SendSos) {
        viewModelScope.launch {
            repository.sendSOS(event.body).collect {
                updateScreenState(_screenState.value.copy(sendSosCallState = it))
            }
        }
    }
}

class SosViewModelFactory(private val dataStore: DataStore<SosContacts>):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SosViewModel(repository = SosRepositoryImp(
            remoteDataSource = SosRemoteDataSource(RemoteSource.sosService),
            localDataSource = SosLocalDataSource(dataStore))) as T
    }
}