package daniel.lop.io.marvelappstarter.data.model.character

import java.io.Serializable

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
) : Serializable