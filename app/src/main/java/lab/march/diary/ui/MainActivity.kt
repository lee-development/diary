package lab.march.diary.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import lab.march.diary.R
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firestore = Firebase.firestore

        val post = hashMapOf(
            "content" to "안드로이드 클라이언트에서 만든 일기",
            "createdAt" to Timestamp(Date())
        )

        val ref = firestore.collection("posts")
            .document()

        ref.set(post)
            .addOnSuccessListener {
                Log.d(TAG, "document snapshot written with ID: ${ref.id}")
                Toast.makeText(this, "document snapshot written with ID: ${ref.id}", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "error writing document ${exception.message}")
                Toast.makeText(this, "error writing document ${exception.message}", Toast.LENGTH_LONG).show()
            }
            .addOnCanceledListener {
                Log.d(TAG, "canceled writing document")
                Toast.makeText(this, "canceled writing document", Toast.LENGTH_LONG).show()
            }
    }

    private fun requestPermission() = ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

    private fun pickImage() = startActivityForResult(Intent(Intent.ACTION_PICK).apply { type = "image/*" }, REQUEST_CODE)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                val uri = data?.data

                uri?.let {
                    val firebaseStorage = Firebase.storage
                    val name = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date()) + ".png"

                    val ref = firebaseStorage.reference
                        .child("images")
                        .child(name)

                    lifecycleScope.launchWhenResumed {
                        launch(Dispatchers.IO) { uploadImage(ref, it) }
                    }
                }
            } else {
                /* fail to get uri */
            }
        }
    }

    private suspend fun uploadImage(ref: StorageReference, uri: Uri) {
        try {
            val snapshot = ref.putFile(uri).await()

            val url = snapshot.storage.downloadUrl.await()

            val post = hashMapOf(
                "content" to "텍스트 그리고 \n<image>$url</image>",
                "imageUrl" to url,
                "createdAt" to Timestamp(Date())
            )

            Firebase.firestore
                .collection("posts")
                .document()
                .set(post)
                .await()
        } catch (exception: Exception) {

        }
    }

    companion object {
        private const val TAG = "marchlab"

        private const val REQUEST_CODE = 33
    }
}

/*
<collection>
users
    <document>
    alovelace
        <data>
        first : ada
        last : lovelace
        born : 1895
        </data>
    aturing
        first : ada
        last : lovelace
        born : 1895
    </document>
</collection>
*/