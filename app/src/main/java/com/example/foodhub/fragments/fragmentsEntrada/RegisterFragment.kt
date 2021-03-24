package com.example.foodhub.fragments.fragmentsEntrada


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.foodhub.R
import com.example.foodhub.activities.HomeActivity
import com.example.foodhub.activities.MainActivity
import com.example.foodhub.api.Api
import com.example.foodhub.api.RetrofitClient
import com.example.foodhub.databinding.FragmentRegisterBinding
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class RegisterFragment : Fragment() {
    private var TAG: String = RegisterFragment::class.java.name

    private lateinit var binding: FragmentRegisterBinding

    /*private var name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var password_confirmation: String = ""*/
    private lateinit var progressDialog: ProgressDialog
    private lateinit var mainActivity: MainActivity
    private lateinit var loginFragment: LoginFragment

   // var instanceRetrofit: RetrofitClient = RetrofitClient.instanceFood()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivity = activity as MainActivity

        loginFragment = LoginFragment()

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val transaction = parentFragmentManager.beginTransaction()
                transaction.setCustomAnimations(R.anim.slide_down_bueno, R.anim.fragment_close_exit)
                transaction.replace(R.id.fragment_container, loginFragment)
                transaction.disallowAddToBackStack()
                transaction.commit()
            }
        })

        progressDialog = ProgressDialog(context)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideSoftKeyboard()
        binding.btnEnviar.setOnClickListener(View.OnClickListener setOnClickListener@{
            /*name = binding.inputName.text.toString().trim()
            email = binding.editTextEmail.text.toString().trim()
            password = binding.editTextPassword.text.toString().trim()
            password_confirmation = binding.inputRePassword.text.toString().trim()*/
            if (isEmpty(
                    binding.inputName,
                    binding.editTextEmail,
                    binding.editTextPassword,
                    binding.inputRePassword
                )
            ) {
                initProgressLoad(true, "Registrando nuevo usuario")
                register(
                    binding.inputName.text.toString().trim(),
                    binding.editTextPassword.text.toString().trim(),
                    binding.inputRePassword.text.toString().trim(),
                    binding.editTextEmail.text.toString().trim()
                )
            }
        })
    }

    private fun isEmpty(
        name: EditText,
        correo: EditText,
        password: EditText,
        rePassword: EditText
    ): Boolean {
        if (!name.text.toString().trim().isEmpty()) {
            if (!correo.text.toString().trim().isEmpty()) {
                if (!password.text.toString().trim().isEmpty()) {
                    if (!rePassword.text.toString().trim().isEmpty()) {
                        if (password.text.toString().trim()
                                .contentEquals(rePassword.text.toString().trim())
                        ) {
                            return true
                        } else {
                            setError(rePassword, "Las contraseñas deben ser iguales")
                            return false
                        }
                    } else {
                        setError(rePassword, "Contraseña necesario")
                        return false
                    }
                } else {
                    setError(password, "Contraseña necesario")
                    return false
                }
            } else {
                setError(correo, "Correo necesario")
                return false
            }
        } else {
            setError(name, "Nombre necesario")
            return false
        }
    }

    private fun setError(editText: EditText, message: String) {
        editText.error = message
        editText.requestFocus()
    }

    private fun hideSoftKeyboard() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(requireView().getWindowToken(), 0)
    }

    private fun register(
        name: String,
        password: String,
        password_confirmation: String,
        email: String,
    ) {
        Log.d(
            TAG,
            "register name: " + name + ", password: " + password + ", password_confirmation: " + password_confirmation + ", email: " + email
        )
        RetrofitClient.instanceFood().create(Api::class.java).createUser(name, password, password_confirmation, email)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
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
                        } else if (response.code() == 400) {
                            Log.i(
                                TAG,
                                "response no es nulo y la conexion no fue exitosa " + response.errorBody()
                                    .toString()
                            )
                            Toast.makeText(
                                context,
                                "El correo ingresado ya existe en nuestros registros verifique y vuelva a intentarlo",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Log.i(TAG, "response es nulo" + response.body())
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "t: " + t.message + "casue " + t.cause)
                    initProgressLoad(false, "")

                    val intent = Intent(mainActivity, HomeActivity::class.java)
                    startActivity(intent)
                    Animatoo.animateSlideLeft(mainActivity)
                    mainActivity?.finishAffinity()
                }
            })
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