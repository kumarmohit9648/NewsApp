package com.knovatik.navadesh.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import cn.jzvd.JZDataSource
import cn.jzvd.Jzvd
import com.bumptech.glide.Glide
import com.knovatik.navadesh.R
import com.knovatik.navadesh.model.section.Data
import com.knovatik.navadesh.ui.activity.JokesActivity
import kotlinx.android.synthetic.main.recycler_audio.view.*
import kotlinx.android.synthetic.main.recycler_jokes.view.*
import kotlinx.android.synthetic.main.recycler_video.view.*

class JokesAdapter(
    private var context: Context,
    private var list: List<Data>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TAG = "JokesAdapter"
    }

    private var currentMusicId = 0

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
                        R.layout.recycler_video,
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
        try {
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
                    holder.itemView.musicName.text = HtmlCompat.fromHtml(
                        data.content,
                        HtmlCompat.FROM_HTML_MODE_LEGACY
                    )

                    if (data.isPlay == 0)
                        Glide.with(context).load(R.drawable.ic_play_button)
                            .into(holder.itemView.ivPlay)
                    else
                        Glide.with(context).load(R.drawable.ic_pause_button)
                            .into(holder.itemView.ivPlay)

                    holder.itemView.setOnClickListener {
                        if (JokesActivity.mediaPlayer != null) {
                            if (JokesActivity.mediaPlayer?.isPlaying!!) {
                                JokesActivity.mediaPlayer?.stop()
                            }
                        }
                        if (currentMusicId != position) {
                            JokesActivity.mediaPlayer = MediaPlayer().apply {
                                setAudioAttributes(
                                    AudioAttributes.Builder()
                                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                                        .setUsage(AudioAttributes.USAGE_MEDIA)
                                        .build()
                                )
                                setDataSource(data.content_url)
                                prepare() // might take long! (for buffering, etc)
                            }
                            JokesActivity.mediaPlayer?.start()
                            list[currentMusicId].isPlay = 0
                            list[position].isPlay = 1
                            currentMusicId = position
                        } else {
                            list[currentMusicId].isPlay = 0
                        }
                        notifyDataSetChanged()
                    }
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
        } catch (e: Exception) {
            Log.e(TAG, "onBindViewHolder: " + e.message)
        }
    }

    override fun getItemCount(): Int = list.size

}

class ContentHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class VideoHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class MusicHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
class ImageHolder(itemView: View) : RecyclerView.ViewHolder(itemView)