@file:Suppress("PropertyName")

package com.liderman.mundolidermanapp.presentation.ui.base

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.liderman.mundolidermanapp.R

@Suppress("PrivatePropertyName", "PropertyName", "MemberVisibilityCanBePrivate")
abstract class ProfileBaseFragment : LiderManBaseFragment() {
    val RESULT_PHOTOS = 4367
    val IMAGE_PICK_CODE = 5463
    val PERMISSION_CAMERA = 4567
    val PERMISSION_GALLERY = 5678

    private fun getCameraPermission() {
        val permissionArrays = arrayOf(Manifest.permission.CAMERA)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            intentCamera()
        } else {
            requestPermissions(permissionArrays, PERMISSION_CAMERA)
            //Toast.makeText(context, "", Toast.LENGTH_LONG).show()
        }
    }

    fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(activity!!.packageManager)?.also {
                startActivityForResult(takePictureIntent, RESULT_PHOTOS)
            }
        }
    }

    private fun getGalleryPermission() {
        val permissionArrays = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            intentGallery ()
        } else {
            requestPermissions(permissionArrays, PERMISSION_GALLERY)
            //Toast.makeText(context, "", Toast.LENGTH_LONG).show()
        }
    }

    fun intentGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    fun selectOption() {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_option_take)
        val btn1: AppCompatButton = dialog.findViewById<View>(R.id.btn_camera) as AppCompatButton
        val btn2: AppCompatButton = dialog.findViewById<View>(R.id.btn_gallery) as AppCompatButton
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.window?.attributes?.windowAnimations = R.style.AppTheme_Slide
        dialog.show()
        btn1.setOnClickListener {
            dialog.dismiss()
            getCameraPermission()
        }
        btn2.setOnClickListener {
            dialog.dismiss()
            getGalleryPermission()
        }
    }
}