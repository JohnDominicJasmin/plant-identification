@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.plantidentification.feature_plant_identification.presentation.choosing_image
import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plantidentification.R
import com.example.plantidentification.feature_plant_identification.core.utils.ImageUtils.saveImageToGallery
import com.example.plantidentification.feature_plant_identification.core.utils.ImageUtils.toImageUri
import com.example.plantidentification.feature_plant_identification.core.utils.requestPermission
import com.example.plantidentification.feature_plant_identification.presentation.guidelines.GuidelinesScreen
import com.example.plantidentification.feature_plant_identification.presentation.plant_details.PlantInformation
import com.example.plantidentification.feature_plant_identification.presentation.splash_screen.SplashScreen
import com.example.plantidentification.ui.theme.PlantIdentificationTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.io.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantIdentificationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {

                    val viewModel: MainViewModel = hiltViewModel()
                    val state by viewModel.state.collectAsState()
                    val navController = rememberNavController()

                    NavHost(navController = navController , startDestination = "splash-screen"){
                        composable(route = "choosing-image"){
                            ChooseImageScreen(viewModel = viewModel, navController = navController)
                        }

                        composable(route = "splash-screen"){
                            SplashScreen(navController = navController, state = state)
                        }

                        composable(route = "guidelines"){
                            GuidelinesScreen(navController = navController){
                                viewModel.navigateToPlantInfoCompleted()
                            }
                        }

                        composable(route = "plant-info"){
                            PlantInformation(state = state, navController = navController)
                        }
                    }
                }
            }
        }
    }
}













