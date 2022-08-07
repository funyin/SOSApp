package com.initbase.sosapp.features.sos.presentation.send_sos

import com.initbase.sosapp.SosContacts
import com.initbase.sosapp.core.data.CallState
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class SendSosScreenState (
    val sendSosCallState: CallState<SendSosResponse> = CallState.Initial,
    val showBottomSheet:Boolean = false,
    val contacts: List<String> = emptyList()
)