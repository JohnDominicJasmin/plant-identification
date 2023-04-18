package com.example.plantidentification.feature_plant_identification.core.utils.save_images

import android.graphics.Bitmap
import android.net.Uri

interface ImageSaver {
    fun saveImage(bitmap: Bitmap): Uri?
}