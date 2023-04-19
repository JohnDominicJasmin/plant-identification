package com.example.plantidentification.feature_plant_identification.presentation.guidelines

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.plantidentification.R
import com.example.plantidentification.feature_plant_identification.presentation.choosing_image.MainViewModel

@Composable
fun GuidelinesScreen(navController: NavController, mainViewModel: MainViewModel) {

    GuidelinesContent(onClickStartButton = {

        mainViewModel.navigateToPlantInfoCompleted()
        navController.navigate("choosing-image", navOptions {
            popUpTo("guidelines"){
                inclusive = true
            }
        })
    })
}

@Composable
fun GuidelinesContent(onClickStartButton: () -> Unit = {}) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = MaterialTheme.colors.background) {

        Column(
            modifier = Modifier.fillMaxSize().padding(all = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(22.dp)) {

            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = "Guidelines",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center)


            Text(
                modifier = Modifier.padding(all = 15.dp),
                text = context.getString(R.string.guidelines),
                style = TextStyle(
                    textDecoration = TextDecoration.Underline,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    lineHeight = 23.sp
                ), overflow = TextOverflow.Clip)


            Spacer(modifier = Modifier.weight(0.6f))

            OutlinedButton(
                onClick = onClickStartButton,
                modifier = Modifier.padding(all = 4.dp), shape = RoundedCornerShape(30),
                border = BorderStroke(1.dp, Color.Black)) {
                Text(
                    text = "Start",
                    fontSize = 16.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(horizontal = 16.dp))
            }
            Spacer(modifier = Modifier.weight(0.1f))

        }


    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewGuidelines() {
    GuidelinesContent()
}