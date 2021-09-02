package haina.ecommerce.room.roomvideopost

import androidx.room.*
import haina.ecommerce.model.forum.ImagePostData

@Dao
interface VideoPostDao {

    @Insert
    fun insert(videoPost: VideoPostData)

    @Update
    fun update(videoPost: VideoPostData)

    @Delete
    fun delete(videoPost: VideoPostData)

    @Query("SELECT * FROM tableVideoPost")
    fun getAll() : MutableList<VideoPostData>

    @Query("SELECT * FROM tableVideoPost WHERE id = :id")
    fun getById(id: Int) : MutableList<VideoPostData>

    @Query("DELETE FROM tableVideoPost")
    fun deleteAll()

}