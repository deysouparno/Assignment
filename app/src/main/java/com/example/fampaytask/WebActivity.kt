package com.example.fampaytask

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.fampaytask.databinding.ActivityWebBinding
import im.delight.android.webview.AdvancedWebView

class WebActivity : AppCompatActivity(), AdvancedWebView.Listener {

    private lateinit var binding: ActivityWebBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url =
            if (intent.hasExtra("link")) intent.getStringExtra("link") else "https://google.com/"
        val title =
            if (intent.hasExtra("title")) intent.getStringExtra("title") else getString(R.string.app_name)

        binding.webview.apply {
            setListener(this@WebActivity, this@WebActivity)
            setMixedContentAllowed(false)
            url?.let { loadUrl(it) }
        }

        binding.appTitle.text = title
        binding.homeButton.setOnClickListener { onBackPressed() }

    }

    override fun onBackPressed() {
        if (intent.hasExtra("which")) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            finishAffinity()
            startActivity(intent)
        } else {
            super.onBackPressed()
        }
    }

    override fun onPageStarted(url: String?, favicon: Bitmap?) {
        binding.progress.visibility = View.VISIBLE
    }

    override fun onPageFinished(url: String?) {
        binding.progress.visibility = View.GONE
    }

    override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
        binding.progress.visibility = View.GONE
        binding.errorText.visibility = View.VISIBLE
    }

    override fun onDownloadRequested(
        url: String,
        suggestedFilename: String,
        mimeType: String,
        contentLength: Long,
        contentDisposition: String,
        userAgent: String
    ) = Unit

    override fun onExternalPageRequest(url: String) = Unit

}