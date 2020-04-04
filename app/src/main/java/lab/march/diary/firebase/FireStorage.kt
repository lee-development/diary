package lab.march.diary.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

fun main() {
    val storage = Firebase.storage

    // create a storage reference from this app
    val reference = storage.reference
    val imagesReference = reference.child("images")
    val spaceReference = reference.child("images/space.jpg")
    spaceReference.path /* images/space.jpg */
    spaceReference.name /* space.jpg */
    spaceReference.parent /* points to images */
}