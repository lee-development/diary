package lab.march.diary.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "posts")
data class Post(
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Long,
    @ColumnInfo(name = "updated_at")
    val updatedAt: Long
)