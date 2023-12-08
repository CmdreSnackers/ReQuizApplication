package com.sw.requizapplication.core.services

import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class StorageServ(
    private val stor: StorageReference = FirebaseStorage.getInstance().reference
)
{
    suspend fun addImage(
        name: String,
        uri: Uri
    )
    {
        stor.child(name).putFile(uri).await()
    }

    suspend fun getImage(name: String): Uri?
    {
        return try
        {
            stor.child(name).downloadUrl.await()
        } catch (e:Exception)
        {
            e.printStackTrace()
            null
        }
    }

}