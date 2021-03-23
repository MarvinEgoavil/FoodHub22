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
import com.example.foodhub.databinding.FragmentVerifyBinding


class VerifyFragment : Fragment() {

    private lateinit var binding: FragmentVerifyBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var btnVerificar: Button
    private lateinit var resetPasswordFragment: ResetPasswordFragment
    private lateinit var sendEmailFragment: SendEmailFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity


        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                sendEmailFragment = SendEmailFragment()
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.right_in, R.anim.right_out)
                transaction.replace(R.id.fragment_container, sendEmailFragment)
                transaction.disallowAddToBackStack()
                transaction.commit()
            }
        })


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerifyBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnVerificar = binding.btnVerificar

        resetPasswordFragment = ResetPasswordFragment()

        mainActivity = activity as MainActivity

        btnVerificar.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
            transaction.replace(R.id.fragment_container, resetPasswordFragment)
            transaction.disallowAddToBackStack()
            transaction.commit()

        }


    }
}