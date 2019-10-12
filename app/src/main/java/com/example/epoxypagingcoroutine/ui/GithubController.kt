package com.example.epoxypagingcoroutine.ui

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging.PagedListEpoxyController
import com.example.epoxypagingcoroutine.data.model.Owner
import com.example.epoxypagingcoroutine.data.model.Repo
import com.example.epoxypagingcoroutine.ui.epoxy_model.OwnerModel_
import com.example.epoxypagingcoroutine.ui.epoxy_model.RepositoryModel_

class GithubController: PagedListEpoxyController<Repo>() {

    var owners: List<Owner> = emptyList()

    override fun buildItemModel(currentPosition: Int, item: Repo?): EpoxyModel<*> {
        requireNotNull(item)
        return RepositoryModel_().apply {
            id(currentPosition)
            repo = item
        }
    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        CarouselModel_()
            .id("carousel")
            .spanSizeOverride { _, _, _ -> 2 }
            .paddingDp(8)
            .numViewsToShowOnScreen(2f)
            .models(
                owners.map {
                    OwnerModel_()
                        .id(it.id)
                        .owner(it)
                }
            ).addTo(this)

        super.addModels(models)
    }
}
