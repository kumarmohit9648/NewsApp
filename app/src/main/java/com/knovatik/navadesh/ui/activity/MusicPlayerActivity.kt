package com.knovatik.navadesh.ui.activity

import android.net.Uri
import android.os.Bundle
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import com.knovatik.navadesh.R
import com.knovatik.navadesh.ui.BaseActivity
import kotlinx.android.synthetic.main.activity_music_player.*

class MusicPlayerActivity : BaseActivity() {

    private lateinit var simpleExoPlayer: SimpleExoPlayer
    private lateinit var mediaSource: MediaSource
    private lateinit var dataSourceFactory: DefaultDataSourceFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_music_player)

        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this)

        dataSourceFactory =
            DefaultDataSourceFactory(this, Util.getUserAgent(this, "exoPlayerSample"))

        mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(Uri.parse("RADIO_URL"))

        with(simpleExoPlayer) {
            prepare(mediaSource)
            btnStart.setOnClickListener {
                playWhenReady = true
            }

            btnStop.setOnClickListener {
                playWhenReady = false
            }
        }
    }

    override fun onDestroy() {
        simpleExoPlayer.playWhenReady = false
        super.onDestroy()
    }

    companion object {
        var RADIO_URL = "http://kastos.cdnstream.com/1345_32"
    }
}