package com.example.data.internal_storage

import android.graphics.Bitmap
import android.net.Uri

interface InternalStorageManager {

    suspend fun saveUserImage(image: Bitmap): Uri
}
