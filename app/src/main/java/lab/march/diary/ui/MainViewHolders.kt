package lab.march.diary.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView

sealed class MainViewHolder<T: MainItem>(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: T)
}

sealed class MainItem {

    data class Post(val id: String, val content: String)

    data class Movie(val id: String, val title: String)

    data class Book(val id: String, val title: String)
}