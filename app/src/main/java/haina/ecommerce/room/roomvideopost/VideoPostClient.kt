package haina.ecommerce.room.roomvideopost

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import haina.ecommerce.model.forum.ImagePostData

@Database(entities = [VideoPostData::class], version = 1, exportSchema = false)
abstract class VideoPostClient:RoomDatabase() {

    companion object{

        @Volatile
        private var INSTANCE: VideoPostClient? = null

        fun getDatabase(context: Context): VideoPostClient {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext, VideoPostClient::class.java,"videopost_db"
                ).allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
    abstract fun getVideoPostDao():VideoPostDao
}