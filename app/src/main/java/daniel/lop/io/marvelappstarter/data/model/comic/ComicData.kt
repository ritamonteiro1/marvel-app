package daniel.lop.io.marvelappstarter.data.model.comic

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicData(
    @SerializedName("results")
    val result: List<Comic>
) : Serializable
