package tsisyk.app.diary.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.OnClickListener
import android.view.View.OnLongClickListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import java.util.ArrayList

import tsisyk.app.diary.R
import tsisyk.app.diary.model.Sound
import tsisyk.app.diary.utilities.FileManager
import tsisyk.app.diary.views.ItemTouchHelper.ItemTouchHelperAdapter
import tsisyk.app.diary.views.ItemTouchHelper.SimpleItemTouchHelperCallback

class ListRecordsActivity : AppCompatActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: SoundAdapter? = null
    private var mCurrentSoundHolder: SoundHolder? = null

    private val sounds: MutableList<Sound>
        get() {
            val sounds = ArrayList<Sound>()
            val files = FileManager
                    .getFilesWithExternalPublicStorage(intent.getStringExtra(ARG_DIRECTORY_SOUND))
            for (file in files!!) {
                val sound = Sound(file)
                sound.sutupSpecifications()
                sounds.add(sound)
            }
            return sounds
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_sound)

        if (supportActionBar != null){
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }

        mRecyclerView = findViewById<View>(R.id.activity_list_sound_recycle_view) as RecyclerView
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_RENAME) {
            if (resultCode == Activity.RESULT_OK) {
                mCurrentSoundHolder!!.mSound!!
                        .rename(EditTextActivity.getResult(data!!) + intent
                                .getStringExtra(ARG_FORMAT))
                mAdapter!!.notifyItemChanged(mCurrentSoundHolder!!.adapterPosition)
            }
        }
    }

    private fun updateUI() {
        val sounds = sounds
        if (mAdapter == null) {
            mAdapter = SoundAdapter(sounds)
            mRecyclerView!!.adapter = mAdapter
            val callback = SimpleItemTouchHelperCallback(mAdapter!!)
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(mRecyclerView)
        } else {
            mAdapter!!.setSounds(sounds)
            mAdapter!!.notifyDataSetChanged()
        }
    }

    private inner class SoundHolder(itemView: View) : RecyclerView.ViewHolder(itemView), OnClickListener, OnLongClickListener {

        var mSound: Sound? = null
        private val mName: TextView
        private val mDate: TextView
        private val mTime: TextView

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
            mName = itemView.findViewById<View>(R.id.list_item_sound_name_text_view) as TextView
            mDate = itemView.findViewById<View>(R.id.list_item_sound_date_text_view) as TextView
            mTime = itemView.findViewById<View>(R.id.list_item_sound_time_text_view) as TextView
        }

        fun bindSound(sound: Sound) {
            mSound = sound
            mName.text = mSound!!.name
            mDate.text = mSound!!.date
            mTime.text = mSound!!.time
        }


        override fun onClick(view: View) {
            val intent = mSound!!.file?.let {
                PlayerActivity
                        .newIntent(applicationContext, it)
            }
            startActivity(intent)
        }

        override fun onLongClick(view: View): Boolean {
            mCurrentSoundHolder = this
            val intent = mSound!!.name?.let {
                EditTextActivity
                        .newIntent(applicationContext, getString(R.string.rename_file),
                                intent.getStringExtra(ARG_DIRECTORY_SOUND),
                                it,
                                intent.getStringExtra(ARG_FORMAT))
            }
            startActivityForResult(intent, REQUEST_RENAME)
            return true
        }

    }

    private inner class SoundAdapter(private var mSounds: MutableList<Sound>?) : RecyclerView.Adapter<SoundHolder>(), ItemTouchHelperAdapter {

        fun setSounds(sounds: MutableList<Sound>) {
            mSounds = sounds
        }

        override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SoundHolder {
            val inflater = LayoutInflater.from(this@ListRecordsActivity)
            val view = inflater.inflate(R.layout.list_item_sound, viewGroup, false)
            return SoundHolder(view)
        }

        override fun onBindViewHolder(soundHolder: SoundHolder, i: Int) {
            val sound = mSounds!![i]
            soundHolder.bindSound(sound)
        }

        override fun getItemCount(): Int {
            return mSounds!!.size
        }

        override fun onItemDismiss(position: Int) {
            mSounds!![position].delete()
            mSounds!!.removeAt(position)
            notifyItemRemoved(position)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.me -> {
                val intent = Intent(this, AboutMeActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {

        private val ARG_DIRECTORY_SOUND = "ARG_DIRECTORY_SOUND"
        private val ARG_FORMAT = "Format"
        private val REQUEST_RENAME = 1

        fun newIntent(packageContext: Context, directorySound: String, format: String): Intent {
            val intent = Intent(packageContext, ListRecordsActivity::class.java)
            intent.putExtra(ARG_DIRECTORY_SOUND, directorySound)
            intent.putExtra(ARG_FORMAT, format)
            return intent
        }
    }
}
