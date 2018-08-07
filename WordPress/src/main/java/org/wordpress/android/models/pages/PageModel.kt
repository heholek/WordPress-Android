package org.wordpress.android.models.pages

import org.wordpress.android.fluxc.model.PostModel
import org.wordpress.android.fluxc.model.SiteModel
import org.wordpress.android.util.DateTimeUtils
import java.text.DateFormat
import java.util.Date

data class PageModel(
    val site: SiteModel,
    val pageId: Int,
    val title: String,
    var status: PageStatus,
    var date: Date,
    var hasLocalChanges: Boolean,
    val parentId: Long,
    val remoteId: Long
) {
    var parent: PageModel? = null

    constructor(post: PostModel, site: SiteModel) : this(site, post.id, post.title, PageStatus.fromPost(post),
            Date(DateTimeUtils.timestampFromIso8601Millis(post.dateCreated)), post.isLocallyChanged, post.parentId,
            post.remotePostId)
}

fun PostModel.updatePageData(page: PageModel) {
    this.id = page.pageId
    this.title = page.title
    this.status = page.status.toPostStatus().toString()
    this.parentId = page.parentId
    this.remotePostId = page.remoteId
}
