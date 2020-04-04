package lab.march.diary.ui

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Timestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
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

        firestore.collection("posts")
            .add(post)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "document snapshot written with ID: ${documentReference.id}")
                Toast.makeText(this, "document snapshot written with ID: ${documentReference.id}", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "error writing document ${exception.message}")
                Toast.makeText(this, "error writing document ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun pickImage() = startActivityForResult(
        Intent(Intent.ACTION_PICK).apply { type = "image/*" },
        REQUEST_CODE
    )

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                val uri = data?.data
                uri?.let { uploadImage(it) }
            } else {

            }
        }
    }

    private fun uploadImage(uri: Uri) {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.KOREA).format(Date())
        val name = timestamp.plus(".png")

        val storage = Firebase.storage
        val reference = storage.reference.child("images/$name")

        reference.putFile(uri).addOnCompleteListener { task ->
            if(task.isSuccessful) {
                reference.downloadUrl.addOnSuccessListener {
                    val firestore = Firebase.firestore
                    firestore.collection("posts")
                        .document()
                        .set("data")
                        .addOnCompleteListener { task ->
                            if(task.isSuccessful) {

                            } else {

                            }
                        }
                }
            } else {
                task.exception?.let { /* handle exception */ }
            }
        }
        reference.putFile(uri).addOnSuccessListener { snapshot ->

                val timestamp = System.currentTimeMillis()
                reference.downloadUrl
                    .addOnSuccessListener {
                        Firebase.firestore
                            .collection("posts")
                            .add("")
                    }
            }
            .addOnFailureListener { exception ->

            }
    }

    private fun savePost(imageUri: Uri) {

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