package tsisyk.app.rssfeed

import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_web.*
import kotlinx.android.synthetic.main.activity_web.toolbar

class WebActivity : AppCompatActivity() {
    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        setSupportActionBar(toolbar)

        val url = intent.getStringExtra("url")
        val titlePromo = intent.getStringExtra("title")
        toolbar.subtitle = titlePromo
        webView = findViewById(R.id.web)
        webView.settings.javaScriptEnabled = true;
        webView.loadUrl(url)

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, url)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}