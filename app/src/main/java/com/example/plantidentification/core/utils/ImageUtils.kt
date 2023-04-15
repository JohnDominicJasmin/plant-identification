package com.example.plantidentification.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Base64
import com.example.plantidentification.core.utils.save_images.ImageSaver
import com.example.plantidentification.core.utils.save_images.ImageSaverFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.*


object ImageUtils {


    fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream) {
        runCatching {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        }.onFailure {
            Timber.e(it.message)
        }
    }

    fun Bitmap.toImageUri(inContext: Context): Uri? {
        val bytes = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            inContext.contentResolver,
            this,
            "CyclistanceImage",
            null)
        return Uri.parse(path)
    }

    fun Context.saveImageToGallery(
        bitmap: Bitmap,
        imageSaver: ImageSaver = ImageSaverFactory().create(this),
    ): Uri? {
        return imageSaver.saveImage(bitmap)
    }

    private suspend fun base64EncodeFromFile(fileString: String): String {
        val file = File(fileString)
        val fis = withContext(Dispatchers.IO) {
            FileInputStream(file)
        }
        val buffer = ByteArray(file.length().toInt())
        withContext(Dispatchers.IO) {
            fis.read(buffer)
        }
        withContext(Dispatchers.IO) {
            fis.close()
        }
        return Base64.encodeToString(buffer, Base64.DEFAULT)
    }

     suspend fun getRealPathFromURI(uri: Uri, context: Context): String? {
        val returnCursor = context.contentResolver.query(uri, null, null, null, null)
        val nameIndex = returnCursor!!.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE)
        returnCursor.moveToFirst()
        val name = returnCursor.getString(nameIndex)
        val file = File(context.filesDir, name)
        try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val outputStream = withContext(Dispatchers.IO) {
                FileOutputStream(file)
            }
            var read = 0
            val maxBufferSize = 1 * 1024 * 1024
            val bytesAvailable: Int = withContext(Dispatchers.IO) {
                inputStream?.available() ?: 0
            }
            //int bufferSize = 1024;
            val bufferSize = Math.min(bytesAvailable, maxBufferSize)
            val buffers = ByteArray(bufferSize)
            while (withContext(Dispatchers.IO) {
                    inputStream?.read(buffers).also {
                        if (it != null) {
                            read = it
                        }
                    }
                } != -1) {
                withContext(Dispatchers.IO) {
                    outputStream.write(buffers, 0, read)
                }
            }
            Timber.e("File Size", "Size " + file.length())
            withContext(Dispatchers.IO) {
                inputStream?.close()
                outputStream.close()
                returnCursor.close()
            }
            Timber.e("File Path", "Path " + file.path)

        } catch (e: java.lang.Exception) {
            Timber.e("Exception", e.message!!)
        }
        return file.path
    }

}