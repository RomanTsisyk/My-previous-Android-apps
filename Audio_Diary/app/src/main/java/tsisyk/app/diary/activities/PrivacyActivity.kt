package tsisyk.app.diary.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.webkit.WebView

import tsisyk.app.diary.R

class PrivacyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy)

        if (supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        val myWebView = findViewById<View>(R.id.web_privacy) as WebView
        myWebView.loadUrl("file:///android_asset/privacy.htm")

    }


    fun onClick(view: View) {
        val intent = Intent(this, DiaryActivity::class.java)
        startActivity(intent)
    }

}
