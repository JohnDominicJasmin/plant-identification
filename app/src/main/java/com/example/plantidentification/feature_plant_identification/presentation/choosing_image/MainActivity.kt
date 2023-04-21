
package com.example.plantidentification.feature_plant_identification.presentation.choosing_image
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plantidentification.feature_plant_identification.presentation.guidelines.GuidelinesScreen
import com.example.plantidentification.feature_plant_identification.presentation.plant_details.PlantInformation
import com.example.plantidentification.feature_plant_identification.presentation.splash_screen.SplashScreen
import com.example.plantidentification.ui.theme.PlantIdentificationTheme
import dagger.hilt.android.AndroidEntryPoint


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













