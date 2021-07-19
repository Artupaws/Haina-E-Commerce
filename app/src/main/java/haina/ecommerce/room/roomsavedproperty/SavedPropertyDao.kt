package haina.ecommerce.room.roomsavedproperty

import androidx.room.*

@Dao
interface SavedPropertyDao {

    @Insert
    fun insert(dataProperty: DataSavedProperty)

    @Update
    fun update(dataProperty: DataSavedProperty)

    @Delete
    fun delete(dataProperty: DataSavedProperty)

    @Query("SELECT * FROM datasavedproperty")
    fun getAll() : List<DataSavedProperty>

    @Query("SELECT * FROM datasavedproperty WHERE id = :id")
    fun getById(id: Int) : List<DataSavedProperty>

    @Query("DELETE FROM datasavedproperty")
    fun deleteAll()
}