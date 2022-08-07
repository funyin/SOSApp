package com.initbase.sosapp.features.sos.presentation.send_sos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.initbase.sosapp.ui.theme.BackgroundDark

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SendSosScreen(viewModel: SosViewModel) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val state by viewModel.screenState.collectAsState()
    val bottomSheetState = scaffoldState.bottomSheetState
    LaunchedEffect(state.showBottomSheet) {
        if(state.showBottomSheet)
            bottomSheetState.expand()
        else bottomSheetState.collapse()
    }
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = ContactsBottomSheet(viewModel),
        sheetPeekHeight = 0.dp,
        sheetGesturesEnabled = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("SOS", style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.W600))
            Box(modifier = Modifier
                .fillMaxWidth()
                .weight(1f), contentAlignment = Alignment.Center) {
                Box(modifier = Modifier
                    .fillMaxSize(0.9f)
                    .aspectRatio(1f)
                    .background(color = Color.BackgroundDark, shape = CircleShape))
                SendSosButton {

                }
            }
            SosContactsBar{
                viewModel.onEvent(SendSosEvent.UpdateContactsSheetState(showBottomSheet = !state.showBottomSheet))
            }
        }
    }
}