package com.example.plantidentification.feature_plant_identification.presentation.splash_screen

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.example.plantidentification.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController) {

    val scale = remember { Animatable(initialValue = 0f) }
    LaunchedEffect(key1 = true) {
        scale.animateTo(0.75f,
            animationSpec = tween(
                durationMillis = 500,
                easing = { OvershootInterpolator(1.2f).getInterpolation(it) }
            ))
        delay(1200L)
        navController.navigate(route = "guidelines", navOptions {
            this.popUpTo("splash-screen") {
                inclusive = true
            }
        })
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)) {

        Image(
            painter = painterResource(id = R.drawable.ic_app_icon),
            contentDescription = "App Icon",
            modifier = Modifier
                .scale(scale.value))

    }

}