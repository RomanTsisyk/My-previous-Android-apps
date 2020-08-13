package tsisyk.app.rickandmorty.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.format.DateFormat
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.android.codelabs.paging.R
import kotlinx.android.synthetic.main.activity_characters_details.*
import java.io.File
import java.io.FileOutputStream
import java.util.*

/* This activity can be created without viewmodel.
It doesn`t care about screen rotation, because all widgets have id.
ImageView and TextView will save its state after recreating * */

class CharacterDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_details)

        val image = intent.getStringExtra("image")
        val name = intent.getStringExtra("name")
        val gender = intent.getStringExtra("gender")
        val status = intent.getStringExtra("status")
        val locationCurrent = intent.getStringExtra("location_current")
        val locationOrigin = intent.getStringExtra("location_origin")

        bindCharacterDetail(name, gender, status, locationCurrent, locationOrigin, image)

        fab_back.setOnClickListener { onBackPressed() }

        share_button.setOnClickListener {
            if (checkAndRequestPermissions()) {
                takeScreenshot()
            }
        }
    }


    private fun bindCharacterDetail(name: String?, gender: String?, status: String?, locationCurrent: String?, locationOrigin: String?, image: String?) {
        character_detail_name.text = name
        character_detail_gender.text = gender
        character_detail_status.text = status
        character_detail_location_current.text = locationCurrent
        character_detail_location_origin.text = locationOrigin

        Glide.with(this)
                .load(image)
                .transform(CircleCrop())
                .error(R.drawable.loading)
                .into(character_detail_image)
    }

    private fun checkAndRequestPermissions(): Boolean {
        val permissionWrite = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val listPermissionsNeeded: MutableList<String> = ArrayList()

        if (permissionWrite != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            val requestCode = 1
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), requestCode)
            return false
        }
        return true
    }

    @Suppress("DEPRECATION")
    fun takeScreenshot() {
        val now = Date()
        DateFormat.format("yyyy-MM-dd_hh:mm:ss", now)
        try {
            // image naming and path  to include sd card  appending name you choose for file
            val mPath: String = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg"

            // create bitmap screen capture
            val v1: View = window.decorView.rootView
            v1.isDrawingCacheEnabled = true
            val bitmap: Bitmap = Bitmap.createBitmap(v1.drawingCache)
            v1.isDrawingCacheEnabled = false
            val imageFile = File(mPath)
            val outputStream = FileOutputStream(imageFile)
            val quality = 100
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            outputStream.flush()
            outputStream.close()
            openScreenshot(imageFile)
        } catch (e: Throwable) {
            // Several error may come out with file handling or DOM
            e.printStackTrace()
        }


    }

    private fun openScreenshot(imageFile: File) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        val uri: Uri = Uri.fromFile(imageFile)
        intent.setDataAndType(uri, "image/*")
        startActivity(intent)
    }

}