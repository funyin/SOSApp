package com.initbase.sosapp.features.sos.presentation.send_sos

import androidx.camera.core.ImageCapture
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosBody
import java.io.File
import java.util.concurrent.Executor

sealed interface SendSosEvent{
    object SendSos:SendSosEvent
    data class CheckPermissionStatus @OptIn(ExperimentalPermissionsApi::class)
    constructor(val permissionState:PermissionState):SendSosEvent
    data class UpdateContactsSheetState(val showBottomSheet:Boolean):SendSosEvent
    data class AddNumber(val number:String):SendSosEvent
    data class RemoveNumber(val index:Int):SendSosEvent
    object GetContacts:SendSosEvent
    data class CaptureImage(val imageCapture: ImageCapture, val outputFile: File, val executor: Executor):SendSosEvent
    object LaunchCamera:SendSosEvent
    data class ContactTextFieldChanged(val text:String):SendSosEvent
}