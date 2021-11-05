package com.liderman.mundolidermanapp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.AsyncTask
import androidx.exifinterface.media.ExifInterface
import com.afollestad.materialdialogs.MaterialDialog
import java.io.*
import java.util.*

class ProcessBitmap(private val doStuff: DoStuff) : AsyncTask<Any, Void, String>() {

    private lateinit var dialog: MaterialDialog

    override fun onPreExecute() {
        dialog = MaterialDialog.Builder(doStuff.getContext())
            .title("Procesando...")
            .content("Espere un momento")
            .progress(true, 0)
            .cancelable(false)
            .show()
    }

    override fun doInBackground(vararg objects: Any): String? {
        val url = objects[0]
        val isFromCamera = objects[1] as Boolean

        var bitmap: Bitmap

        bitmap = if (isFromCamera) {
            url as Bitmap//exifPhoto(url)
        } else {
            try {
                BitmapFactory.decodeStream(doStuff.getContext().contentResolver.openInputStream(Uri.parse(url as String)))
            } catch (e: FileNotFoundException) {
                return null
            }
        }

        val width = 1000
        val factor = width * 1.0 / bitmap.width
        val height = (bitmap.height * factor).toInt()

        bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false)

        return saveImageFile(bitmap)
    }

    override fun onPostExecute(s: String?) {
        dialog.dismiss()

        if (s == null) {
            MaterialDialog.Builder(doStuff.getContext())
                .title("Ups!")
                .content("Hubo un error, inténtelo nuevamente")
                .positiveText("Ok")
                .show()
            return
        }

        doStuff.done(s)
    }

    private fun exifPhoto(url: String): Bitmap {
        try {
            val exifInterface = ExifInterface(url)

            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1)

            val matrix = Matrix()
            var rotationDegrees = 0

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> rotationDegrees = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> rotationDegrees = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> rotationDegrees = 270
            }

            /*val bitmap = BitmapFactory.decodeFile(url)
            matrix.postRotate(rotationDegrees.toFloat())

            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)*/


            val bitmap = BitmapFactory.decodeFile(url)
            matrix.postRotate(rotationDegrees.toFloat())

            val widthBitmap = bitmap.width
            val heightBitmap = bitmap.height

            val maxWidth = 1800
            val newScale = (maxWidth * 1.0 / widthBitmap).toFloat()

            matrix.postScale(newScale, newScale)

            /*if (facing == CameraView.FACING_FRONT) {
                matrix.postScale(1f, -1f, newScale / 2, newScale / 2)
            }*/

            return Bitmap.createBitmap(bitmap, 0, 0, widthBitmap, heightBitmap, matrix, true)
        } catch (e: IOException) {
            return BitmapFactory.decodeFile(url)
        }

    }

    private fun saveImageFile(bitmap: Bitmap?): String? {
        val quality = 80
        val format = Bitmap.CompressFormat.WEBP

        val builder = StringBuilder()
        builder.append(doStuff.getContext().cacheDir.absolutePath)
        builder.append("/Natura/")
        builder.append(Date().time)
        builder.append(".jpg")

        val file = File(builder.toString())
        val path = File(file.parent)

        if (file.parent != null && !path.isDirectory) {
            path.mkdirs()
        }

        var outputStream: FileOutputStream? = null

        try {
            outputStream = FileOutputStream(file)
            bitmap!!.compress(format, quality, outputStream)
            return builder.toString()
        } catch (e: FileNotFoundException) {
            return null
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush()
                    outputStream.close()
                }
            } catch (e: IOException) {
                return null
            }

            bitmap!!.recycle()
        }
    }

    interface DoStuff {
        fun getContext(): Context
        fun done(s: String)
    }
}