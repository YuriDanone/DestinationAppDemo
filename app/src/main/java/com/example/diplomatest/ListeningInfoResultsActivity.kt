package com.example.diplomatest

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.diplomatest.adapter.ListeningTestResultsAdapter
import com.example.diplomatest.databinding.ActivityListeningInfoResultsBinding
import com.example.diplomatest.model.ListeningTest
import com.google.firebase.firestore.FirebaseFirestore
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ListeningInfoResultsActivity: AppCompatActivity() {
    private lateinit var binding: ActivityListeningInfoResultsBinding
    private lateinit var mDataBase: FirebaseFirestore
    private lateinit var listeningList: ArrayList<ListeningTest>
    private lateinit var mAdapter: ListeningTestResultsAdapter

    private var handler = Handler()
    private val runnableMap = HashMap<MediaPlayer, Runnable>()

    private var mediaPlayer1: MediaPlayer? = null
    private var mediaPlayer2: MediaPlayer? = null
    private var mediaPlayer3: MediaPlayer? = null
    private var mediaPlayer4: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        binding = ActivityListeningInfoResultsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val id = intent.extras?.get("id").toString()

        setupImageView(intent.extras?.get("img1").toString(), R.id.part_img1)
        setupImageView(intent.extras?.get("img2").toString(), R.id.part_img2)
        setupImageView(intent.extras?.get("img3").toString(), R.id.part_img3)
        setupImageView(intent.extras?.get("img4").toString(), R.id.part_img4)

        val questions = (1..40).map { intent.extras?.get("q$it").toString() }
        val questionTextViews = (1..40).map { findViewById<TextView>(resources.getIdentifier("et$it", "id", packageName)) }
        questions.forEachIndexed { index, question -> questionTextViews[index].setText(question) }

        val feedback = intent.extras?.get("feedback").toString()
        binding.feedback.setText(feedback)

        mediaPlayer1 = setupAudioPlayer(intent.extras?.get("audio1").toString(), R.id.seekbar, R.id.ivPlay, R.id.tvTimer1)
        mediaPlayer2 = setupAudioPlayer(intent.extras?.get("audio2").toString(), R.id.seekbar2, R.id.ivPlay2, R.id.tvTimer2)
        mediaPlayer3 = setupAudioPlayer(intent.extras?.get("audio3").toString(), R.id.seekbar3, R.id.ivPlay3, R.id.tvTimer3)
        mediaPlayer4 = setupAudioPlayer(intent.extras?.get("audio4").toString(), R.id.seekbar4, R.id.ivPlay4, R.id.tvTimer4)

        findViewById<Button>(R.id.fill_answers_btn).setOnClickListener {
            editBtn(id)
        }
    }

    private fun setupImageView(imgUrl: String, imageViewId: Int) {
        val imageView = findViewById<AspectRatioImageView>(imageViewId)
        if (imgUrl.isEmpty()) {
            imageView.setImageResource(R.drawable.img_7)
        } else {
            Picasso.get()
                .load(imgUrl)
                .into(imageView, object : Callback {
                    override fun onSuccess() {
                        val aspectRatio = imageView.drawable.intrinsicWidth.toFloat() / imageView.drawable.intrinsicHeight.toFloat()
                        imageView.setAspectRatio(aspectRatio)
                    }

                    override fun onError(e: Exception?) {
                        // Handle error
                    }
                })
        }
    }

    private fun setupAudioPlayer(audioUrl: String, seekBarId: Int, playButtonId: Int, timerTextViewId: Int): MediaPlayer? {
        val seekBar: SeekBar = findViewById(seekBarId)
        val playButton: ImageView = findViewById(playButtonId)
        val timerTextView: TextView = findViewById(timerTextViewId)
        val mediaPlayer = MediaPlayer()

        mediaPlayer.setOnPreparedListener { mp ->
            seekBar.progress = 0
            seekBar.max = mp.duration

            playButton.setOnClickListener {
                if (!mp.isPlaying) {
                    mp.start()
                } else {
                    mp.pause()
                }
            }

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        mp.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            val updateSeekBarAndTimer = object : Runnable {
                override fun run() {
                    seekBar.progress = mp.currentPosition
                    updateTimer(mp.currentPosition, mp.duration, timerTextView)
                    handler.postDelayed(this, 100)
                }
            }

            mp.setOnCompletionListener {
                mp.pause()
                seekBar.progress = 0 // Reset seek bar when audio completes
                updateTimer(0, mp.duration, timerTextView) // Reset timer
                handler.post(updateSeekBarAndTimer) // Restart updating seek bar and timer
            }
            mp.setOnSeekCompleteListener {
                handler.post(updateSeekBarAndTimer) // Resume updating seek bar and timer after seeking
            }

            handler.post(updateSeekBarAndTimer) // Start updating seek bar and timer
        }

        mediaPlayer.setOnErrorListener { _, _, _ ->
            Log.e("MediaPlayer", "Error occurred while playing audio")
            false
        }

        try {
            if (audioUrl.isEmpty()) {
                mediaPlayer.setDataSource(this, Uri.parse("android.resource://$packageName/raw/ielts1"))
            } else {
                mediaPlayer.setDataSource(audioUrl)
            }
            mediaPlayer.prepareAsync()
        } catch (e: Exception) {
            Log.e("MediaPlayer", "Error setting data source", e)
            return null
        }

        return mediaPlayer
    }

    private fun updateTimer(currentPosition: Int, duration: Int, timerTextView: TextView) {
        val currentSeconds = currentPosition / 1000
        val durationSeconds = duration / 1000
        val currentMinutes = currentSeconds / 60
        val currentSecondsDisplay = currentSeconds % 60
        val durationMinutes = durationSeconds / 60
        val durationSecondsDisplay = durationSeconds % 60
        val timerText = String.format(
            "%02d:%02d / %02d:%02d",
            currentMinutes, currentSecondsDisplay,
            durationMinutes, durationSecondsDisplay
        )
        timerTextView.text = timerText
    }

    private fun editBtn(id: String) {
        finish()
        val intent = Intent(this, ListeningEditResultsActivity::class.java)
        intent.putExtra("id", id)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
//        mediaPlayer1?.release()
//        mediaPlayer2?.release()
//        mediaPlayer3?.release()
//        mediaPlayer4?.release()
        overridePendingTransition(R.anim.slide_enter_left, R.anim.slide_exit_right)
//        val intent = Intent(this, ListeningResultsActivity::class.java)
//        startActivity(intent)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//
//    }
}