package com.example.foodhub.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import androidx.annotation.Nullable
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.blogspot.atifsoftwares.animatoolib.Animatoo
import com.example.foodhub.R
import com.example.foodhub.databinding.ActivityHomeBinding
import com.example.foodhub.fragments.fragmentsEntrada.LoginFragment
import com.example.foodhub.fragments.fragmentsHome.*
import com.example.foodhub.fragments.webViewFragment.WebViewFragment
import com.google.android.material.navigation.NavigationView
import com.infideap.drawerbehavior.AdvanceDrawerLayout


class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var TAG: String = HomeActivity::class.java.simpleName

    private lateinit var binding: ActivityHomeBinding
    lateinit var mFragmentManager: FragmentManager

    lateinit var acercaFragment: AcercaFragment
    lateinit var configFragment: ConfigFragment
    lateinit var homeFragment: HomeFragment
    lateinit var politicaFragment: PoliticaFragment
    lateinit var userFragment: UserFragment
    lateinit var loginFragment: LoginFragment
    lateinit var drawer: AdvanceDrawerLayout
    lateinit var mFragmentTag:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mFragmentManager = supportFragmentManager
        config()
        // Inicializando este libreria
        drawer = binding.drawerLayout

        drawer.useCustomBehavior(GravityCompat.START)
        drawer.useCustomBehavior(GravityCompat.END)
        acercaFragment = AcercaFragment()
        configFragment = ConfigFragment()
        homeFragment = HomeFragment()
        politicaFragment = PoliticaFragment()
        userFragment = UserFragment()
        loginFragment = LoginFragment()


        addOrReplaceFragment(homeFragment, false, false, false, homeFragment.TAG)

        //Creando el toogle del Drawer
        val toolbar = binding.incluyedLayout.toolbar

        setSupportActionBar(toolbar)
        var actionBar = supportActionBar
        actionBar?.title = ""

        //Creando el toogle del Drawer
        val drawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            toolbar,
            (((R.string.open))),
            ((R.string.close))
        ) {
        }

        drawerToggle.isDrawerIndicatorEnabled = true
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        binding.navView.setNavigationItemSelectedListener(this)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                addOrReplaceFragment(homeFragment, true, false, true, homeFragment.TAG)
            } /*
            R.id.nav_perfil -> {
                addOrReplaceFragment(userFragment, true, true, true, userFragment.TAG)
            }
            R.id.nav_configuracion -> {
                addOrReplaceFragment(configFragment, true, true, true, configFragment.TAG)
            } */
            R.id.nav_nosotros -> {
                addOrReplaceFragment(acercaFragment, true, false, true, acercaFragment.TAG)
            }
            R.id.nav_salir -> {
                val n = Intent(this, MainActivity::class.java)
                startActivity(n)
                Animatoo.animateDiagonal(this)
                finishAffinity()
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true

    }

    fun addOrReplaceFragment(
        @Nullable fragment: Fragment,
        replaceFragment: Boolean,
        addToBackStack: Boolean,
        isAnimated: Boolean,
        @Nullable tag: String
    ) {
        //   transaction.setCustomAnimations(R.anim.slide_up, R.anim.slide_down)
        Log.d(
            TAG,
            "openFragment fragment: " + fragment + ",tag: " + tag
        )
        var transaction = mFragmentManager.beginTransaction()

        if (isAnimated) {
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.setCustomAnimations(
                R.anim.transition_slide_right_in,
                R.anim.transition_slide_left_out,
                android.R.anim.slide_in_left,
                R.anim.transition_slide_right_out
            );
        }

        if (replaceFragment)
            transaction.replace(R.id.fragment_container_home, fragment, tag);
        else
            transaction.add(R.id.fragment_container_home, fragment, tag);

        if (addToBackStack)
            transaction.addToBackStack(null);

        //transaction.addToBackStack(tag)
        try {
            transaction.commit()
        } catch (e: Exception) {
            transaction.commitAllowingStateLoss()
        }
    }

    fun onRemoveFragmentToStack(withBackPressed: Boolean) {
        if (withBackPressed) {
            if (recursivePopBackStack(mFragmentManager)) return
            super.onBackPressed()
        } else {
            recursivePopBackStack(mFragmentManager)
        }
    }

    override fun onBackPressed() {
        Log.d(TAG, "onBackPressed 1 mFragmentTag: "+ mFragmentTag)
        val drawer = binding.drawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            Log.d(TAG,"onBackPressed 2 mFragmentTag: "+mFragmentTag)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return
        }

        if(mFragmentTag.equals(homeFragment.TAG)) {
            Log.d(TAG,"onBackPressed 3 mFragment: "+mFragmentTag)
            createAlertDialog()
            return
        }
        if(supportFragmentManager.backStackEntryCount==0) {
            mFragmentTag=homeFragment.TAG
            Log.d(TAG,"onBackPressed 4 mFragmentTag: "+ mFragmentTag)
            addOrReplaceFragment(homeFragment, true,false,true,mFragmentTag)
            return
        }
        if(recursivePopBackStack(supportFragmentManager)) {
            Log.d(TAG,"onBackPressed 5 mFragment: "+mFragmentTag)
            return
        }
            super.onBackPressed()
    }

    fun config() {
        // window.statusBarColor = Color.rgb(246,46,15)
        window.setBackgroundDrawableResource(R.color.white)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar, menu)
        return true
    }

    private fun recursivePopBackStack(fragmentManager: FragmentManager): Boolean {
        if (fragmentManager.fragments != null) {
            for (fragment in fragmentManager.fragments) {
                if (fragment != null && fragment.isVisible) {
                    val popped = recursivePopBackStack(fragment.childFragmentManager)
                    if (popped) {
                        return true
                    }
                }
            }
        }
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            return true
        }
        return false
    }

    private fun createAlertDialog() {
        var builder: AlertDialog.Builder = this.let { AlertDialog.Builder(this) }
        builder.setTitle("Action")
        builder.setMessage("Esta a punto de salir de la aplicación. \nDesea salir de la aplicación?")
        builder.apply {
            setPositiveButton("Salir") { dialog, id ->
                exit()
            }
            setNegativeButton("No") { dialog, id ->
                dialog.dismiss()
            }
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun exit() {
        val n = Intent(this, MainActivity::class.java)
        startActivity(n)
        Animatoo.animateDiagonal(this)
        finishAffinity()


    }

}