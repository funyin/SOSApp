package com.initbase.sosapp.features.sos.presentation.send_sos

import android.net.Uri
import androidx.camera.core.ImageCaptureException
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.initbase.sosapp.core.data.CallState
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosResponse

data class SendSosScreenState @ExperimentalPermissionsApi constructor(
    val sendSosCallState: CallState<SendSosResponse> = CallState.Initial,
    val showBottomSheet:Boolean = false,
    val contacts: List<String> = emptyList(),
    val permissionStatus: PermissionStatus? = null,
    val capturedImage: Uri? =null,
    val imageCaptureError: ImageCaptureException? = null,
    val showCamera:Boolean = false
)