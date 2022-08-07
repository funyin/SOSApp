package com.initbase.sosapp.features.sos.presentation.send_sos

import com.initbase.sosapp.features.sos.domain.use_cases.SendSosBody

sealed interface SendSosEvent{
    data class SendSos(val body: SendSosBody):SendSosEvent
    data class UpdateContactsSheetState(val showBottomSheet:Boolean):SendSosEvent
    data class AddNumber(val number:String):SendSosEvent
    object GetContacts:SendSosEvent
    data class ContactTextFieldChanged(val text:String):SendSosEvent
}