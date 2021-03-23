package com.example.foodhub.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.foodhub.R
import com.example.foodhub.databinding.ActivitySplashBinding

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Animacion de la imagen
        var animation = AnimationUtils.loadAnimation(this, R.anim.anim)
        binding.IVicono.startAnimation(animation)


        // Crear Delayed en mi Splash
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Animatoo.animateSlideLeft(this)
            finish()
        }, 3000)


        //Quitar el Status Bar de Splash Screen

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else
            window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
            )

    }

}