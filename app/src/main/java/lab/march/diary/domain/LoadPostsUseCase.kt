package lab.march.diary.domain

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import lab.march.diary.core.usecase.UseCase

class LoadPostsUseCase: UseCase<Unit, Unit>() {

    override suspend fun execute(parameters: Unit) {

        FirebaseAuth.getInstance()
            .signInAnonymously()
            .await()

        try {
            val posts = FirebaseFirestore.getInstance()
                .collection("posts")
                .get()
                .await()
        } catch (exception: Exception) {

        }

    }
}