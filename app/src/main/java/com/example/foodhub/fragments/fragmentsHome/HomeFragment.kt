package com.example.foodhub.fragments.fragmentsHome

import android.app.ActionBar
import android.app.AlertDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodhub.activities.HomeActivity
import com.example.foodhub.adapter.HomeAdapter
import com.example.foodhub.databinding.FragmentHomeBinding
import com.example.foodhub.fragments.webViewFragment.WebViewFragment
import com.example.foodhub.models.Product
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.imaginativeworld.whynotimagecarousel.CarouselItem
import org.imaginativeworld.whynotimagecarousel.CarouselType

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {
    val TAG = HomeFragment::class.java.simpleName

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var mInterstitialAd: InterstitialAd? = null

    var actionBar: ActionBar? = null
    private lateinit var progressDialog: ProgressDialog

    companion object {
        private lateinit var homeActivity: HomeActivity
    }

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog(context)
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        progressDialog.setCancelable(false)

        homeActivity = activity as HomeActivity
        loadIntersitial()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        linearLayoutManager = LinearLayoutManager(context)
        binding.containerMain.recyclerView.layoutManager = linearLayoutManager
        obtainListMenu()
        binding.containerMain.searchView.setOnQueryTextListener(this)
        return binding.root
    }

    fun getInterstitialAd(): InterstitialAd {
        if (mInterstitialAd == null)
            loadIntersitial()
        return mInterstitialAd!!
    }

    fun loadIntersitial() {
        MobileAds.initialize(homeActivity)
        mInterstitialAd = InterstitialAd(homeActivity)
        mInterstitialAd?.adUnitId = "ca-app-pub-8801135711260387/2051502943"
        mInterstitialAd?.adListener
        mInterstitialAd?.loadAd(AdRequest.Builder().build())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /* Caarrusel */

        var carousel = binding.carousel

        carousel.carouselType = CarouselType.BLOCK
        carousel.scaleOnScroll = true
        carousel.scalingFactor = 0.5f
        carousel.showTopShadow =  false
        carousel.showBottomShadow =  false
        carousel.showNavigationButtons =  false


// If you want auto slide, turn this feature on.
        carousel.autoPlay = true
        carousel.autoPlayDelay = 3000

        var list = mutableListOf<CarouselItem>()

        list.add(
            CarouselItem(
                imageUrl = "https://scontent.fmad6-1.fna.fbcdn.net/v/t1.0-9/157299652_1066419703852968_1310339105705357669_n.jpg?_nc_cat=110&ccb=3&_nc_sid=730e14&_nc_ohc=6DTf-3Hikl4AX8Z4S-q&_nc_ht=scontent.fmad6-1.fna&oh=191b16f5ad43bc9e94c9299531271632&oe=60683FFA",

                )
        )

        // Just image URL
        list.add(
            CarouselItem(
                imageUrl = "https://scontent.fmad6-1.fna.fbcdn.net/v/t1.0-9/157738438_1066420527186219_2805972213559985891_n.jpg?_nc_cat=104&ccb=3&_nc_sid=730e14&_nc_ohc=y0udIUGKIzIAX96DftF&_nc_ht=scontent.fmad6-1.fna&oh=415e4511dee178d5f5151784b46d49d1&oe=6065FADB"
            )
        )

        list.add(
            CarouselItem(
                imageUrl = "https://scontent.fmad6-1.fna.fbcdn.net/v/t1.0-9/157056981_1066421660519439_1368035686240487011_n.jpg?_nc_cat=102&ccb=3&_nc_sid=730e14&_nc_ohc=ATtA1q4kGqsAX-g1KJi&_nc_ht=scontent.fmad6-1.fna&oh=1eaeff25a88f28a4f0612cbd78b6d258&oe=6068D490"
            )
        )

        list.add(
            CarouselItem(
                imageUrl = "https://scontent.fmad6-1.fna.fbcdn.net/v/t1.0-9/157836900_1066423933852545_7165577836039767383_n.jpg?_nc_cat=104&ccb=3&_nc_sid=730e14&_nc_ohc=YUtMxsrU-XMAX-qCf1i&_nc_ht=scontent.fmad6-1.fna&oh=2a502c7c4aced32843d4a5e8a017e9cf&oe=60692DFD"
            )
        )

        list.add(
            CarouselItem(
                imageUrl = "https://scontent.fmad6-1.fna.fbcdn.net/v/t1.0-9/157030628_1066424690519136_1185516196094132224_n.jpg?_nc_cat=107&ccb=3&_nc_sid=730e14&_nc_ohc=YnHbnSJoCKYAX8S594J&_nc_ht=scontent.fmad6-1.fna&oh=c21169ee6cf8147554ff19c7c8c61abd&oe=60696406"
            )
        )

        carousel.start()

        carousel.addData(list)
    }

    private fun obtainListMenu() {
        initProgressLoad(true, "Cargando la lista del menu.\nEspere por favor")
        val database = Firebase.database
        val myRef = database.getReference("data")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                loadDataInRecycler(dataSnapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
                initProgressLoad(false, "")
            }
        })

        /*RetrofitClient.instanceMenu.listMenu()
            .enqueue(object : Callback<List<Product>> {
                override fun onResponse(
                    call: Call<List<Product>>,
                    response: Response<List<Product>>
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
                            val dataList: List<Product>
                            dataList = response.body()!!;
                            if (dataList.size > 0) {
                                homeAdapter = HomeAdapter(mainActivity, dataList)
                                binding.containerMain.recyclerView.adapter = homeAdapter
                            }

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

                override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                    Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "t: " + t.message + "casue " + t.cause)
                    initProgressLoad(false, "")
                }


            })*/
    }

    fun loadDataInRecycler(dataSnapshot: DataSnapshot) {
        var dataList: MutableList<Product> = ArrayList()
        for (snapshot in dataSnapshot.children) {
            val product: Product? = snapshot.getValue(Product::class.java)
            //Log.e(TAG, "loadDataInRecycler product: $product")
            if (product != null) {
                dataList.add(product)
            }
        }

        if (dataList.size > 0) {
            homeAdapter = HomeAdapter(homeActivity, dataList)
            binding.containerMain.recyclerView.adapter = homeAdapter

            homeAdapter.setListener(object : HomeAdapter.OnItemClickListener {
                override fun onItemClick(
                    view: View,
                    product: Product,
                    position: Int,
                    longPress: Boolean
                ): Boolean {
                    if (!longPress) {
                        Log.d(TAG, "product: " + product + ", position: " + position)
                        createAlertDialog(product)
                    }
                    return false
                }
            })
        }
        initProgressLoad(false, "")
    }

    private fun createAlertDialog(product: Product) {
        var builder: AlertDialog.Builder = homeActivity.let { AlertDialog.Builder(context) }
        builder.setTitle("Atención")
        builder.setMessage("Ud. sera redigido al portal web de: " + product.name + ", Desea continuar?")
        builder.apply {
            setPositiveButton("Continuar") { dialog, id ->
                if (getInterstitialAd().isLoaded == true) {
                    getInterstitialAd().show()
                } else {
                    loadIntersitial()
                }
                loadInterstitialAd(product)
            }
            setNegativeButton("Cancelar") { dialog, id ->
                dialog.dismiss()
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }

    fun loadInterstitialAd(product: Product) {
        Log.d(TAG, "loadInterstitialAd entro: ")
        getInterstitialAd().adListener = object : AdListener() {
            override fun onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.d(TAG, "loadInterstitialAd onAdLoaded: ")
                getInterstitialAd().show()
            }

            override fun onAdFailedToLoad(errorCode: Int) {
                // Code to be executed when an ad request fails.
                Log.d(TAG, "loadInterstitialAd onAdFailedToLoad: " + errorCode.toString())
                mInterstitialAd = null
            }

            override fun onAdOpened() {
                // Code to be executed when the ad is displayed.
                Log.d(TAG, "loadInterstitialAd onAdOpened: ")
            }

            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Log.d(TAG, "loadInterstitialAd onAdClicked: ")
            }

            override fun onAdLeftApplication() {
                // Code to be executed when the user has left the app.
                Log.d(TAG, "loadInterstitialAd onAdLeftApplication: ")
            }

            override fun onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                Log.d(TAG, "loadInterstitialAd onAdClosed: ")
                moveToWebViewFragment(product)
                mInterstitialAd = null
            }
        }
    }

    private fun moveToWebViewFragment(product: Product) {
        homeActivity.addOrReplaceFragment(
            WebViewFragment.newInstance(product),
            true,
            true,
            true,
            WebViewFragment.TAG
        )
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

    override fun onQueryTextSubmit(p0: String): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String): Boolean {
        if (homeAdapter == null)
            homeAdapter = HomeAdapter(homeActivity)
        homeAdapter.filter(newText)
        return false
    }
}
