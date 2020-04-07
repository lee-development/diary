package lab.march.diary.domain

import android.net.Uri

class UploadImageUseCase{

}

data class UploadImageParams(
    val name: String,
    val uri: Uri
)