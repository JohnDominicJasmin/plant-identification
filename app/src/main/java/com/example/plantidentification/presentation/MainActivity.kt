@file:OptIn(ExperimentalPermissionsApi::class)

package com.example.plantidentification.presentation

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Base64
import androidx.activity.ComponentActivity
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.plantidentification.R
import com.example.plantidentification.core.utils.ImageUtils.saveImageToGallery
import com.example.plantidentification.core.utils.requestPermission
import com.example.plantidentification.ui.theme.PlantIdentificationTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantIdentificationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()

                    NavHost(navController = navController , startDestination = "main-screen"){
                        composable(route = "main-screen"){
                            MainScreen()
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun MainScreenPreview() {
    PlantIdentificationTheme {
        MainScreenContent()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }


    val saveImageToGallery = remember {{ bitmap: Bitmap ->
        context.saveImageToGallery(bitmap = bitmap)
    }}


    val openGalleryResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uriResult: Uri? ->
            imageUri = uriResult
            Timber.d("Uri ${uriResult.toString()}")
            uriResult?.let {

                val imagePath = getRealPathFromURI(uri = uriResult, context = context) ?: return@let

                viewModel.recognizeFood(imagePath = imagePath)
            }
        }

    val openCameraResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmapResult: Bitmap? ->
            val uri = bitmapResult?.let(saveImageToGallery)
            imageBitmap = bitmapResult

            uri?.let {
                val imagePath = getRealPathFromURI(uri = uri, context = context) ?: return@let
                viewModel.recognizeFood(imagePath = imagePath)
            }
        }

    val accessStoragePermissionState =
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) { permissionGranted ->
            if (permissionGranted.values.all { it }) {
                openGalleryResultLauncher.launch("image/*")
            }
        }


    val openCameraPermissionState =
        rememberPermissionState(permission = Manifest.permission.CAMERA) { permissionGranted ->
            if (permissionGranted) {
                openCameraResultLauncher.launch()
            }
        }


    val onClickTakePhotoButton = remember {
        {
            openCameraPermissionState.requestPermission(
                context = context,
                rationalMessage = "Camera permission is not yet granted.", onGranted = {
                    openCameraResultLauncher.launch()
                })
        }
    }

    val onClickSelectGalleryButton = remember {
        {
            accessStoragePermissionState.requestPermission(
                context = context,
                rationalMessage = "Storage permission is not yet granted.", onGranted = {
                    openGalleryResultLauncher.launch("image/*")
                })
        }
    }

    MainScreenContent(
        onClickTakePhotoButton = onClickTakePhotoButton,
        onClickSelectGalleryButton = onClickSelectGalleryButton)

}







@Composable
fun MainScreenContent(
    onClickTakePhotoButton: () -> Unit = {},
    onClickSelectGalleryButton: () -> Unit = {}) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = onClickTakePhotoButton, modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_camera_alt_24),
                contentDescription = "Take a photo")
            Text(text = "Take a Photo", modifier = Modifier.padding(all = 8.dp))
        }

        Button(onClick = onClickSelectGalleryButton, modifier = Modifier.fillMaxWidth()) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_browse_gallery_24),
                contentDescription = "Select from gallery")
            Text(text = "Select from Gallery", modifier = Modifier.padding(all = 8.dp))
        }
    }


}



fun getRealPathFromURI(uri: Uri, context: Context): String? {
    val returnCursor = context.contentResolver.query(uri, null, null, null, null)
    val nameIndex =  returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name = returnCursor.getString(nameIndex)
    val file = File(context.filesDir, name)
    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        var read = 0
        val maxBufferSize = 1 * 1024 * 1024
        val bytesAvailable: Int = inputStream?.available() ?: 0
        //int bufferSize = 1024;
        val bufferSize = Math.min(bytesAvailable, maxBufferSize)
        val buffers = ByteArray(bufferSize)
        while (inputStream?.read(buffers).also {
                if (it != null) {
                    read = it
                }
            } != -1) {
            outputStream.write(buffers, 0, read)
        }
        Timber.e("File Size", "Size " + file.length())
        inputStream?.close()
        outputStream.close()
        Timber.e("File Path", "Path " + file.path)

    } catch (e: java.lang.Exception) {
        Timber.e("Exception", e.message!!)
    }
    return file.path
}









