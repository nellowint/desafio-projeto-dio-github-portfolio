package br.com.wellintonvieira.githubportifolio.data.repositories

import br.com.wellintonvieira.githubportifolio.data.models.Repo
import kotlinx.coroutines.flow.Flow

interface RepoRepository {
    suspend fun getRepositories(user: String): Flow<List<Repo>>
}