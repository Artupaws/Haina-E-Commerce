package haina.ecommerce.room.roomimagepost

import androidx.room.*
import haina.ecommerce.model.forum.ImagePostData

@Dao
interface ImagePostDao {

    @Insert
    fun insert(imagePost: ImagePostData)

    @Update
    fun update(imagePost: ImagePostData)

    @Delete
    fun delete(imagePost: ImagePostData)

    @Query("SELECT * FROM tableImagePost")
    fun getAll() : MutableList<ImagePostData>

    @Query("SELECT * FROM tableImagePost WHERE id = :id")
    fun getById(id: Int) : MutableList<ImagePostData>

    @Query("DELETE FROM tableImagePost")
    fun deleteAll()

}