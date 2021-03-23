package com.example.foodhub.fragments.fragmentsEntrada.fragmentsRecovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import com.example.foodhub.R
import com.example.foodhub.activities.HomeActivity
import com.example.foodhub.activities.MainActivity
import com.example.foodhub.databinding.FragmentCongratulationsBinding
import com.example.foodhub.fragments.fragmentsEntrada.LoginFragment


class CongratulationsFragment : Fragment() {

    private lateinit var binding: FragmentCongratulationsBinding
    private lateinit var loginFragment: LoginFragment
    private lateinit var btnContinuar: Button
    private lateinit var mainActivity: MainActivity
    private lateinit var resetPasswordFragment: ResetPasswordFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                resetPasswordFragment = ResetPasswordFragment()

                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out)
                transaction.replace(R.id.fragment_container, resetPasswordFragment)
                transaction.disallowAddToBackStack()
                transaction.commit()
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCongratulationsBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnContinuar = binding.btnContinuar
        loginFragment = LoginFragment()
        mainActivity = activity as MainActivity

        btnContinuar.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
            transaction.replace(R.id.fragment_container, loginFragment)
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
    }
}