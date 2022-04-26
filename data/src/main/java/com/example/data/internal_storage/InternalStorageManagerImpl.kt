package com.example.data.internal_storage

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import android.net.Uri
import com.example.trempelapp.utils.USER_IMAGE
import com.example.trempelapp.utils.USER_IMAGE_PATH
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import javax.inject.Inject

class InternalStorageManagerImpl @Inject constructor(
    context: Context
) : InternalStorageManager {

    // Get the context wrapper instance
    private val wrapper = ContextWrapper(context)

    override suspend fun saveUserImage(image: Bitmap): Uri {

        // Initializing a new file
        // The bellow line return a directory in internal storage
        var file = wrapper.getDir(USER_IMAGE_PATH, Context.MODE_PRIVATE)

        // Create a file to save the image
        file = File(file, USER_IMAGE)

        try {
            // Get the file output stream
            val stream: OutputStream = FileOutputStream(file)

            // Compress bitmap
            image.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            // Flush the stream
            stream.flush()

            // Close stream
            stream.close()
        } catch (e: IOException) { // Catch the exception
            e.printStackTrace()
        }

        // Return the saved image uri
        return Uri.parse(file.absolutePath)
    }
}
