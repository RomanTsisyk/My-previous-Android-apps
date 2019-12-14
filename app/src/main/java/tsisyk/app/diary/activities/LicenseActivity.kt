package tsisyk.app.diary.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import tsisyk.app.diary.R
import android.webkit.WebView

class LicenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)

        if (supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        val myWebView = findViewById<View>(R.id.web) as WebView
        myWebView.loadUrl("file:///android_asset/license.htm")


    }

    fun onClick(view: View) {
        val intent = Intent(this, DiaryActivity::class.java)
        startActivity(intent)
    }

}
