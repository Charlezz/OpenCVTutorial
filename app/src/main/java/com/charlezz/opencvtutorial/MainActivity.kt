package com.charlezz.opencvtutorial

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.charlezz.opencvtutorial.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val navHostFragment by lazy{
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private val navController by lazy{ navHostFragment.navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.toolbar.title = arguments?.getString("title", "OpenCVTutorial")
        }

//        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

    }

    override fun onBackPressed() {
        if(navController.previousBackStackEntry==null){
            AlertDialog.Builder(this)
                .setMessage("정말 종료하시겠어요?")
                .setPositiveButton("확인") { _, _ -> finish() }
                .setNegativeButton("취소",null)
                .show()
        }else{
            super.onBackPressed()
        }
    }

}