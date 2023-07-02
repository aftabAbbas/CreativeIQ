@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NAME_SHADOWING")

package com.itzcafe.creativeiq.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.graphics.Color
import android.media.AudioManager
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.itzcafe.creativeiq.R
import java.io.File
import java.lang.String.format
import java.lang.reflect.Field
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.time.Duration.Companion.milliseconds


@Suppress("deprecation", "all")
object Functions {

    fun hideSystemUI(context: Context) {
        val activity = context as AppCompatActivity
        activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            activity.window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }

    fun hideStatusBar(context: Context) {
        val activity = context as Activity
        activity.window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            activity.window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }

    fun getTime(date: String?): String? {
        val originalFormat: DateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH)
        @SuppressLint("SimpleDateFormat") val targetFormat: DateFormat = SimpleDateFormat("hh:mm a")
        var date1: Date? = null

        try {
            date1 = originalFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        assert(date1 != null)
        return targetFormat.format(date1)
    }

    fun setStatusBarIconsBlack(context: Context) {
        val window = (context as Activity).window
        val decorView = window.decorView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
    }

    fun hideKeyboard(context: Context) {
        val activity = context as Activity
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus

        if (view == null) {
            view = View(activity)
        }

        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun openKeyboard(context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun changeStatusBarColor(context: Context, color: Int) {
        val window = (context as Activity).window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(context, color)
    }

    fun changeNavigationBarColor(context: Context, color: Int) {
        val activity = context as AppCompatActivity
        activity.window.navigationBarColor = activity.resources.getColor(color)
    }

    fun fullScreenWithNav(context: Context) {
        val activity = context as AppCompatActivity
        val window = activity.window
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }

    class MyBrowser(var progressBar: ProgressBar) : WebViewClient() {
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            progressBar.visibility = View.VISIBLE
            view.loadUrl(url)
            return true
        }

        override fun onPageFinished(view: WebView, url: String) {
            progressBar.visibility = View.GONE
        }
    }

    fun changeColor(context: Context, imageView: ImageView, textView: TextView, color: Int) {
        changeImgTint(context, imageView, color)
        changeTextColor(context, textView, color)
    }

    fun changeImgTint(context: Context, imageView: ImageView, color: Int) {
        imageView.setColorFilter(ContextCompat.getColor(context, color))
    }

    private fun changeTextColor(context: Context, textView: TextView, color: Int) {
        textView.setTextColor(ContextCompat.getColor(context, color))
    }

    fun setError(editText: EditText, error: String) {
        editText.error = error
    }

    fun changeTaskBarColor(context: Context, color: Int) {
        val window = (context as Activity).window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(context, color)
    }

    fun setStatusBarTransparent(context: Context) {
        val window = (context as Activity).window
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        val decorView = window.decorView

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }

        window.statusBarColor = Color.TRANSPARENT
    }

    fun getAppVersion(context: Context): String? {
        val manager = context.packageManager
        var info: PackageInfo? = null
        try {
            info = manager.getPackageInfo(context.packageName, 0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return info!!.versionName
    }

    fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    fun startActivityWithFlags(context: Context, mClass: Class<*>) {
        (context as Activity).finish()
        context.startActivity(
            Intent(context, mClass)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    fun startActivityWithFlagsAndData(context: Context, mClass: Class<*>, gson: Gson, data: Any) {
        (context as Activity).finish()
        context.startActivity(
            Intent(context, mClass)
                .putExtra("send_data", gson.toJson(data))
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }

    fun startActivity(context: Context, mClass: Class<*>) {
        context.startActivity(Intent(context, mClass))
    }

    fun startActivityWithData(context: Context, mClass: Class<*>?, gson: Gson, data: Any) {
        context.startActivity(
            Intent(context, mClass)
                .putExtra("send_data", gson.toJson(data))
        )
    }

    fun getIntentData(context: Context, gson: Gson, nClass: Class<*>?): Any {
        val data = (context as Activity).intent.getStringExtra("send_data")
        return gson.fromJson(data, nClass)
    }

    fun isValidEmailAddress(email: String?): Boolean {
        val ePattern =
            "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
        val p = Pattern.compile(ePattern)
        val m = p.matcher(email)
        return m.matches()
    }

    fun showSnackBar(context: Context, message: String) {
        val activity = context as Activity
        Snackbar.make(
            activity.findViewById(android.R.id.content),
            Html.fromHtml("<i>$message</i>"), Snackbar.LENGTH_LONG
        )
            .setBackgroundTint(context.getResources().getColor(android.R.color.white))
            .setTextColor(context.getResources().getColor(android.R.color.black))
            .setAction(Html.fromHtml("<b> OK </b>")) { }
            .setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light))
            .show()
    }

    fun pickFile(context: Context) {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/*"
        (context as AppCompatActivity).startActivityForResult(intent, 111)
    }

    fun getMimeType(context: Context, uri: Uri): String? {
        val extension = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
            val mime = MimeTypeMap.getSingleton()
            mime.getExtensionFromMimeType(context.contentResolver.getType(uri))
        } else {
            MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
        }
        return extension
    }

    fun getFileName(context: Context, uri: Uri): String? {
        var result: String? = null
        if (uri.scheme == "content") {
            context.contentResolver.query(uri, null, null, null, null).use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            }
        }

        if (result == null) {
            result = uri.path
            val cut = result!!.lastIndexOf('/')
            if (cut != -1) {
                result = result!!.substring(cut + 1)
            }
        }

        return result
    }

    private fun getFilePath(context: Context, uri: Uri): String? {
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)

        context.contentResolver.query(
            uri, projection, null, null, null
        ).use { cursor ->

            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME)
                return cursor.getString(index)
            }
        }

        return null
    }

    fun getAllDataFromRaw(): ArrayList<Field> {
        val musicList: ArrayList<Field> = ArrayList()
        val fields: Array<Field> = R.raw::class.java.fields

        fields.forEach { field ->
            musicList.add(field)
        }

        return musicList
    }

    fun isMusicPlaying(context: Context): Boolean {
        val manager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        if (manager.isMusicActive) {
            return true
        }

        return false
    }

    fun getDuration(absolutePath: String): String {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(absolutePath)
        val rawDuration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0L
        val duration = rawDuration.milliseconds
        return format("%02d:%02d", duration.inWholeMinutes, duration.inWholeSeconds % 60)
    }

    fun getMusicDuration(duration: Int): String {
        val minutes = (duration?.div((1000 * 60)))?.rem(60)
        val seconds = (duration?.div(1000))?.rem(60)
        return String.format("%02d:%02d", minutes, seconds)
    }
}