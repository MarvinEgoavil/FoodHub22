package com.example.foodhub.fragments.webViewFragment

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.foodhub.activities.HomeActivity
import com.example.foodhub.databinding.FragmentWebViewBinding
import com.example.foodhub.models.Product

class WebViewFragment : Fragment() {

    companion object {
        var TAG = WebViewFragment::class.java.simpleName
        private lateinit var homeActivity: HomeActivity
        private lateinit var binding: FragmentWebViewBinding
        private lateinit var progressDialog: ProgressDialog
        private lateinit var mProduct: Product

        fun newInstance(product: Product): WebViewFragment {
            var fragment = WebViewFragment()
            val bundle = bundleOf("product" to product)
            fragment.setArguments(bundle)
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeActivity = activity as HomeActivity
        progressDialog = ProgressDialog(context)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)
        mProduct = arguments?.getSerializable("product") as Product
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentWebViewBinding.inflate(inflater, container, false)

        return binding.root
    }

    fun onBackPressed(): Boolean {
        homeActivity.onRemoveFragmentToStack(true)
        return false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadUrl()
    }

    private fun loadUrl() {
        /*binding.webView.setWebViewClient(object : WebViewClient() {
            override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, er: SslError) {
                handler.proceed()
                super.onReceivedSslError(view, handler, er)
            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)
            }

            override fun onLoadResource(view: WebView, url: String) {
                super.onLoadResource(view, url)
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return super.shouldOverrideUrlLoading(view, url)
            }
        })*/

        binding.webView.getSettings().setJavaScriptEnabled(true)
        binding.webView.getSettings().setBuiltInZoomControls(false)
        binding.webView.getSettings()
            .setDomStorageEnabled(true) /// permite ejecutar comandos de AJAX
        binding.webView.getSettings().setSupportZoom(false)
        binding.webView.getSettings().setAppCacheEnabled(false)
        /*if (resolucion != 0) {
            binding.webView.setInitialScale(resolucion)
        }
        if (sizeFont != 0) {
            binding.webView.getSettings().setTextZoom(sizeFont)
        }*/
        binding.webView.clearCache(true)
        binding.webView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, progress: Int) {
                if (progress == 100) {
                    initProgressLoad(false, "")
                } else {
                    initProgressLoad(true, "Cargando")
                }
            }
        })
        binding.webView.loadUrl(mProduct.url)
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