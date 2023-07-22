package com.itzcafe.creativeiq.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.itzcafe.creativeiq.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {

    private var context = this
    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        clickListeners()
    }

    private fun clickListeners() {
        binding.run {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener { onBackPressed() }
        }
    }
}