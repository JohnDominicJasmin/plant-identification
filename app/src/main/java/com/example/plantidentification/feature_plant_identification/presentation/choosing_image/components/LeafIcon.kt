package com.example.plantidentification.feature_plant_identification.presentation.choosing_image.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.plantidentification.R


@Composable
fun LeafIcon(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.leaf_icon),
        contentDescription = "Leaf icon",
        modifier = modifier
    )
}

@Preview
@Composable
private fun PreviewLeafIcon() {
    LeafIcon()
}