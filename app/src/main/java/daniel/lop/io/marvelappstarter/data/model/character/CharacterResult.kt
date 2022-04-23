package daniel.lop.io.marvelappstarter.data.model.character

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CharacterResult(
    @SerializedName("data")
    val data: CharacterData) : Serializable
