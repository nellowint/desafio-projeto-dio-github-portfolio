package br.com.wellintonvieira.githubportifolio.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.wellintonvieira.githubportifolio.data.models.Repo
import br.com.wellintonvieira.githubportifolio.domain.usecases.ListUserRepositoryUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class MainViewModel(private val listUserRepositoryUseCase: ListUserRepositoryUseCase) : ViewModel() {

    private val _repoList = MutableLiveData<State>()
    val repoList: LiveData<State> = _repoList

    fun getRepositories(user: String) {
        viewModelScope.launch {
            listUserRepositoryUseCase(user)
                .onStart {
                    _repoList.postValue(State.Loading)
                }
                .catch {
                    _repoList.postValue(State.Error(it))
                }
                .collect {
                    _repoList.postValue(State.Success(it))
                }
        }
    }

    sealed class State {
        object Loading : State()
        data class Success(val list: List<Repo>) : State()
        data class Error(val error: Throwable) : State()
    }
}