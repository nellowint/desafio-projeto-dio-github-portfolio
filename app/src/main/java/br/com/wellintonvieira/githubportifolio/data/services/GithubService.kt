package br.com.wellintonvieira.githubportifolio.data.services

import br.com.wellintonvieira.githubportifolio.data.models.Repo
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {
    @GET("users/{user}/repos")
    suspend fun getRepositories(@Path("user") user: String): List<Repo>
}