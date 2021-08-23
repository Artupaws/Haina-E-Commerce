package haina.ecommerce.room.roomimagepost

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import haina.ecommerce.model.forum.ImagePostData

@Database(entities = [ImagePostData::class], version = 4, exportSchema = false)
abstract class ImagePostClient:RoomDatabase() {

    companion object{

        @Volatile
        private var INSTANCE: ImagePostClient? = null

        fun getDatabase(context: Context): ImagePostClient {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, ImagePostClient::class.java,"imagepost_db"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    abstract fun getImagePostDao():ImagePostDao
}