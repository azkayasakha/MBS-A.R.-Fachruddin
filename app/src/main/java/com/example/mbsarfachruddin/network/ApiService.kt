package com.example.mbsarfachruddin.network

import com.example.mbsarfachruddin.model.remote.announcement.AnnouncementResponse
import com.example.mbsarfachruddin.model.remote.login.LoginResponse
import com.example.mbsarfachruddin.model.remote.login.update.LoginUpdateResponse
import com.example.mbsarfachruddin.model.remote.musyrif.halaqah.HalaqahResponse
import com.example.mbsarfachruddin.model.remote.musyrif.halaqahattendance.HalaqahAttendanceResponse
import com.example.mbsarfachruddin.model.remote.musyrif.halaqahattendance.update.HalaqahAttendanceUpdateResponse
import com.example.mbsarfachruddin.model.remote.musyrif.halaqahmember.HalaqahMemberResponse
import com.example.mbsarfachruddin.model.remote.musyrif.prayerattendance.update.PrayerAttendanceUpdateResponse
import com.example.mbsarfachruddin.model.remote.musyrif.profile.MusyrifResponse
import com.example.mbsarfachruddin.model.remote.musyrif.tahfidz.create.TahfidzCreateResponse
import com.example.mbsarfachruddin.model.remote.musyrif.tahfidz.delete.TahfidzDeleteResponse
import com.example.mbsarfachruddin.model.remote.musyrif.wallet.WalletResponse
import com.example.mbsarfachruddin.model.remote.musyrif.wallet.update.WalletUpdateResponse
import com.example.mbsarfachruddin.model.remote.notification.NotificationResponse
import com.example.mbsarfachruddin.model.remote.student.classattendance.ClassAttendanceResponse
import com.example.mbsarfachruddin.model.remote.student.courseattendance.CourseAttendanceResponse
import com.example.mbsarfachruddin.model.remote.student.prayerattendance.PrayerAttendanceResponse
import com.example.mbsarfachruddin.model.remote.student.prayerattendance.detail.PrayerAttendanceDetailResponse
import com.example.mbsarfachruddin.model.remote.student.profile.StudentResponse
import com.example.mbsarfachruddin.model.remote.student.tahfidz.TahfidzResponse
import com.example.mbsarfachruddin.model.remote.student.tahfidz.update.TahfidzUpdateResponse
import com.example.mbsarfachruddin.model.remote.student.tahfidzattendance.TahfidzAttendanceResponse
import com.example.mbsarfachruddin.model.remote.student.tahfidzattendance.detail.TahfidzAttendanceDetailResponse
import com.example.mbsarfachruddin.model.remote.student.transaction.TransactionResponse
import com.example.mbsarfachruddin.model.remote.teacher.courseattendance.update.CourseAttendanceUpdateResponse
import com.example.mbsarfachruddin.model.remote.teacher.profile.TeacherResponse
import com.example.mbsarfachruddin.model.remote.teacher.schedule.ScheduleResponse
import com.example.mbsarfachruddin.model.remote.token.TokenResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("login/index.php")
    suspend fun getLogin(
        @Query("username") username: String,
        @Query("password") password: String
    ): LoginResponse

    @GET("login/update/username/index.php")
    suspend fun updateLoginUsername(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("new-username") newUsername: String
    ): LoginUpdateResponse

    @GET("login/update/password/index.php")
    suspend fun updateLoginPassword(
        @Query("username") username: String,
        @Query("password") password: String,
        @Query("new-password") newPassword: String
    ): LoginUpdateResponse

    @GET("token/create/index.php")
    suspend fun createToken(
        @Query("reference-id") referenceId: String,
        @Query("fcm-token") fcmToken: String
    ): com.example.mbsarfachruddin.model.remote.token.create.TokenResponse

    @GET("token/delete/index.php")
    suspend fun deleteToken(
        @Query("fcm-token") fcmToken: String
    ): com.example.mbsarfachruddin.model.remote.token.delete.TokenResponse

    @GET("token/create/index.php")
    suspend fun getToken(
        @Query("reference-id") referenceId: String
    ): TokenResponse

    @GET("notification/index.php")
    suspend fun createNotification(
        @Query("fcm-token") fcmToken: String,
        @Query("title") title: String,
        @Query("body") body: String
    ): NotificationResponse

    @GET("student/profile/index.php")
    suspend fun getStudent(
        @Query("nisn") nisn: String
    ): StudentResponse

    @GET("student/tahfidz/index.php")
    suspend fun getStudentTahfidz(
        @Query("nisn") nisn: String,
        @Query("month") month: Int
    ): TahfidzResponse

    @GET("student/tahfidz/update/index.php")
    suspend fun updateStudentTahfidz(
        @Query("id") id: Int
    ): TahfidzUpdateResponse

    @GET("student/tahfidz-attendance/detail/index.php")
    suspend fun getStudentTahfidzAttendanceDetail(
        @Query("nisn") nisn: String,
        @Query("date") date: String
    ): TahfidzAttendanceDetailResponse

    @GET("student/tahfidz-attendance/index.php")
    suspend fun getStudentTahfidzAttendance(
        @Query("nisn") nisn: String,
        @Query("month") month: Int
    ): TahfidzAttendanceResponse

    @GET("student/class-attendance/index.php")
    suspend fun getStudentClassAttendance(
        @Query("nisn") nisn: String,
        @Query("date") date: String
    ): ClassAttendanceResponse

    @GET("student/course-attendance/index.php")
    suspend fun getStudentCourseAttendance(
        @Query("nisn") nisn: String,
        @Query("course-id") courseId: String,
    ): CourseAttendanceResponse

    @GET("student/prayer-attendance/detail/index.php")
    suspend fun getStudentPrayerAttendanceDetail(
        @Query("nisn") nisn: String,
        @Query("date") date: String
    ): PrayerAttendanceDetailResponse

    @GET("student/prayer-attendance/index.php")
    suspend fun getStudentPrayerAttendance(
        @Query("nisn") nisn: String,
        @Query("month") month: Int
    ): PrayerAttendanceResponse

    @GET("student/transaction/index.php")
    suspend fun getStudentTransaction(
        @Query("nisn") nisn: String
    ): TransactionResponse

    @GET("musyrif/profile/index.php")
    suspend fun getMusyrif(
        @Query("musyrif-id") musyrifId: String
    ): MusyrifResponse

    @GET("musyrif/halaqah/halaqah/index.php")
    suspend fun getMusyrifHalaqah(): HalaqahResponse

    @GET("musyrif/halaqah/halaqah-member/index.php")
    suspend fun getMusyrifHalaqahMemberByMusyrif(
        @Query("musyrif-id") musyrifId: String
    ): HalaqahMemberResponse

    @GET("musyrif/halaqah/halaqah-member/index.php")
    suspend fun getMusyrifHalaqahMemberByHalaqah(
        @Query("halaqah-id") halaqahId: Int
    ): HalaqahMemberResponse

    @GET("musyrif/halaqah/tahfidz/index.php")
    suspend fun getMusyrifTahfidz(
        @Query("nisn") nisn: String
    ): com.example.mbsarfachruddin.model.remote.musyrif.tahfidz.TahfidzResponse

    @GET("musyrif/halaqah/tahfidz/create/index.php")
    suspend fun createMusyrifTahfidz(
        @Query("nisn") nisn: String,
        @Query("halaqah-id") halaqahId: Int,
        @Query("quran-juz") quranJuz: Int,
        @Query("quran-page") quranPage: Int,
        @Query("quran-page-section") quranPageSection: Double,
        @Query("type") type: String
    ): TahfidzCreateResponse

    @GET("musyrif/halaqah/tahfidz/delete/index.php")
    suspend fun deleteMusyrifTahfidz(
        @Query("id") id: Int
    ): TahfidzDeleteResponse

    @GET("musyrif/prayer-attendance/index.php")
    suspend fun getMusyrifAttendancePrayer(
        @Query("musyrif-id") musyrifId: String,
        @Query("prayer") prayer: String,
        @Query("date") date: String,
    ): com.example.mbsarfachruddin.model.remote.musyrif.prayerattendance.PrayerAttendanceResponse

    @GET("musyrif/prayer-attendance/update/index.php")
    suspend fun updateMusyrifAttendancePrayer(
        @Query("attendance-id") attendanceId: String,
        @Query("nisn") nisn: String,
        @Query("prayer") prayer: String,
        @Query("status") status: String,
        @Query("date") date: String
    ): PrayerAttendanceUpdateResponse

    @GET("musyrif/halaqah-attendance/index.php")
    suspend fun getMusyrifAttendanceHalaqah(
        @Query("musyrif-id") musyrifId: String,
        @Query("time") time: String,
        @Query("date") date: String,
    ): HalaqahAttendanceResponse

    @GET("musyrif/halaqah-attendance/update/index.php")
    suspend fun updateMusyrifAttendanceHalaqah(
        @Query("attendance-id") attendanceId: String,
        @Query("nisn") nisn: String,
        @Query("time") time: String,
        @Query("status") status: String,
        @Query("date") date: String
    ): HalaqahAttendanceUpdateResponse

    @GET("musyrif/wallet/index.php")
    suspend fun getMusyrifWallet(
        @Query("nisn") nisn: String
    ): WalletResponse

    @GET("musyrif/wallet/update/index.php")
    suspend fun updateMusyrifWallet(
        @Query("nisn") nisn: String,
        @Query("amount") amount: Int
    ): WalletUpdateResponse

    @GET("teacher/profile/index.php")
    suspend fun getTeacher(
        @Query("nip") nip: String
    ): TeacherResponse

    @GET("teacher/schedule/index.php")
    suspend fun getTeacherSchedule(
        @Query("nip") nip: String,
        @Query("day") day: String
    ): ScheduleResponse

    @GET("teacher/course-attendance/index.php")
    suspend fun getTeacherCourseAttendance(
        @Query("course-id") courseId: String,
        @Query("class-id") classId: String,
        @Query("date") date: String
    ): com.example.mbsarfachruddin.model.remote.teacher.courseattendance.CourseAttendanceResponse

    @GET("teacher/course-attendance/update/index.php")
    suspend fun updateTeacherCourseAttendance(
        @Query("attendance-id") attendanceId: String,
        @Query("status") status: String,
        @Query("nisn") nisn: String,
        @Query("class-id") classId: String,
        @Query("course-id") courseId: String,
        @Query("date") date: String
    ): CourseAttendanceUpdateResponse

    @GET("announcement/index.php")
    suspend fun getAnnouncement(
        @Query("role") role: String
    ): AnnouncementResponse

    companion object {
        private const val BASE_URL = "https://aplikasi.mbsarfachruddin.com/api/software/"

        fun create() : ApiService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}