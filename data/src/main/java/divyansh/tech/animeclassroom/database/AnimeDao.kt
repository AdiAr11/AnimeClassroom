package divyansh.tech.animeclassroom.database

import androidx.lifecycle.LiveData
import androidx.room.*
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel

@Dao
interface AnimeDao {
    @Query("SELECT * FROM animes")
    suspend fun getAllAnimes(): List<OfflineAnimeModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnime(anime: OfflineAnimeModel)

    @Query("SELECT EXISTS(SELECT 1 FROM animes WHERE animeUrl = :animeUrl LIMIT 1)")
    suspend fun isAnimeWithUrlSaved(animeUrl: String): Boolean
}