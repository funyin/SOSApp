package com.initbase.sosapp.features.sos.data.repository

import com.initbase.sosapp.core.data.CallState
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosBody
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosResponse
import kotlinx.coroutines.flow.Flow

interface SosRepository {
    suspend fun sendSOS(body:SendSosBody): Flow<CallState<SendSosResponse>>
    suspend fun addContact(mobile: String)
    suspend fun removeContact(mobile: String)
    suspend fun getContacts(): Flow<List<String>>
}