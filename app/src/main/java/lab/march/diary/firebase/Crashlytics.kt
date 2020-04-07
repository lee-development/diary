package lab.march.diary.firebase

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.Button
import com.crashlytics.android.Crashlytics

fun main(context: Context, activity: Activity) {
    val button = Button(context)
    button.text = "crash"
    button.setOnClickListener { Crashlytics.getInstance().crash() }

    activity.addContentView(
        button,
        ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    )
}