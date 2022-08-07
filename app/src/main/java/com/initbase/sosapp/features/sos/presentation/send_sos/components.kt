package com.initbase.sosapp.features.sos.presentation.send_sos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import com.initbase.sosapp.SosContacts
import com.initbase.sosapp.core.data.source.contactsDataStore
import com.initbase.sosapp.ui.theme.BackgroundDark

@Composable
fun SosContactsBar(onClick: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp)
        .height(60.dp)
        .background(color = Color.BackgroundDark, shape = MaterialTheme.shapes.medium)
        .clip(MaterialTheme.shapes.medium)
        .clickable(onClick = onClick), contentAlignment = Alignment.Center) {
        Icon(imageVector = Icons.Default.Contacts, contentDescription = "Sos Contacts")
    }
}

@Composable
fun SendSosButton(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Box(modifier = modifier
        .fillMaxSize(0.5f)
        .aspectRatio(1f)
        .background(brush = Brush.verticalGradient(colors = listOf(
            Color(
                ColorUtils.blendARGB(MaterialTheme.colors.primary.toArgb(), Color.White.toArgb(), 0.2f),
            ),
            MaterialTheme.colors.primary
        )),
            shape = CircleShape)
        .clip(CircleShape)
        .clickable(onClick = onClick), contentAlignment = Alignment.Center) {
        Text("Send SOS", style = MaterialTheme.typography.h5, textAlign = TextAlign.Center)
    }
}

@Composable
fun ContactsBottomSheet(viewModel: SosViewModel): @Composable() (ColumnScope.() -> Unit) =
    {
        val textFieldValue by viewModel.contactTextFieldState.collectAsState()
        val state by viewModel.screenState.collectAsState()
        Row(modifier = Modifier
            .padding(16.dp)
            .height(50.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(modifier= Modifier
                .weight(4f)
                .fillMaxHeight(),value = textFieldValue, onValueChange = {
                viewModel.onEvent(SendSosEvent.ContactTextFieldChanged(it))
            })
            Button(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(), onClick = {
                viewModel.onEvent(SendSosEvent.AddNumber(textFieldValue))
            }) {
                Text("Add",color = MaterialTheme.colors.onBackground)
            }
        }
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 500.dp), verticalArrangement = Arrangement.spacedBy(8.dp)){
            items(state.contacts){number->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically){
                    Text(text = number,modifier=Modifier.weight(4f), style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.W600))
                    IconButton(onClick = {

                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }