package br.com.wellintonvieira.githubportifolio.data.di

import android.util.Log
import br.com.wellintonvieira.githubportifolio.data.repositories.RepoRepository
import br.com.wellintonvieira.githubportifolio.data.repositories.RepoRepositoryImpl
import br.com.wellintonvieira.githubportifolio.data.services.GithubService
import br.com.wellintonvieira.githubportifolio.util.Constants
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataModule {

    fun load() {
        loadKoinModules(networkModules() + repositoryModules())
    }

    private fun networkModules(): Module {
        return module {
            single {
                val interceptor = HttpLoggingInterceptor {
                    Log.d(Constants.TAG, "networkModules: $it")
                }
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                OkHttpClient.Builder()
                    .addInterceptor(interceptor)
                    .build()
            }

            single {
                GsonConverterFactory.create(GsonBuilder().create())
            }
            single {
                createService<GithubService>(get(), get())
            }
        }
    }

    private fun repositoryModules(): Module {
        return module {
            single<RepoRepository> {
                RepoRepositoryImpl(get())
            }
        }
    }

    private inline fun <reified T> createService(client: OkHttpClient, factory: GsonConverterFactory): T {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL).client(client)
            .addConverterFactory(factory).build().create(T::class.java)
    }
}