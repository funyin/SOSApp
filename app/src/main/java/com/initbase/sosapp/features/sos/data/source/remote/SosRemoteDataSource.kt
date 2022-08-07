package com.initbase.sosapp.features.sos.data.source.remote

import com.initbase.sosapp.core.data.DataSourceException
import com.initbase.sosapp.features.sos.data.source.SosDataSource
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosBody
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosResponse
import kotlinx.coroutines.flow.Flow

class SosRemoteDataSource(private val service: SosService):SosDataSource {
    override suspend fun sendSOS(body: SendSosBody): SendSosResponse {
        val response = service.sendSOS(body)
        if(response.isSuccessful)
            return response.body()!!
        else
            throw DataSourceException("An error occurred",response.code())
    }

    override suspend fun addContact(mobile: String) {
        throw NotImplementedError()
    }

    override suspend fun removeContact(mobile: String) {
        throw NotImplementedError()
    }

    override suspend fun getContacts(): Flow<List<String>> {
        throw NotImplementedError()
    }
}