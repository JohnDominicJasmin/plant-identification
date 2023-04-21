package com.example.plantidentification.feature_plant_identification.presentation.choosing_image.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextTitle(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.padding(all = 12.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically)) {

        Text(
            text = "A photo of a leaf",
            color = Color(0xFF595959),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = TextUnit(20f, TextUnitType.Sp),
        )
        Text(
            text = "Please make sure your photo clearly shows a leaf",
            textAlign = TextAlign.Center,
            color = Color(0xFF81898C),
            fontSize = TextUnit(16f, TextUnitType.Sp),

        )
    }
}

@Preview
@Composable
fun PreviewTextTitle() {
    TextTitle()
}