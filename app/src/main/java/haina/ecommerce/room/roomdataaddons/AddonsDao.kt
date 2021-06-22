package haina.ecommerce.room.roomdataaddons

import androidx.room.*
import haina.ecommerce.room.roomdatapassenger.DataPassenger

@Dao
interface AddonsDao {

    @Insert
    fun insert(dataPassenger: DataPassenger)

    @Update
    fun update(dataPassenger: DataPassenger)

    @Delete
    fun delete(dataPassenger: DataPassenger)

    @Query("SELECT * FROM datapassenger")
    fun getAll() : List<DataPassenger>

    @Query("SELECT * FROM datapassenger WHERE id = :id")
    fun getById(id: Int) : List<DataPassenger>

    @Query("DELETE FROM datapassenger")
    fun deleteAll()
}