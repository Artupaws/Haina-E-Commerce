package haina.ecommerce.room.roomphotoproperty

import androidx.room.*

@Dao
interface PropertyDao {

    @Insert
    fun insert(dataProperty: DataProperty)

    @Update
    fun update(dataProperty: DataProperty)

    @Delete
    fun delete(dataProperty: DataProperty)

    @Query("SELECT * FROM dataproperty")
    fun getAll() : List<DataProperty>

    @Query("SELECT * FROM dataproperty WHERE id = :id")
    fun getById(id: Int) : List<DataProperty>

    @Query("DELETE FROM dataproperty")
    fun deleteAll()
}