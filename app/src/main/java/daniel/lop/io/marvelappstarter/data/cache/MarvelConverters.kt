package daniel.lop.io.marvelappstarter.data.cache

import androidx.room.TypeConverter
import com.google.gson.Gson
import daniel.lop.io.marvelappstarter.data.model.character.ThumbnailModel

class MarvelConverters {
    @TypeConverter
    fun fromThumbnail(thumbnailModel: ThumbnailModel): String = Gson().toJson(thumbnailModel)

    @TypeConverter
    fun toThumbnail(thumbnailModel: String): ThumbnailModel =
        Gson().fromJson(thumbnailModel, ThumbnailModel::class.java)
}