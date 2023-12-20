package com.project.myrecyclerview

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val imgphoto: ImageView = findViewById(R.id.img_gambar)
        val judul: TextView = findViewById(R.id.tv_Judul)
        val description: TextView = findViewById(R.id.tv_description)
        val playButton:ImageView=findViewById(R.id.tombol_play)

        val receivedData = intent.getParcelableExtra<Video>("shadow")

        if (receivedData != null) {
            imgphoto.setImageResource(receivedData.photo)
            judul.text = receivedData.name
            description.text = receivedData.description

            // Inisialisasi MediaPlayer dengan audio yang sesuai dari data yang dipilih
            mediaPlayer = MediaPlayer.create(this, receivedData.audioResource)

            playButton.setOnClickListener {
                val videoIntent= Intent(this,VideoActivity::class.java)
                videoIntent.putExtra("videoId",receivedData.videoId)
                startActivity(videoIntent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Memulai backsound audio
        mediaPlayer?.start()
        mediaPlayer?.isLooping = true // Mengatur backsound agar diulang secara terus menerus
    }
    override fun onPause() {
        super.onPause()
        // Jika aktivitas di-pause, hentikan backsound audio
        mediaPlayer?.pause()
    }
    override fun onDestroy() {
        super.onDestroy()
        // Ketika aktivitas dihancurkan, stop dan lepaskan resource MediaPlayer
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}