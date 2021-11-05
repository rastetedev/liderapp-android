package com.liderman.mundolidermanapp.presentation.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.liderman.mundolidermanapp.R
import com.liderman.mundolidermanapp.domain.entity.lidernet.LiderNetEntity
import com.liderman.mundolidermanapp.presentation.ui.application.LiderManApplication
import java.util.regex.Pattern


class YoutubeViewerActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {

    private lateinit var liderNetEntity: LiderNetEntity
    private lateinit var videoId: String
    //val youtube_regex = "/^.*((m\\.)?youtu\\.be\\/|vi?\\/|u\\/\\w\\/|embed\\/|\\?vi?=|\\&vi?=)([^#\\&\\?]*).*/"

    private lateinit var player: YouTubePlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_youtube_viewer)
        LiderManApplication.addActivity(this)

        liderNetEntity = intent.getSerializableExtra("extra0") as LiderNetEntity

        videoId = getYouTubeId(liderNetEntity.lidernet.mainVideoUrl)!!


        val layout = layoutInflater.inflate(R.layout.activity_youtube_viewer, null) as ConstraintLayout
        setContentView(layout)

        val playerView = YouTubePlayerView(this)
        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        layout.addView(playerView)

        playerView.initialize("AIzaSyCmOrjDGuN9PDyT8PSJOEVjjZEEOpE5jdo", this)
    }

    private fun getYouTubeId(youTubeUrl: String): String? {
        val pattern = "https?://(?:[0-9A-Z-]+\\.)?(?:youtu\\.be/|youtube\\.com\\S*[^\\w\\-\\s])([\\w\\-]{11})(?=[^\\w\\-]|$)(?![?=&+%\\w]*(?:['\"][^<>]*>|</a>))[?=&+%\\w]*"
        val compiledPattern = Pattern.compile(
            pattern,
            Pattern.CASE_INSENSITIVE
        )
        val matcher = compiledPattern.matcher(youTubeUrl)
        return if (matcher.find()) {
            matcher.group(1)
        } else null
    }

    override fun onDestroy() {
        super.onDestroy()
        LiderManApplication.removeActivity(this)

        try {
            val bundle = Bundle().apply {
                putString("duration", player.currentTimeMillis.toString())
            }
        }
        catch (e: Exception) {
            Log.wtf("YoutubeViewerActivity", "onDestroy() called", e)
        }
    }


    override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, player: YouTubePlayer, wasRestored: Boolean) {
        player.setPlayerStateChangeListener(playerStateChangeListener)
        player.setPlaybackEventListener(playbackEventListener)

        if (!wasRestored) {
            player.cueVideo(videoId)
        }

        this.player = player
    }

    override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {

    }

    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener {

        override fun onBuffering(arg0: Boolean) {}

        override fun onPaused() {}

        override fun onPlaying() {}

        override fun onSeekTo(arg0: Int) {}

        override fun onStopped() {}

    }

    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {

        override fun onAdStarted() {}

        override fun onError(arg0: YouTubePlayer.ErrorReason) {}

        override fun onLoaded(arg0: String) {
            if (player != null) {
                player?.play()
            }
        }

        override fun onLoading() {}

        override fun onVideoEnded() {
            val intent = Intent(this@YoutubeViewerActivity, QuestionnaireActivity::class.java)
            intent.putExtra("extra0", liderNetEntity)
            startActivity(intent)
            this@YoutubeViewerActivity.finish()
        }

        override fun onVideoStarted() {}
    }
}
