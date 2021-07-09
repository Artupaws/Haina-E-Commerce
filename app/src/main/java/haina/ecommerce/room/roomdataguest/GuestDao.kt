package haina.ecommerce.room.roomdataguest

import androidx.room.*
import haina.ecommerce.model.hotels.newHotel.DataGuest

@Dao
interface GuestDao {

    @Insert
    fun insert(dataPassenger: DataGuest)

    @Update
    fun update(dataPassenger: DataGuest)

    @Delete
    fun delete(dataPassenger: DataGuest)

    @Query("SELECT * FROM dataguest")
    fun getAll() : List<DataGuest>

    @Query("SELECT * FROM dataguest WHERE id = :id")
    fun getById(id: Int) : List<DataGuest>

    @Query("DELETE FROM dataguest")
    fun deleteAll()
}