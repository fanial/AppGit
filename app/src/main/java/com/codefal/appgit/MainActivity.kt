package com.codefal.appgit

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.codefal.appgit.databinding.ActivityMainBinding
import com.codefal.appgit.view.FavoriteFragment
import com.codefal.appgit.view.SettingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                val fragment = FavoriteFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
            R.id.theme -> {
                val fragment = SettingFragment()
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.nav_host_fragment_container, fragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}