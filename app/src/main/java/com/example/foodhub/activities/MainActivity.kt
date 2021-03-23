package com.example.foodhub.activities

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.foodhub.R
import com.example.foodhub.databinding.ActivityMainBinding
import com.example.foodhub.fragments.fragmentsEntrada.LoginFragment
import com.example.foodhub.fragments.fragmentsEntrada.RegisterFragment
import com.example.foodhub.fragments.fragmentsEntrada.fragmentsRecovery.CongratulationsFragment
import com.example.foodhub.fragments.fragmentsEntrada.fragmentsRecovery.ResetPasswordFragment
import com.example.foodhub.fragments.fragmentsEntrada.fragmentsRecovery.SendEmailFragment
import com.example.foodhub.fragments.fragmentsEntrada.fragmentsRecovery.VerifyFragment



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    lateinit var loginFragment: LoginFragment
    lateinit var registerFragment: RegisterFragment
    lateinit var sendEmailFragment: SendEmailFragment
    lateinit var verifyFragment: VerifyFragment
    lateinit var resetPasswordFragment: ResetPasswordFragment
    lateinit var congratulationsFragment: CongratulationsFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Hacer configuraciones

        config()


        loginFragment = LoginFragment()
        registerFragment = RegisterFragment()
        sendEmailFragment = SendEmailFragment()
        verifyFragment = VerifyFragment()
        resetPasswordFragment = ResetPasswordFragment()
        congratulationsFragment = CongratulationsFragment()

        openFragment(loginFragment)

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun config() {
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
     //   window.statusBarColor = Color.TRANSPARENT;
        window.statusBarColor = Color.BLACK
        window.navigationBarColor = ContextCompat.getColor(baseContext, R.color.black)
        // val background = this@MainActivity.resources.getDrawable(R.drawable.gradientesplash)
       // getWindow().setBackgroundDrawable(background)
    }

    fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.disallowAddToBackStack()
        transaction.commit()
    }

}