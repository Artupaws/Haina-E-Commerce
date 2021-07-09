package haina.ecommerce.room.roomdatapassenger

import androidx.room.*

@Dao
interface PassengerDao {

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