package tsisyk.app.desertandcandies.view.desert

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import tsisyk.app.desertandcandies.R
import tsisyk.app.desertandcandies.model.AttributeStore
import tsisyk.app.desertandcandies.model.Avatar
import tsisyk.app.desertandcandies.view.avatars.AvatarAdapter
import tsisyk.app.desertandcandies.view.avatars.AvatarBottomDialogFragment
import kotlinx.android.synthetic.main.activity_desert.*
import tsisyk.app.desertandcandies.model.AttributeType
import tsisyk.app.desertandcandies.viewmodel.DesertViewModel


class DesertActivity : AppCompatActivity(), AvatarAdapter.AvatarListener {

    private lateinit var viewModel: DesertViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_desert)

        viewModel = ViewModelProviders.of(this).get(DesertViewModel::class.java)

        configureUI()
        configureSpinnerAdapters()
        configureSpinnerListeners()
        configureEditText()
        configureClickListeners()
        configureLiveDataObservers()
    }

    private fun configureUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = getString(R.string.add_desert)
        if (viewModel.drawable != 0) hideTapLabel()
    }

    private fun configureSpinnerAdapters() {
        price.adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, AttributeStore.PRICE)
        calories.adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, AttributeStore.CALORIES)
        taste.adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item, AttributeStore.TASTE)
    }

    private fun configureSpinnerListeners() {
        price.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.attributeSelected(AttributeType.PRICE, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        calories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.attributeSelected(AttributeType.CALORIES, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
        taste.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.attributeSelected(AttributeType.TASTE, position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun configureEditText() {
        nameEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.name = s.toString()
            }
        })
    }

    private fun configureClickListeners() {
        avatarImageView.setOnClickListener {
            val bottomDialogFragment = AvatarBottomDialogFragment.newInstance()
            bottomDialogFragment.show(supportFragmentManager, "AvatarBottomDialogFragment")
        }

        saveButton.setOnClickListener {
            // TODO: handle save button clicked
        }
    }

    override fun avatarClicked(avatar: Avatar) {
        viewModel.drawableSelected(avatar.drawable)
        hideTapLabel()
    }

    private fun hideTapLabel() {
        tapLabel.visibility = View.INVISIBLE
    }

    fun configureLiveDataObservers() {
        viewModel.getDesertLiveData().observe(this, Observer { desert ->
            desert.let {
                hitPoints.text = desert.pointsRanking.toString()
                avatarImageView.setImageResource(desert.drawable)
                nameEditText.setText(desert.name)
            }
        })
    }
}
