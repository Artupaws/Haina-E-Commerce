package haina.ecommerce.room.roomphotoproperty

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DataProperty::class], version = 2, exportSchema = false)
abstract class RoomDataProperty :RoomDatabase(){

    companion object {
        @Volatile
        private var INSTANCE: RoomDataProperty? = null

        fun getDatabase(context: Context):RoomDataProperty{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, RoomDataProperty::class.java, "property_db"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    abstract fun getDataPropertyDao():PropertyDao

}