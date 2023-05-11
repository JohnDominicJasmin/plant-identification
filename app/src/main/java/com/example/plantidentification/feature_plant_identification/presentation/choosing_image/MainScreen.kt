package com.example.plantidentification.feature_plant_identification.presentation.choosing_image

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.plantidentification.R
import com.example.plantidentification.feature_plant_identification.core.utils.ImageUtils.saveImageToGallery
import com.example.plantidentification.feature_plant_identification.core.utils.ImageUtils.toImageUri
import com.example.plantidentification.feature_plant_identification.core.utils.connection.ConnectionStatus.hasInternetConnection
import com.example.plantidentification.feature_plant_identification.core.utils.requestPermission
import com.example.plantidentification.feature_plant_identification.presentation.choosing_image.components.ButtonItem
import com.example.plantidentification.feature_plant_identification.presentation.choosing_image.components.LeafIcon
import com.example.plantidentification.feature_plant_identification.presentation.choosing_image.components.TextTitle
import com.example.plantidentification.ui.theme.PlantIdentificationTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

@Preview
@Composable
fun MainScreenPreview() {
    PlantIdentificationTheme {
        MainScreenContent(state = MainState(isLoading = true))
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChooseImageScreen(
    viewModel: MainViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }


    val openGalleryResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uriResult: Uri? ->
            imageUri = uriResult
            if (!context.hasInternetConnection()) {
                Toast.makeText(context, "No internet connection.", Toast.LENGTH_LONG).show()
                return@rememberLauncherForActivityResult
            }
            uriResult?.let {
                val imagePath = getRealPathFromURI(uri = uriResult, context = context)
                if (imagePath == null) {
                    Toast.makeText(context, "Image path not found.", Toast.LENGTH_LONG).show()
                    return@let
                }
                viewModel.recognizeFood(imagePath = imagePath)
            }
        }

    val openCameraResultLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmapResult: Bitmap? ->
            val uri = bitmapResult?.toImageUri(inContext = context)
            imageBitmap = bitmapResult

            if (!context.hasInternetConnection()) {
                Toast.makeText(context, "No internet connection.", Toast.LENGTH_LONG).show()
                return@rememberLauncherForActivityResult
            }

            uri?.let {
                val imagePath = getRealPathFromURI(uri = uri, context = context)
                if (imagePath == null) {
                    Toast.makeText(context, "Image path is null.", Toast.LENGTH_LONG).show()
                    return@let
                }
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

    LaunchedEffect(key1 = true) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is MainEvent.ShowToastMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }


                is MainEvent.GetPlantInfo -> {
                    navController.navigate(route = "plant-info")
                }
            }
        }
    }

    MainScreenContent(
        onClickTakePhotoButton = onClickTakePhotoButton,
        onClickSelectGalleryButton = onClickSelectGalleryButton)

}


@Composable
fun MainScreenContent(
    state: MainState = MainState(),
    onClickTakePhotoButton: () -> Unit = {},
    onClickSelectGalleryButton: () -> Unit = {}) {

    Row(modifier = Modifier.fillMaxSize()) {

        Spacer(modifier = Modifier.weight(0.09f))

        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = R.drawable.batangas_lakelands_review_14_1__8_),
                contentDescription = "Background Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillHeight,

                )



            Column(
                modifier = Modifier

                    .padding(top = 20.dp)
                    .fillMaxSize()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {


                Image(
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .scale(1.4f),
                    painter = painterResource(id = R.drawable.logoooooooooooooooooooo),
                    contentDescription = "Logo Batangas Lakelands")



                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.spacedBy(
                        8.dp,
                        alignment = Alignment.CenterVertically)) {

                    ButtonItem(
                        isEnabled = !state.isLoading,
                        icon = R.drawable.ic_camera,
                        text = "Take Photo",
                        contentDescription = "Take Photo",
                        onClick = onClickTakePhotoButton)

                    ButtonItem(
                        isEnabled = !state.isLoading,
                        icon = R.drawable.ic_gallery,
                        text = "Select from Gallery",
                        contentDescription = "Select from Gallery",
                        onClick = onClickSelectGalleryButton)

                }


            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color(0xFF1C681C))
            }
        }
        Spacer(modifier = Modifier.weight(0.09f))

    }


}

private fun getRealPathFromURI(uri: Uri, context: Context): String? {
    val returnCursor = context.contentResolver.query(uri, null, null, null, null)
    val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    returnCursor.moveToFirst()
    val name = returnCursor.getString(nameIndex)
    val file = File(context.filesDir, name)
    try {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val outputStream = FileOutputStream(file)
        var read = 0
        val maxBufferSize = 1 * 1024 * 1024
        val bytesAvailable: Int = inputStream?.available() ?: 0
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


