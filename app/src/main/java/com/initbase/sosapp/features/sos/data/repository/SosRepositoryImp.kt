package com.initbase.sosapp.features.sos.data.repository

import com.initbase.sosapp.core.data.CallState
import com.initbase.sosapp.core.data.DataSourceException
import com.initbase.sosapp.features.sos.data.source.SosDataSource
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosBody
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SosRepositoryImp(private val remoteDataSource:SosDataSource,private val localDataSource:SosDataSource): SosRepository {
    override suspend fun sendSOS(body: SendSosBody): Flow<CallState<SendSosResponse>>  = flow{
         try{
             emit(CallState.Loading)
            val response = remoteDataSource.sendSOS(body)
            emit(CallState.Success(response))
        }catch (e:DataSourceException) {
            emit(CallState.Error(error = e))
        }
    }

    override suspend fun addContact(mobile: String) {
        localDataSource.addContact(mobile)
    }

    override suspend fun removeContact(mobile: String) {
        localDataSource.removeContact(mobile)
    }

    override suspend fun getContacts(): Flow<List<String>>  = localDataSource.getContacts()

}