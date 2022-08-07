package br.com.wellintonvieira.githubportifolio.domain.usecases

import br.com.wellintonvieira.githubportifolio.data.models.Repo
import br.com.wellintonvieira.githubportifolio.data.repositories.RepoRepository
import br.com.wellintonvieira.githubportifolio.util.UseCase

class ListUserRepositoryUseCase(private val repository: RepoRepository): UseCase<String, List<Repo>>() {

    override suspend fun execute(param: String) = repository.getRepositories(param)
}