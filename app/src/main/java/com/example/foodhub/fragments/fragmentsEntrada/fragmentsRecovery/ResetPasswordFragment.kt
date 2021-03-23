package com.example.foodhub.fragments.fragmentsEntrada.fragmentsRecovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import com.example.foodhub.R
import com.example.foodhub.activities.MainActivity
import com.example.foodhub.databinding.FragmentResetPasswordBinding
import com.example.foodhub.fragments.fragmentsEntrada.LoginFragment


class ResetPasswordFragment : Fragment() {

 private lateinit var binding:FragmentResetPasswordBinding
 private lateinit var congratulationsFragment: CongratulationsFragment
 private lateinit var btnResetPass:Button

 private lateinit var verifyFragment: VerifyFragment

 private lateinit var mainActivity: MainActivity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                verifyFragment = VerifyFragment()

                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out)
                transaction.replace(R.id.fragment_container, verifyFragment)
                transaction.disallowAddToBackStack()
                transaction.commit()
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResetPasswordBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnResetPass=binding.btnResetPass
        congratulationsFragment= CongratulationsFragment()

        mainActivity = activity as MainActivity

        btnResetPass.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
            transaction.replace(R.id.fragment_container, congratulationsFragment)
            transaction.disallowAddToBackStack()
            transaction.commit()
        }

    }
}