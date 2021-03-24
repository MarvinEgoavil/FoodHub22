package com.example.foodhub.fragments.fragmentsEntrada

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.foodhub.R
import com.example.foodhub.activities.HomeActivity
import com.example.foodhub.activities.MainActivity
import com.example.foodhub.api.Api
import com.example.foodhub.api.RetrofitClient
import com.example.foodhub.databinding.FragmentLoginBinding
import com.example.foodhub.fragments.fragmentsEntrada.fragmentsRecovery.SendEmailFragment
import com.example.foodhub.fragments.fragmentsHome.HomeFragment
import com.example.foodhub.models.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {
    private var TAG: String = LoginFragment::class.java.simpleName

    private lateinit var binding: FragmentLoginBinding
    private lateinit var tvRegistrarse: TextView
 //    private lateinit var tvOlvidaste: TextView
    private lateinit var mainActivity: MainActivity

    private lateinit var registerFragment: RegisterFragment

    private lateinit var sendEmailFragment: SendEmailFragment
    private lateinit var homeFragment: HomeFragment
    private lateinit var progressDialog: ProgressDialog
    private lateinit var btnLogin: Button

    private var email: String = "";
    private var pass: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        progressDialog = ProgressDialog(context)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideSoftKeyboard()

        tvRegistrarse = binding.TvRegistrar
     //    tvOlvidaste = binding.TvOlvidaste
        btnLogin = binding.btnLogin

        mainActivity = activity as MainActivity
        registerFragment = RegisterFragment()
        sendEmailFragment = SendEmailFragment()
        homeFragment = HomeFragment()


        tvRegistrarse.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.slide_up_bueno, R.anim.slide_out_down)
            transaction.replace(R.id.fragment_container, registerFragment)
            transaction.disallowAddToBackStack()
            transaction.commit()
        }

   /*     tvOlvidaste.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out)
            transaction.replace(R.id.fragment_container, sendEmailFragment)
            transaction.disallowAddToBackStack()
            transaction.commit()
        }
*/
        binding.btnLogin.setOnClickListener {
            initProgressLoad(true, "Iniciando sesion")

            email = binding.editTextEmail.text.toString().trim()
            pass = binding.editTextPassword.text.toString().trim()

            if (email.isEmpty()) {
                binding.editTextEmail.error = "Email Necesario"
                binding.editTextEmail.requestFocus()
                initProgressLoad(false, "")
                return@setOnClickListener
            }

            if (pass.isEmpty()) {
                binding.editTextPassword.error = "Password Necesario"
                binding.editTextPassword.requestFocus()
                initProgressLoad(false, "")
                return@setOnClickListener
            }

            loginUser()

        }

    }

    private fun loginUser() {
        RetrofitClient.instanceFood().create(Api::class.java).loginUser(email, pass)
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    initProgressLoad(false, "")
                    if (response != null) {
                        Log.i(TAG, "response no es nulo " + response.body())
                        if (response.isSuccessful) {
                            Log.i(
                                TAG,
                                "response no es nulo y la conexion fue exitosa " + response.body()
                                    .toString()
                            )
                            val intent = Intent(mainActivity, HomeActivity::class.java)
                            startActivity(intent)
                            Animatoo.animateSlideLeft(mainActivity)
                            mainActivity?.finishAffinity()
                        } else {
                            Log.i(
                                TAG,
                                "response no es nulo y la conexion no fue exitosa " + response.errorBody()
                                    .toString()
                            )

                            Toast.makeText(
                                context,
                                "Verifique sus credenciales al parecer no son correctas",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        Log.i(TAG, "response es nulo" + response.body())
                        Toast.makeText(
                            context,
                            "Verifique sus credenciales al parecer no son correctas",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "t: " + t.message + "casue " + t.cause)
                    initProgressLoad(false, "")
                }


            })
    }

    private fun hideSoftKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().windowToken, 0)
    }

    private fun initProgressLoad(shown: Boolean, message: String) {
        if (shown) {
            if (progressDialog != null) {
                if (progressDialog.isShowing) {
                    progressDialog.setMessage(message)
                } else {
                    progressDialog.setMessage(message)
                    progressDialog.show()
                }
            } else {
                progressDialog = ProgressDialog(context)
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
                progressDialog.setCancelable(false)
                progressDialog.setMessage(message)
                progressDialog.show()
            }
        } else {
            if (progressDialog != null)
                if (progressDialog.isShowing)
                    progressDialog.dismiss()
        }
    }
}