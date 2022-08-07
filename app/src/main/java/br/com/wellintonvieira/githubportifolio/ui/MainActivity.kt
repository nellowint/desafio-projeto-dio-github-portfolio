package br.com.wellintonvieira.githubportifolio.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.wellintonvieira.githubportifolio.R
import br.com.wellintonvieira.githubportifolio.databinding.ActivityMainBinding
import br.com.wellintonvieira.githubportifolio.presentation.viewmodels.MainViewModel
import br.com.wellintonvieira.githubportifolio.util.createDialog
import br.com.wellintonvieira.githubportifolio.util.createProgressDialog
import br.com.wellintonvieira.githubportifolio.util.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val dialog by lazy { createProgressDialog() }
    private val repoListAdapter by lazy { RepoListAdapter() }
    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.materialToolbar)
        configureRecyclerView()
        configureObserver()
    }

    private fun configureRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
            adapter = repoListAdapter
        }
    }

    private fun configureObserver() {
        viewModel.repoList.observe(this) {
            when(it) {
                MainViewModel.State.Loading -> {
                    dialog.show()
                }
                is MainViewModel.State.Error -> {
                    createDialog {
                        setMessage(it.error.message)
                    }.show()
                    dialog.dismiss()
                }
                is MainViewModel.State.Success -> {
                    dialog.dismiss()
                    repoListAdapter.submitList(it.list)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        val search = menu.findItem(R.id.menu_search).actionView as SearchView
        search.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let { viewModel.getRepositories(it) }
        binding.root.hideKeyboard()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }
}