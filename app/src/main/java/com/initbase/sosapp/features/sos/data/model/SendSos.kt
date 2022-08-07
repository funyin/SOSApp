package com.initbase.sosapp.features.sos.domain.use_cases

data class SendSosBody (
    val phoneNumbers: List<String>,
    val image: String,
    val location: Location
)

data class Location (
    val longitude: String,
    val latitude: String
)


data class SendSosResponse (
    val status: String,
    val data: SendSosResponseData,
    val message: String
)
data class SendSosResponseData (
    val phoneNumbers: List<String>,
    val image: String,
    val location: Location,
    val id: Long
)