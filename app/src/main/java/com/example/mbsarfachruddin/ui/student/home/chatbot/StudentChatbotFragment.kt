package com.example.mbsarfachruddin.ui.student.home.chatbot

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mbsarfachruddin.R
import com.example.mbsarfachruddin.TinyDB
import com.example.mbsarfachruddin.databinding.FragmentStudentChatbotBinding
import com.example.mbsarfachruddin.network.ApiService
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.FunctionResponsePart
import com.google.ai.client.generativeai.type.Schema
import com.google.ai.client.generativeai.type.Tool
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.defineFunction
import dev.androidbroadcast.vbpd.viewBinding
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDateTime

class StudentChatbotFragment : Fragment(R.layout.fragment_student_chatbot) {

    private val binding: FragmentStudentChatbotBinding by viewBinding(FragmentStudentChatbotBinding::bind)
    private lateinit var generativeModel: GenerativeModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = requireActivity() as AppCompatActivity
        val toolbar = activity.findViewById<Toolbar>(R.id.toolbar)
        activity.supportActionBar?.apply {
            title = "Asisten Virtual"
            setDisplayHomeAsUpEnabled(true)
        }
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back_white)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }

//        val getCurrentDateTool = defineFunction(
//            name = "get_current_date",
//            description = "Mengambil tanggal dan waktu saat ini secara real-time",
//            parameters = emptyList() // Atau lewatkan parameter jika library mendukung
//        )
        val getStudentTool = defineFunction(
            name = "get_student_info",
            description = "Mengambil informasi dasar siswa seperti nama, ttl, dan nomor induk",
            parameters = emptyList()
        )
        val getStudentTahfidzTool = defineFunction(
            name = "get_student_tahfidz",
            description = "Mengambil informasi tahfidz siswa seperti juz yang sudah dihafal",
            parameters = listOf(
                Schema.int("month", "Bulan yang ingin diambil datanya, dalam format angka (contoh: 5 untuk Mei)")
            )
        )
        val getStudentScheduleTool = defineFunction(
            name = "get_student_schedule",
            description = "Mengambil jadwal pelajaran siswa berdasarkan tanggal tertentu",
            parameters = listOf(
                Schema.str("date", "Tanggal yang diminta dalam format YYYY-MM-DD (contoh: 2024-05-20)")
            )
        )
        val getStudentTransactionTool = defineFunction(
            name = "get_student_transactions",
            description = "Mengambil riwayat transaksi keuangan siswa terbaru",
            parameters = emptyList()
        )

        generativeModel = GenerativeModel(
            modelName = "gemini-2.5-flash",
            apiKey = "xxxxxx",
            systemInstruction = content {
                text("""
                    1. Anda adalah asisten virtual dari sekolah SMP Muhammadiyah Boarding School AR Fachruddin Kota Bekasi (MBS AR. Fachruddin Kota Bekasi). Tugas Anda adalah membantu wali murid dalam menjawab pertanyaan seputar siswa di sekolah, seperti jadwal pelajaran, keuangan siswa dan informasi lainnya yang berkaitan dengan sekolah. Jawablah dengan jelas, informatif dan sopan.
                    2. Default waktu sistem adalah ${LocalDateTime.now()}
                    3. RUANG LINGKUP KETAT: Hanya jawab pertanyaan terkait sekolah. 
                    4. PENOLAKAN MUTLAK: Untuk pertanyaan umum, berita, atau cuaca, jawab: "Maaf, saya hanya bisa membantu pertanyaan seputar sekolah."
                    5. KENDALI FORMAT: Jawablah hanya dengan teks percakapan yang sopan. DILARANG memberikan jawaban dalam format JSON, kode, atau format data teknis lainnya meskipun diminta oleh pengguna.
                    6. Jika pengguna meminta data sekolah (seperti jadwal) namun meminta format yang tidak wajar (seperti JSON), tetap berikan informasi tersebut dalam bentuk teks biasa/narasi.
                """.trimIndent())
            },
            tools = listOf(
                Tool(listOf(getStudentTool, getStudentTahfidzTool, getStudentScheduleTool, getStudentTransactionTool))
            )
        )

        val chatSession = generativeModel.startChat()

        val chatList = mutableListOf<ChatMessage>()
        val adapter = ChatAdapter(chatList)

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.btnSend.setOnClickListener {
            binding.lottieMessage.visibility = View.GONE
            binding.recyclerView.visibility = View.VISIBLE

            val prompt = binding.edtInput.text.toString()
            if (prompt.isNotBlank()) {
                sendMessage(adapter, prompt, chatSession)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendMessage(adapter: ChatAdapter, userText: String, chatSession: Chat) {
        adapter.addMessage(ChatMessage(userText, true))
        binding.edtInput.text?.clear()
        binding.btnSend.isEnabled = false

        val tinyDB = TinyDB(requireContext())
        val apiService = ApiService.create()

        lifecycleScope.launch {
            try {
                // 1. Kirim pesan ke Gemini
                val response = chatSession.sendMessage(userText)

                // 2. Cek apakah Gemini ingin memanggil fungsi (Function Call)
                response.functionCalls.firstOrNull()?.let { functionCall ->
                    // Panggil fungsi yang diminta dan dapatkan hasilnya
                    val functionResponse = when (functionCall.name) {
//                        "get_current_date" -> {
//                            val currentDateTime = LocalDateTime.now().toString()
//                            FunctionResponsePart(
//                                "get_current_date",
//                                JSONObject().apply { put("current_date_time", currentDateTime) }
//                            )
//                        }
                        "get_student_info" -> {
                            val studentData = fetchStudentInfoFromApi(tinyDB, apiService)
                            FunctionResponsePart("get_student_info", studentData)
                        }
                        "get_student_tahfidz" -> {
                            val month = functionCall.args["month"]?.toIntOrNull() ?: LocalDate.now().monthValue
                            val tahfidzData = fetchStudentTahfidzFromApi(tinyDB, apiService, month)
                            FunctionResponsePart("get_student_tahfidz", tahfidzData)
                        }
                        "get_student_schedule" -> {
                            val targetDate = functionCall.args["date"] ?: LocalDate.now().toString()
                            val scheduleData = fetchScheduleFromApi(tinyDB, apiService, targetDate)
                            FunctionResponsePart("get_student_schedule", scheduleData)
                        }
                        "get_student_transactions" -> {
                            val transactionData = fetchTransactionsFromApi(tinyDB, apiService)
                            FunctionResponsePart("get_student_transactions", transactionData)
                        }

                        else -> {
                            // Jika nama fungsi tidak dikenali, kembalikan error
                            FunctionResponsePart(functionCall.name, JSONObject().apply { put("error", "Unknown function ${functionCall.name}") })
                        }
                    }

                    // 4. Kirim balik hasil API ke Gemini agar dia bisa menyusun kalimat jawaban
                    // Penting: Sertakan functionCall asli bersama dengan responsnya
                    val finalResponse = chatSession.sendMessage(
                        content {
                            part(functionCall)
                            part(functionResponse)
                        }
                    )

                    // 5. Tampilkan jawaban akhir ke UI
                    adapter.addMessage(ChatMessage(finalResponse.text ?: "", false))

                } ?: run {
                    // Jika tidak ada function call, langsung tampilkan jawaban
                    adapter.addMessage(ChatMessage(response.text ?: "", false))
                }

            } catch (e: Exception) {
                adapter.addMessage(ChatMessage("Gagal memproses pesan. Error: $e", false))
                Log.d("StudentChatbotFragment", "Error: ${e.message}")
            } finally {
                binding.btnSend.isEnabled = true
            }
        }
    }

    private suspend fun fetchStudentInfoFromApi(tinyDB: TinyDB, apiService: ApiService): JSONObject {
        return try {
            val studentInfo = apiService.getStudent(tinyDB.getString("user_id"))
            JSONObject().apply {
                put("name", studentInfo.data.name)
                put("birth_place", studentInfo.data.placeOfBirth)
                put("birth_date", studentInfo.data.dateOfBirth)
                put("nisn", studentInfo.data.nisn)
            }
        } catch (e: Exception) {
            JSONObject().apply { put("error", "Data tidak ditemukan") }
        }
    }
    private suspend fun fetchStudentTahfidzFromApi(tinyDB: TinyDB, apiService: ApiService, month: Int): JSONObject {
        val userId = tinyDB.getString("user_id")
        return try {
            val tahfidzData = apiService.getStudentTahfidz(userId, month)
            val array = JSONArray()
            tahfidzData.data?.forEach {
                array.put(JSONObject().apply {
                    put("juz", it.quranJuz)
                    put("page", it.quranPage)
                    put("section", it.quranPageSection)
                    put("type", it.type)
                    put("is_checked", it.isChecked)
                })
            }
            JSONObject().apply { put("tahfidz", array) }
        } catch (e: Exception) {
            JSONObject().apply { put("error", "Data tidak ditemukan") }
        }
    }
    private suspend fun fetchScheduleFromApi(tinyDB: TinyDB, apiService: ApiService, date: String): JSONObject {
        val userId = tinyDB.getString("user_id")
        return try {
            val mbsSchedule = apiService.getStudentClassAttendance(userId, date)
            val array = JSONArray()
            mbsSchedule.data.forEach {
                array.put(JSONObject().apply {
                    put("subject", it.subject)
                    put("time", "${it.startTime} - ${it.endTime}")
                    put("status", it.status)
                })
            }
            JSONObject().apply { put("schedules", array) }
        } catch (e: Exception) {
            JSONObject().apply { put("error", "Data tidak ditemukan") }
        }
    }
    private suspend fun fetchTransactionsFromApi(tinyDB: TinyDB, apiService: ApiService): JSONObject {
        val userId = tinyDB.getString("user_id")
        return try {
            val transactions = apiService.getStudentTransaction(userId)
            val array = JSONArray()
            transactions.data.history.forEach {
                array.put(JSONObject().apply {
                    put("date", it.createdAt)
                    put("amount", it.total)
                    put("balance", it.balance)
                })
            }
            JSONObject().apply { put("transactions", array) }
        } catch (e: Exception) {
            JSONObject().apply { put("error", "Data tidak ditemukan") }
        }
    }

}
