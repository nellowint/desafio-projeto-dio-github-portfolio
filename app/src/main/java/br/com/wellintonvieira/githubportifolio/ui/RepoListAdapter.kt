package br.com.wellintonvieira.githubportifolio.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.wellintonvieira.githubportifolio.data.models.Repo
import br.com.wellintonvieira.githubportifolio.databinding.ItemRepoBinding
import com.bumptech.glide.Glide

class RepoListAdapter : ListAdapter<Repo, RepoListAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemRepoBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: ItemRepoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Repo) {
            binding.textViewRepoName.text = item.name
            binding.textViewRepoDescription.text = item.description
            binding.textViewRepoLanguage.text = item.language
            binding.chipStars.text = item.stargazersCount.toString()
            Glide.with(binding.root.context)
                .load(item.owner.avatarURL).into(binding.imageViewOwnerAvatar)
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
    override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id
}