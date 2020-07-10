package ua.skrypin.youtubeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.player_activity.*
import kr.co.prnd.YouTubePlayerView


class PlayerActivity : AppCompatActivity() {

    var video_id = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_activity)

        val intent = intent
        video_id = intent.getStringExtra("video_id")
        this_video_description.text = intent.getStringExtra("video_description")

        val youTubePlayerView = findViewById<YouTubePlayerView>(R.id.you_tube_player_view)
        youTubePlayerView.play(video_id)
    }


}