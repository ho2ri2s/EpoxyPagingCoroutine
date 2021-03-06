package com.example.epoxypagingcoroutine.ui.epoxy_model

import android.view.View
import androidx.databinding.DataBindingUtil
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyHolder
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.example.epoxypagingcoroutine.R
import com.example.epoxypagingcoroutine.databinding.ItemOwnerBinding
import java.lang.IllegalStateException
import com.example.epoxypagingcoroutine.data.model.Owner

@EpoxyModelClass(layout = R.layout.item_owner)
abstract class OwnerModel : EpoxyModelWithHolder<OwnerModel.OwnerViewHolder>() {

    @EpoxyAttribute
    lateinit var owner: Owner

    @EpoxyAttribute
    lateinit var cardClickListener: (String) -> Unit

    override fun bind(holder: OwnerViewHolder) {
        super.bind(holder)
        holder.binding.owner = owner
        holder.binding.containerCard.setOnClickListener{
            cardClickListener(owner.name)
        }
    }

    inner class OwnerViewHolder : EpoxyHolder() {
        override fun bindView(itemView: View) {
            binding = DataBindingUtil.bind(itemView) ?: run {
                throw IllegalStateException("Cannot create binding.")
            }
        }

        lateinit var binding: ItemOwnerBinding
    }
}