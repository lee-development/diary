package lab.march.diary

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

class DiaryApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        if(BuildConfig.DEBUG) FirebaseFirestore.setLoggingEnabled(true)
    }
}