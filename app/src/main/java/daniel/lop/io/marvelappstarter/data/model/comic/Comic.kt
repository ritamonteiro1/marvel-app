package daniel.lop.io.marvelappstarter.data.model.comic

import daniel.lop.io.marvelappstarter.data.model.character.Thumbnail
import java.io.Serializable

data class Comic(
    val id: Int,
    val title: String,
    val description: String,
    val thumbnail: Thumbnail
) : Serializable
