package haina.ecommerce.roomdatapassenger

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataPassenger::class], version = 9, exportSchema = false)
abstract class RoomDataPassenger :RoomDatabase(){

    companion object {

        @Volatile
        private var INSTANCE: RoomDataPassenger? = null

        fun getDatabase(context: Context):RoomDataPassenger{
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, RoomDataPassenger::class.java,"passenger_db"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

    abstract fun getDataPassengerDao():PassengerDao

}