package com.knovatik.navadesh.model.media

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import java.util.*

data class Media(
    val id: Long,
    val displayName: String,
    val dateAdded: Date,
    val contentUri: Uri
) {
    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Media>() {
            override fun areItemsTheSame(oldItem: Media, newItem: Media) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Media, newItem: Media) =
                oldItem == newItem
        }
    }
}