package tsisyk.app.kittyrank.view.desert

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import tsisyk.app.kittyrank.R
import tsisyk.app.kittyrank.model.AttributeStore
import tsisyk.app.kittyrank.model.Avatar
import tsisyk.app.kittyrank.view.avatars.AvatarAdapter
import tsisyk.app.kittyrank.view.avatars.AvatarBottomDialogFragment
import kotlinx.android.synthetic.main.activity_desert.*
import tsisyk.app.kittyrank.databinding.ActivityDesertBinding
import tsisyk.app.kittyrank.model.AttributeType
import tsisyk.app.kittyrank.viewmodel.DesertViewModel


class DesertActivity : AppCompatActivity(), AvatarAdapter.AvatarListener {

    private lateinit var viewModel: DesertViewModel

    lateinit var binding: ActivityDesertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_desert)
        viewModel = ViewModelProviders.of(this).get(DesertViewModel::class.java)
        binding.viewmodel = viewModel
        configureUI()
        configureSpinnerAdapters()
        configureSpinnerListeners()
        configureClickListeners()
        configureLiveDataObservers()
    }

    private fun configureUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.add_desert)
        if (viewModel.drawable != 0) hideTapLabel()
    }

    private fun configureSpinnerAdapters() {
        opinion.adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, AttributeStore.MY_OPINION)
        kitty_opinion.adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, AttributeStore.KITTIES_OPINION)
        emoji.adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, AttributeStore.EMOTIONS)
        age.adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, AttributeStore.AGE)
    }

    private fun configureSpinnerListeners() {
        opinion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.attributeSelected(AttributeType.MY_OPINION, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        kitty_opinion.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.attributeSelected(AttributeType.KITTY_OPINION, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        emoji.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.attributeSelected(AttributeType.EMOJI, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        age.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.attributeSelected(AttributeType.AGE, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun configureClickListeners() {
        avatarImageView.setOnClickListener {
            val bottomDialogFragment = AvatarBottomDialogFragment.newInstance()
            bottomDialogFragment.show(supportFragmentManager, "AvatarBottomDialogFragment")
        }

    }

    override fun avatarClicked(avatar: Avatar) {
        viewModel.drawableSelected(avatar.drawable)
        hideTapLabel()
    }

    private fun hideTapLabel() {
        tapLabel.visibility = View.GONE
    }

    private fun configureLiveDataObservers() {
        viewModel.getDesertLiveData().observe(this, Observer { desert ->
            desert.let {
                hitPoints.text = desert.pointsRanking.toString()
                avatarImageView.setImageResource(desert.drawable)
                nameEditText.setText(desert.name)
            }
        })

        viewModel.getSaveLiveData().observe(this, Observer { saved ->
            saved?.let {
                if (saved) {
                    Toast.makeText(this, getString(R.string.desert_saved), Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.error_saving_desert), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}
