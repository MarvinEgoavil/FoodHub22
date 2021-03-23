package com.example.foodhub.fragments.fragmentsEntrada.fragmentsRecovery

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import com.example.foodhub.R
import com.example.foodhub.activities.MainActivity
import com.example.foodhub.databinding.FragmentSendEmailBinding
import com.example.foodhub.fragments.fragmentsEntrada.LoginFragment
import com.example.foodhub.fragments.fragmentsEntrada.RegisterFragment


class SendEmailFragment : Fragment() {

    private lateinit var binding: FragmentSendEmailBinding
    private lateinit var btnSendEmail: Button
    private lateinit var verifyFragment: VerifyFragment
    private lateinit var mainActivity: MainActivity
    private lateinit var loginFragment:LoginFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginFragment=LoginFragment()
        mainActivity = activity as MainActivity

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out)
                transaction.replace(R.id.fragment_container, loginFragment)
                transaction.disallowAddToBackStack()
                transaction.commit()
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSendEmailBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSendEmail = binding.btnSendEmail

        verifyFragment = VerifyFragment()

        mainActivity = activity as MainActivity

        btnSendEmail.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
            transaction.replace(R.id.fragment_container, verifyFragment)
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
      //   mainActivity.window.statusBarColor=Color.rgb(212,119,62)
    }
}