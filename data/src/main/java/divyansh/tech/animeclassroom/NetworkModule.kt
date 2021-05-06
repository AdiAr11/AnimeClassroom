package divyansh.tech.animeclassroom

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import divyansh.tech.animeclassroom.C.BASE_URL
import divyansh.tech.animeclassroom.api.HomeScreenApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
* Dependency container for the Networking facilities
* */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    /*
    * Provide retrofit instance
    * */
    fun provideRetrofitInstance(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    /*
    * Provides homeScreenApi
    * @param retrofit: Retrofit
    * @returns -> HomeScreenApi service
    * */
    fun providesHomeScreenApi(
        retrofit: Retrofit
    ): HomeScreenApi = retrofit.create(HomeScreenApi::class.java)
}