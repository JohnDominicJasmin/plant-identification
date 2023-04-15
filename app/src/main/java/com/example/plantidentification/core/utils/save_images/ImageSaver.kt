package com.example.plantidentification.core.utils.save_images

import android.graphics.Bitmap
import android.net.Uri

interface ImageSaver {
    fun saveImage(bitmap: Bitmap): Uri?
}