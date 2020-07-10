package tsisyk.app.diary.activities

import tsisyk.app.diary.R
import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class CancelDialogActivity : AppCompatActivity() {
    private var mTitleTextView: TextView? = null
    private var mPositiveButton: Button? = null
    private var mCancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_cancel)

        mTitleTextView = findViewById<View>(R.id.activity_dialog_cancel_title) as TextView
        mTitleTextView!!.text = intent.getStringExtra(TITLE)

        mPositiveButton = findViewById<View>(R.id.activity_dialog_cancel_cancel_ok) as Button
        mPositiveButton!!.text = intent.getStringExtra(POSITIVE_BUTTON)
        mPositiveButton!!.setOnClickListener { sendResult(Activity.RESULT_OK) }

        mCancelButton = findViewById<View>(R.id.activity_dialog_cancel_cancel) as Button
        mCancelButton!!.setOnClickListener { sendResult(Activity.RESULT_CANCELED) }
    }

    private fun sendResult(resultCode: Int) {
        val intent = Intent()
        setResult(resultCode, intent)
        finish()
    }

    companion object {

        private const val TITLE = "Title"
        private const val POSITIVE_BUTTON = "NAME_POSITIVE_BUTTON"

        fun newIntent(packageContext: Context, title: String,
                      namePositiveButton: String): Intent {
            val intent = Intent(packageContext, CancelDialogActivity::class.java)
            intent.putExtra(TITLE, title)
            intent.putExtra(POSITIVE_BUTTON, namePositiveButton)
            return intent
        }
    }
}
