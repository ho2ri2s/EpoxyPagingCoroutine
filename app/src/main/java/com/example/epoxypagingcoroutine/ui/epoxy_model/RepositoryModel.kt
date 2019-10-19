package com.example.epoxypagingcoroutine.ui.epoxy_model

import android.view.View
import androidx.databinding.DataBindingUtil
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.epoxypagingcoroutine.R
import com.example.epoxypagingcoroutine.data.model.Repo
import com.example.epoxypagingcoroutine.databinding.ItemRepositoryBinding
import java.lang.IllegalStateException

@EpoxyModelClass(layout = R.layout.item_repository)
abstract class RepositoryModel : EpoxyModelWithHolder<RepositoryModel.RepositoryViewHolder>() {

    @EpoxyAttribute
    lateinit var repo: Repo

    override fun bind(holder: RepositoryViewHolder) {
        super.bind(holder)
        holder.binding.repo = repo
    }

    inner class RepositoryViewHolder : EpoxyHolder() {
        override fun bindView(itemView: View) {
            binding = DataBindingUtil.bind(itemView) ?: kotlin.run {
                throw IllegalStateException("Cannot create binding.")
            }
        }

        lateinit var binding: ItemRepositoryBinding
    }
}