package haina.ecommerce.room.roomdataguest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import haina.ecommerce.model.hotels.newHotel.DataGuest

@Database(entities = [DataGuest::class], version = 2, exportSchema = false)
abstract class RoomDataGuest :RoomDatabase(){

    companion object {

        @Volatile
        private var INSTANCE: RoomDataGuest? = null

        fun getDatabase(context: Context): RoomDataGuest {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, RoomDataGuest::class.java,"guest_db"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

    abstract fun getGuestDao(): GuestDao

}