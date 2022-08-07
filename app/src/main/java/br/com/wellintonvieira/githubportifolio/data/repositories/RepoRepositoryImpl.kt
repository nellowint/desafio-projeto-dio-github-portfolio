package br.com.wellintonvieira.githubportifolio.data.repositories

import br.com.wellintonvieira.githubportifolio.R
import br.com.wellintonvieira.githubportifolio.data.services.GithubService
import br.com.wellintonvieira.githubportifolio.util.RemoteException
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException

class RepoRepositoryImpl(private val service: GithubService): RepoRepository {

    override suspend fun getRepositories(user: String) = flow {
        try {
            val repoList = service.getRepositories(user)
            emit(repoList)
        } catch (exception: HttpException) {
            throw RemoteException(exception.message ?: "${R.string.network_error}")
        }
    }
}