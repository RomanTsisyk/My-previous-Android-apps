package tsisyk.app.diary.activities

import tsisyk.app.diary.R
import tsisyk.app.diary.utilities.FileManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditTextActivity : AppCompatActivity() {
    private var mTitleTextView: TextView? = null
    private var mEditText: EditText? = null
    private var mOKButton: Button? = null
    private var mCancelButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_edit_text)

        mTitleTextView = findViewById<View>(R.id.activity_dialog_edit_text_title) as TextView
        mTitleTextView!!.text = intent.getStringExtra(ARG_TITLE)

        mEditText = findViewById<View>(R.id.activity_dialog_edit_text_edit) as EditText
        mEditText!!.setText(intent.getStringExtra(ARG_DEFAULT_TEXT))
        mEditText!!.setSelection(mEditText!!.text.length)

        mOKButton = findViewById<View>(R.id.activity_dialog_edit_text_ok) as Button
        mOKButton!!.setOnClickListener {
            val format = intent.getStringExtra(ARG_FORMAT)
            val newName = mEditText!!.text.toString()
            val defaultName = intent.getStringExtra(ARG_DEFAULT_TEXT)
            val directory = intent.getStringExtra(ARG_DIRECTORY)
            if (newName == defaultName || !FileManager
                            .fileExistsInExternalPublicStorage(directory, newName + format)) {
                sendResult(Activity.RESULT_OK, newName)
            } else {
                Toast.makeText(applicationContext, R.string.file_exists,
                        Toast.LENGTH_SHORT).show()
            }
        }

        mCancelButton = findViewById<View>(R.id.activity_dialog_edit_text_cancel) as Button
        mCancelButton!!.setOnClickListener { sendResult(Activity.RESULT_CANCELED, null) }
    }

    private fun sendResult(resultCode: Int, stringEditText: String?) {
        val intent = Intent()
        intent.putExtra(EXTRA_TEXT, stringEditText)
        setResult(resultCode, intent)
        finish()
    }

    companion object {

        private const val ARG_TITLE = "Title"
        private const val ARG_DEFAULT_TEXT = "DefaultText"
        private const val ARG_DIRECTORY = "Directory"
        private const val ARG_FORMAT = "Format"
        private const val EXTRA_TEXT = "Text"

        fun newIntent(packageContext: Context, title: String, directory: String,
                      defaultText: String, format: String): Intent {
            val intent = Intent(packageContext, EditTextActivity::class.java)
            intent.putExtra(ARG_TITLE, title)
            intent.putExtra(ARG_DEFAULT_TEXT, defaultText)
            intent.putExtra(ARG_DIRECTORY, directory)
            intent.putExtra(ARG_FORMAT, format)
            return intent
        }

        fun getResult(intent: Intent): String {
            return intent.getStringExtra(EXTRA_TEXT)
        }
    }
}
