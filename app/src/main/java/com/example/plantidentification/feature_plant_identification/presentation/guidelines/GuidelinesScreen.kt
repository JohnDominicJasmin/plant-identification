package com.example.plantidentification.feature_plant_identification.presentation.guidelines

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.plantidentification.R
import com.example.plantidentification.feature_plant_identification.presentation.choosing_image.MainState

@Composable
fun GuidelinesScreen(
    navController: NavController,
    onNavigateToPlantInfoCompleted: () -> Unit = {}) {




    GuidelinesContent(onClickStartButton = {

        onNavigateToPlantInfoCompleted()
        navController.navigate("choosing-image", navOptions {
            popUpTo("guidelines") {
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

        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = R.drawable.image_13),
                contentDescription = "Information Background",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                alpha = 0.9f,
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(22.dp)) {

                Text(
                    modifier = Modifier.padding(top = 12.dp),
                    text = "Information",
                    fontSize = TextUnit(value = 26f, type = TextUnitType.Sp),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Center)


                Text(
                    modifier = Modifier.padding(all = 15.dp),
                    text = context.getString(R.string.guidelines),
                    style = TextStyle(
                        textDecoration = TextDecoration.Underline,
                        textAlign = TextAlign.Justify,
                        fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
                        lineHeight = 28.sp,
                        color = Color.White
                    ), overflow = TextOverflow.Clip)


                Spacer(modifier = Modifier.weight(0.6f))

                OutlinedButton(
                    onClick = onClickStartButton,
                    modifier = Modifier.padding(all = 4.dp), shape = RoundedCornerShape(30),
                    colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.Transparent),
                    border = BorderStroke(1.5.dp, Color.White)) {
                    Text(
                        text = "Start",
                        fontSize = TextUnit(value = 14f, type = TextUnitType.Sp),
                        color = Color.White,
                        modifier = Modifier.padding(horizontal = 30.dp, vertical = 4.dp))
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