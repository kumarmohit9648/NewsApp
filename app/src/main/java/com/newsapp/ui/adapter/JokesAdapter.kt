package com.newsapp.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.JZDataSource
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import com.newsapp.R
import com.newsapp.model.section.Data
import com.newsapp.util.Helper
import kotlinx.android.synthetic.main.recycler_audio.view.*
import kotlinx.android.synthetic.main.recycler_jokes.view.*
import kotlinx.android.synthetic.main.recycler_video.view.*

class JokesAdapter(
    private var context: Context,
    private var list: List<Data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            // CONTENT
            1 -> {
                return ContentHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_jokes,
                        parent,
                        false
                    )
                )
            }
            // AUDIO
            2 -> {
                return MusicHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_audio,
                        parent,
                        false
                    )
                )
            }
            // VIDEO
            3 -> {
                return VideoHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_video,
                        parent,
                        false
                    )
                )
            }
            // IMAGE
            4 -> {
                return ImageHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_image,
                        parent,
                        false
                    )
                )
            }
            // DEFAULT
            else -> {
                return VideoHolder(
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.recycler_jokes,
                        parent,
                        false
                    )
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = list[position]
        when (holder.itemViewType) {
            // CONTENT
            1 -> {
                holder.itemView.view.setBackgroundColor(Color.parseColor(data.color))
                holder.itemView.tvJokes.text = HtmlCompat.fromHtml(
                    data.content,
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                holder.itemView.ivShare.setOnClickListener {
                    val shareIntent = Intent()
                    shareIntent.action = Intent.ACTION_SEND
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_TEXT, data.link)
                    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
                }
            }
            // AUDIO
            2 -> {
                holder.itemView.musicName.text = data.content
            }
            // VIDEO
            3 -> {
                val jzDataSource = JZDataSource(data.content_url)
                jzDataSource.looping = true
                holder.itemView.videoPlayer.setUp(jzDataSource, Jzvd.SCREEN_NORMAL)
                // Glide.with(context).load(Helper.retrieveVideoFrameFromVideo(data.video_url)).into(holder.itemView.videoPlayer.posterImageView)
            }
            // IMAGE
            4 -> {
                // holder.itemView
            }
            // DEFAULT
            else -> {
                val jzDataSource = JZDataSource(data.content_url)
                jzDataSource.looping = true
                holder.itemView.videoPlayer.setUp(jzDataSource, Jzvd.SCREEN_NORMAL)
                // Glide.with(context).load(Helper.retrieveVideoFrameFromVideo(data.video_url)).into(holder.itemView.videoPlayer.posterImageView)
            }
        }
    }

    override fun getItemCount(): Int = list.size

}

class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class MusicHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView)