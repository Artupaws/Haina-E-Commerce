package haina.ecommerce.room.roomsavedproperty

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataSavedProperty::class], version = 3, exportSchema = false)
abstract class RoomDataSavedProperty :RoomDatabase(){

    companion object {
        @Volatile
        private var INSTANCE: RoomDataSavedProperty? = null

        fun getDatabase(context: Context):RoomDataSavedProperty{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, RoomDataSavedProperty::class.java, "property_db"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getDataPropertyDao():SavedPropertyDao

}