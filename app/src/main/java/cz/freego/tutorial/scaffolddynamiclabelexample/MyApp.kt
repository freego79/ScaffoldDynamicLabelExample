package cz.freego.tutorial.scaffolddynamiclabelexample

import android.app.Application
import android.content.Context
import androidx.emoji2.text.EmojiCompat
import androidx.emoji2.text.FontRequestEmojiCompatConfig
import androidx.core.provider.FontRequest

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()

        initEmojiCompat(this)
    }

    private fun initEmojiCompat(context: Context) {
        val fontRequest = FontRequest(
            "com.google.android.gms.fonts",
            "com.google.android.gms",
            "Noto Color Emoji Compat",
            R.array.com_google_android_gms_fonts_certs
        )

        val config = FontRequestEmojiCompatConfig(context, fontRequest)
            .setReplaceAll(true) // Automaticky nahradí nekompatibilní emoji

        EmojiCompat.init(config)
    }
}
