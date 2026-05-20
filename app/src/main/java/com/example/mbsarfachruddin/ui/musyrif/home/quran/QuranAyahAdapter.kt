package com.example.mbsarfachruddin.ui.musyrif.home.quran

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.SharedViewModel
import com.example.mbsarfachruddin.databinding.ItemQuranAyahBinding
import com.example.mbsarfachruddin.model.remote.quran.ayah.Ayat
import dev.androidbroadcast.vbpd.viewBinding

class QuranAyahAdapter(private var listAyah: List<Ayat>, private val sharedViewModel: SharedViewModel) : RecyclerView.Adapter<QuranAyahAdapter.QuranAyahViewHolder>() {

    private var mediaPlayer: MediaPlayer? = null
    private var isMurattalPlaying = false

    inner class QuranAyahViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding: ItemQuranAyahBinding by viewBinding(ItemQuranAyahBinding::bind)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuranAyahViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_quran_ayah, parent, false)
        return QuranAyahViewHolder(view)
    }

    override fun getItemCount(): Int = listAyah.size

    override fun onBindViewHolder(holder: QuranAyahViewHolder, position: Int) {
        val ayah = listAyah[position]
        with(holder.binding) {
            tvAyahNo.text = ayah.nomorAyat.toString()
            tvAyahAr.text = ayah.teksArab
            tvAyahLatin.text = ayah.teksLatin
            tvAyahTranslate.text = ayah.teksIndonesia

            val ayatQuran = ayah.teksArab + "\n\n" + ayah.teksIndonesia

            imgbtnShare.setOnClickListener {
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, ayatQuran)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                it.context.startActivity(shareIntent)
            }

            imgbtnCopy.setOnClickListener {
                val clipboard = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Ayah", ayatQuran)
                clipboard.setPrimaryClip(clip)
                Toast.makeText(it.context, "Ayah copied to clipboard", Toast.LENGTH_SHORT).show()
            }

            imgbtnPlay.setOnClickListener {
                val numberSurah = String.format("%03d", sharedViewModel.nowQuranSurah.value.toInt())
                val numberAyah = String.format("%03d", ayah.nomorAyat)
                val url = "https://cdn.equran.id/audio-partial/Misyari-Rasyid-Al-Afasi/${numberSurah}${numberAyah}.mp3"
                if (isMurattalPlaying) {
                    mediaPlayer?.let {
                        if (it.isPlaying) {
                            it.pause()
                            isMurattalPlaying = false
                        }
                    }
                    imgbtnPlay.setImageResource(R.drawable.ic_music_play) // Ganti ikon ke play
                } else {
                    mediaPlayer = MediaPlayer().apply {
                        setDataSource(url)
                        prepareAsync() // Siapkan secara asinkron
                        setOnPreparedListener {
                            start() // Mulai pemutaran setelah siap
                            isMurattalPlaying = true
                        }
                        setOnCompletionListener {
                            // Ketika audio selesai, set status isPlaying menjadi false dan ubah ikon ke play
                            isMurattalPlaying = false
                            imgbtnPlay.setImageResource(R.drawable.ic_music_play)
                        }
                        setOnErrorListener { mp, what, extra ->
                            Toast.makeText(holder.itemView.context, "Error playing audio", Toast.LENGTH_SHORT).show()
                            false
                        }
                    }
                    imgbtnPlay.setImageResource(R.drawable.ic_music_stop) // Ganti ikon ke stop
                }
            }
        }
    }
}