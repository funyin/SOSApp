package com.initbase.sosapp.features.sos.data.source.local

import androidx.datastore.core.DataStore
import com.initbase.sosapp.SosContacts
import com.initbase.sosapp.features.sos.data.source.SosDataSource
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosBody
import com.initbase.sosapp.features.sos.domain.use_cases.SendSosResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SosLocalDataSource(private val dataStore: DataStore<SosContacts>): SosDataSource {
    override suspend fun sendSOS(body: SendSosBody): SendSosResponse {
        throw NotImplementedError()
    }

    override suspend fun addContact(mobile: String) {
        dataStore.updateData {
            it.toBuilder().addItems(mobile).build()
        }
    }

    override suspend fun removeContact(mobile: String) {
        dataStore.updateData {
            it.toBuilder().clearItems().addAllItems(it.itemsList.filter {item-> item!=mobile}) .build()
        }
    }

    override suspend fun getContacts(): Flow<List<String>> = dataStore.data.map { it.itemsList }
}