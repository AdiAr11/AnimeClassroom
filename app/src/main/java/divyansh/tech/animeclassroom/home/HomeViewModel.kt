package divyansh.tech.animeclassroom.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import divyansh.tech.animeclassroom.ResultWrapper
import divyansh.tech.animeclassroom.common.CommonViewModel
import divyansh.tech.animeclassroom.di.DispatcherModule
import divyansh.tech.animeclassroom.home.dataModels.*
import divyansh.tech.animeclassroom.utils.HomeTypes
import divyansh.tech.animeclassroom.models.home.AnimeModel
import divyansh.tech.animeclassroom.models.home.GenreModel
import divyansh.tech.animeclassroom.models.home.OfflineAnimeModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repo: HomeDefaultRepo,
    @DispatcherModule.IODispatcher val coroutineDispatcher: CoroutineDispatcher
): CommonViewModel() {

    private val _animeList: MutableLiveData<ResultWrapper<ArrayList<HomeMainModel>>> = MutableLiveData()
    val animeList: LiveData<ResultWrapper<ArrayList<HomeMainModel>>> get() = _animeList

    private val _list: ArrayList<HomeMainModel> = arrayListOf()

    init {
        getRecentReleases()
        getPopularAnimes()
        getNewSeasons()
        getPopularMovies()
        getGenres()
    }

    private fun getPopularAnimes() {
        viewModelScope.launch(coroutineDispatcher) {
            _animeList.postValue(ResultWrapper.Loading())
            val response = repo.parsePopularAnimes()
            response.collect {
                if (it is ResultWrapper.Success) {
                    val model = HomeMainModel(
                        type = HomeTypes.POPULAR_ANIME,
                        feedResult = it.data as ArrayList<AnimeModel>)
                    _list.add(model)
                    _animeList.postValue(ResultWrapper.Success(_list))
                }
                else
                    _animeList.postValue(ResultWrapper.Error(it.message.toString()))
            }
        }
    }

    private fun getRecentReleases() {
        viewModelScope.launch(coroutineDispatcher) {
            _animeList.postValue(ResultWrapper.Loading())
            val response = repo.parseRecentReleases()
            response.collect {
                if (it is ResultWrapper.Success){
                    val model = HomeMainModel(
                        type = HomeTypes.RECENT_RELEASE,
                        feedResult = it.data as ArrayList<AnimeModel>)
                    _list.add(model)
                    _animeList.postValue(ResultWrapper.Success(_list))
                }
                else
                    _animeList.postValue(ResultWrapper.Error(it.message.toString()))
            }
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch(coroutineDispatcher) {
            _animeList.postValue(ResultWrapper.Loading())
            val response = repo.parsePopularMovies()
            response.collect {
                if (it is ResultWrapper.Success){
                    val model = HomeMainModel(
                        type = HomeTypes.POPULAR_MOVIES,
                        feedResult = it.data as ArrayList<AnimeModel>)
                    _list.add(model)
                    _animeList.postValue(ResultWrapper.Success(_list))
                }
                else
                    _animeList.postValue(ResultWrapper.Error(it.message.toString()))
            }
        }
    }

    private fun getNewSeasons() {
        viewModelScope.launch(coroutineDispatcher) {
            _animeList.postValue(ResultWrapper.Loading())
            val response = repo.parseNewSeasons()
            response.collect {
                if (it is ResultWrapper.Success){
                    val model = HomeMainModel(
                        type = HomeTypes.NEW_SEASON,
                        feedResult = it.data as ArrayList<AnimeModel>)
                    _list.add(model)
                    _animeList.postValue(ResultWrapper.Success(_list))
                }
                else
                    _animeList.postValue(ResultWrapper.Error(it.message.toString()))
            }
        }
    }

    private fun getGenres() {
        viewModelScope.launch(coroutineDispatcher) {
            _animeList.postValue(ResultWrapper.Loading())
            val response = repo.parseGenres()
            response.collect {
                if (it is ResultWrapper.Success){
                    val model = HomeMainModel(
                        type = HomeTypes.GENRES,
                        feedResult = it.data as ArrayList<GenreModel>)
                    _list.add(model)
                    _animeList.postValue(ResultWrapper.Success(_list))
                }
                else
                    _animeList.postValue(ResultWrapper.Error(it.message.toString()))
            }
        }
    }
}