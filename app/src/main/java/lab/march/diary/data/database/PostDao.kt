package lab.march.diary.data.database

import androidx.room.*

@Dao
interface PostDao {

    @Query("SELECT * FROM posts")
    suspend fun loadPosts(): List<Post>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: Post)

    @Update
    suspend fun updatePost(post: Post)

    @Delete
    suspend fun deletePost(post: Post)
}