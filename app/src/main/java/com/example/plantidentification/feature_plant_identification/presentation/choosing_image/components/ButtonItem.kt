package com.example.plantidentification.feature_plant_identification.presentation.choosing_image.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.plantidentification.R

@Composable
fun ButtonItem(
    isEnabled:Boolean = true,
    modifier: Modifier = Modifier,
    icon: Int = -1,
    text: String = "",
    contentDescription: String = "",
    onClick: () -> Unit = {}) {


    Card(
        modifier = modifier.padding(all = 4.dp),
        backgroundColor = Color(0xFFE0E0E0),
        shape = RoundedCornerShape(12.dp)) {

        Button(
            modifier = Modifier.fillMaxWidth(fraction = 0.6f),
            onClick = onClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE0E0E0)),
            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 0.dp),
            enabled = isEnabled
        ) {


            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    4.dp,
                    alignment = Alignment.CenterVertically),
                modifier = Modifier.padding(vertical = 18.dp, horizontal = 16.dp)) {

                Icon(
                    painter = painterResource(id = icon),
                    contentDescription = contentDescription,
                    tint = Color(0xFF1C681C),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )

                Text(
                    text = text,
                    color = Color(0xFF292929),
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                    fontSize = TextUnit(14f, TextUnitType.Sp),
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewButtonItemTakePhoto() {
    Box(modifier = Modifier.fillMaxSize()) {
        ButtonItem(
            icon = R.drawable.ic_camera,
            text = "Take Photo",
            contentDescription = "Take Photo")
    }
}

@Preview
@Composable
fun PreviewButtonItemSelectFromGallery() {
    Box(modifier = Modifier.fillMaxSize()) {
        ButtonItem(
            icon = R.drawable.ic_gallery,
            text = "Select from Gallery",
            contentDescription = "Take Photo")
    }
}