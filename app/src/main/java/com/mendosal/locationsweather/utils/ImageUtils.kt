package com.mendosal.locationsweather.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * Created by Luis Mendoza on 8/28/2021.
 */
class ImageUtils {

    companion object {
        fun bitMapFromVector(context: Context, vectorResID: Int, width: Int = 0, height: Int = 0): BitmapDescriptor {
            val vectorDrawable = context?.let { ContextCompat.getDrawable(it, vectorResID) }
            var vectorHeight = vectorDrawable!!.intrinsicHeight
            var vectorWidth = vectorDrawable.intrinsicWidth
            if (width != 0 && height != 0) {
                vectorHeight = height
                vectorWidth = width
            }
            vectorDrawable!!.setBounds(0, 0, vectorWidth, vectorHeight)
            val bitmap = Bitmap.createBitmap(vectorWidth, vectorHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            vectorDrawable.draw(canvas)
            return BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}