package com.example.plantidentification.feature_plant_identification.presentation.splash_screen

import android.content.res.Configuration
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.plantidentification.R
import com.example.plantidentification.feature_plant_identification.presentation.choosing_image.MainState
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController, state: MainState) {

    SplashScreenContent(onClickContinue = {
        navController.navigate(route = state.startingDestination, navOptions {
            this.popUpTo("splash-screen") {
                inclusive = true
            }
        })
    })


}


@Composable
fun SplashScreenContent(onClickContinue: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {


        Image(
            painter = painterResource(id = R.drawable.batangas_lakelands_review_14_1__8_),
            contentDescription = "Background Image",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillHeight,

            )

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween) {


            Image(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .scale(1.4f),
                painter = painterResource(id = R.drawable.logoooooooooooooooooooo),
                contentDescription = "Logo Batangas Lakelands")

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    15.dp,
                    alignment = Alignment.CenterVertically)
            ) {

                Text(
                    text = "Welcome To \nLEAFGraphy",
                    style = MaterialTheme.typography.h4,
                    color = Color.White,
                    modifier = Modifier

                        .align(alignment = Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontSize = TextUnit(value = 27f, TextUnitType.Sp)
                )

                Text(
                    text = "Your personal assistant for \nrecognizing the tree species",
                    color = Color.White,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally),
                    fontSize = TextUnit(value = 16f, TextUnitType.Sp)
                )


                IconButton(
                    onClick = onClickContinue, modifier = Modifier
                        .scale(1.1f)
                        .padding(top = 50.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrrowww),
                        contentDescription = "Continue Arrow",
                        tint = Color.White)
                }


            }
        }
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    SplashScreenContent()
}