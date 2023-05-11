package com.example.plantidentification.feature_plant_identification.presentation.guidelines

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions

@Composable
fun GuidelinesScreen(
    navController: NavController,
    onNavigateToPlantInfoCompleted: () -> Unit = {}) {

    GuidelinesContent(onClickContinueButton = {

        onNavigateToPlantInfoCompleted()
        navController.navigate("choosing-image", navOptions {
            popUpTo("guidelines") {
                inclusive = true
            }
        })
    })
}

@Composable
fun GuidelinesContent(onClickContinueButton: () -> Unit = {}) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xFFF1F5F8)) {

        Box(modifier = Modifier.fillMaxSize()) {


            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(22.dp)) {

                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = "How to use LEAFGraphy",
                    fontSize = TextUnit(value = 26f, type = TextUnitType.Sp),
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center)


                Text(fontWeight = FontWeight.SemiBold,
                    fontSize = TextUnit(value = 18f, TextUnitType.Sp),
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("Step 1: First, make sure that you have an Internet Connection while using this app.\n\n")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("Step 2: Allow Permissions for Camera and Storage.\n\n")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("Step 3: Take a photo or select from gallery of whole Leaves (Note: Crop Leaves and photo with dark lightning can't be recognized)\n\n")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("Step 4: Click the Button (✔) if you are done taking and uploading photo\n\n")
                        }
                        withStyle(style = SpanStyle(color = Color.Black)) {
                            append("Step 5: If the screen shows (Not Found), you need to retake a photo again.")
                        }
                    }
                )





                Spacer(modifier = Modifier.weight(0.6f))

                Button(
                    onClick = onClickContinueButton,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(text = "Continue", fontSize = TextUnit(value = 14f, TextUnitType.Sp),    modifier = Modifier.padding(horizontal = 30.dp, vertical = 4.dp))
                }

                Spacer(modifier = Modifier.weight(0.1f))

            }
        }


    }
}

@Preview(device = Devices.PIXEL_4)
@Composable
fun PreviewGuidelines() {
    GuidelinesContent()
}