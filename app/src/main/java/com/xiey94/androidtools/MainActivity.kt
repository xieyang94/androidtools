package com.xiey94.androidtools

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xiey94.androidtools.main.ToolListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, ToolListFragment.newInstance("", ""))
            .commitAllowingStateLoss()

    }
}
