package com.initbase.sosapp.features.sos.presentation.send_sos

import android.Manifest
import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.initbase.sosapp.ui.theme.BackgroundDark
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutorService
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalMaterialApi::class, ExperimentalPermissionsApi::class)
@Composable
fun SendSosScreen(viewModel: SosViewModel,cameraExecutor: ExecutorService) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val state by viewModel.screenState.collectAsState()
    val bottomSheetState = scaffoldState.bottomSheetState
    val permissionStatus = state.permissionStatus

    val onPermissionGranted = {
        viewModel.onEvent(SendSosEvent.LaunchCamera)
    }
    val permissionState = rememberPermissionState(permission = Manifest.permission.CAMERA, onPermissionResult = {
        if(it)
            onPermissionGranted()
    })
    LaunchedEffect(state.showBottomSheet) {
        if (state.showBottomSheet)
            bottomSheetState.expand()
    }
    LaunchedEffect(bottomSheetState.currentValue){
        if(bottomSheetState.currentValue==BottomSheetValue.Collapsed)
            viewModel.onEvent(SendSosEvent.UpdateContactsSheetState(showBottomSheet = false))
    }
    LaunchedEffect(permissionStatus) {
        when (permissionStatus) {
            is PermissionStatus.Denied -> {
                if (permissionStatus.shouldShowRationale) {
                    scaffoldState.snackbarHostState.showSnackbar("Please grant camera permission", actionLabel = "Request").also {
                        if(it == SnackbarResult.ActionPerformed)
                            permissionState.launchPermissionRequest()
                    }
                } else
                    permissionState.launchPermissionRequest()
            }
            PermissionStatus.Granted -> onPermissionGranted()
            null -> {}
        }
    }
    val capturedImage = state.capturedImage
    LaunchedEffect(capturedImage){
        capturedImage?.let {
            viewModel.onEvent(SendSosEvent.SendSos)
        }
    }
    BackHandler(enabled = bottomSheetState.isExpanded) {
        scope.launch {
            bottomSheetState.collapse()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = ContactsBottomSheet(viewModel),
        sheetPeekHeight = 0.dp,
    ) {
        Box(modifier=Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("SOS", style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.W600))
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f), contentAlignment = Alignment.Center) {
                    Box(modifier = Modifier
                        .fillMaxSize(0.9f)
                        .aspectRatio(1f)
                        .background(color = Color.BackgroundDark, shape = CircleShape))
                    SendSosButton(state=state.sendSosCallState) {
                        viewModel.onEvent(SendSosEvent.CheckPermissionStatus(permissionState))
                    }
                }
                SosContactsBar {
                    viewModel.onEvent(SendSosEvent.UpdateContactsSheetState(showBottomSheet = true))
                }
            }

            if (state.showCamera)
                CameraView(
                    viewModel = viewModel,
                    executor = cameraExecutor,
                )
        }
    }
}