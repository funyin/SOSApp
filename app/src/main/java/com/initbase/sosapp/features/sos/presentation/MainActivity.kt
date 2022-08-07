package com.initbase.sosapp.features.sos.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.initbase.sosapp.core.data.source.contactsDataStore
import com.initbase.sosapp.features.sos.presentation.send_sos.SendSosScreen
import com.initbase.sosapp.features.sos.presentation.send_sos.SosViewModel
import com.initbase.sosapp.features.sos.presentation.send_sos.SosViewModelFactory
import com.initbase.sosapp.ui.theme.SOSAppTheme

class MainActivity : ComponentActivity() {
    private val dataStore by lazy { applicationContext.contactsDataStore }
    private val viewModel by viewModels<SosViewModel>{SosViewModelFactory(dataStore)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SOSAppTheme(darkTheme = true) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    SendSosScreen(viewModel)
                }
            }
        }
    }
}