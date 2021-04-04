package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.myapplication.Comment.View.CommentFragment
import com.example.myapplication.Detail.View.DetailFragment
import com.example.myapplication.List.View.ListingFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    var bottomNav : BottomNavigationView? = null

    var detailbundle =  Bundle()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val   listingFragment = ListingFragment()
        setFragment(listingFragment!!, "list")

        setupBottomNav()

    }

    fun setupBottomNav()
    {

        bottomNav = findViewById(R.id.bottom_navigation)

        bottomNav?.setOnNavigationItemSelectedListener(object : BottomNavigationView.OnNavigationItemSelectedListener {
            @SuppressLint("NewApi")
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when (item.getItemId()) {
                    R.id.action_list -> {


                        supportFragmentManager.findFragmentByTag("detail")?.onSaveInstanceState(detailbundle) //main code



                        var listingFragment = ListingFragment()
                        setFragment(listingFragment!!, "list")



                        return true
                    }

                    R.id.action_detail -> {

//                        supportFragmentManager.findFragmentByTag("detail")?.onSaveInstanceState(detailbundle)



                        var detailFragment = DetailFragment()
                        detailFragment.setArguments(detailbundle);


                       setFragment(detailFragment,"detail")




                        return true
                    }

                    R.id.action_cmmnt -> {

                        supportFragmentManager.findFragmentByTag("detail")?.onSaveInstanceState(detailbundle)

                        var cmntFragment = CommentFragment()
                        setFragment(cmntFragment,"comment")


                        return true
                    }


                }


                return true
            }
        })


    }







    fun setFragment(fragment: Fragment, tag: String)
    {

        val fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment, tag)
                .addToBackStack(tag)
                .commit();

    }




}