package com.initbase.sosapp.core.data

class DataSourceException(override val message:String, val errorCode:Int):Exception()