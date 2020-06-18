package tsisyk.app.desertandcandies.view.alldeserts

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.*
import tsisyk.app.desertandcandies.view.desert.DesertActivity
import kotlinx.android.synthetic.main.activity_all_desertss.*
import kotlinx.android.synthetic.main.content_all_desertss.*
import tsisyk.app.desertandcandies.R
import tsisyk.app.desertandcandies.viewmodel.AllDesertViewModel

class AllDesertsActivity : AppCompatActivity(), ItemDragListener {

    private var gridAdapter = DesertGridAdapter(mutableListOf())
    private var listAdapter = DesertListAdapter(mutableListOf(), this)
    private var gridState = GridState.GRID
    lateinit var layoutManager: GridLayoutManager

    private lateinit var listMenuItem: MenuItem
    private lateinit var gridMenuItem: MenuItem
    private lateinit var viewModel: AllDesertViewModel
    private lateinit var listItemDecoration: RecyclerView.ItemDecoration
    private lateinit var gridItemDecoration: RecyclerView.ItemDecoration
    private lateinit var itemTouchHelper: ItemTouchHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_desertss)
        setSupportActionBar(toolbar)
        setupItemTouchHelper()
        setupViewModel()
        setupLayoutManager()
        setupItemDecoration()
        setupScrollListener()
        fab.setOnClickListener { startActivity(Intent(this, DesertActivity::class.java)) }
    }

    private fun setupScrollListener() {
        desertsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                listAdapter.scrollDirection = (if (dy > 0) {
                    ScrollDirection.DOWN
                } else ScrollDirection.UP)
            }
        })
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(AllDesertViewModel::class.java)
        viewModel.getAllDesertsLiveData().observe(this, Observer { deserts ->
            deserts?.let {
                gridAdapter.updateDeserts(deserts)
                listAdapter.updateDeserts(deserts)
            }
        })
    }

    private fun setupItemDecoration() {
        val spacingListPixels = resources.getDimensionPixelSize(R.dimen.desert_card_list_lauout_margin)
        val spacingGridPixels = resources.getDimensionPixelSize(R.dimen.desert_card_grid_lauout_margin)
        listItemDecoration = SpacingItemDecoration(1, spacingListPixels)
        gridItemDecoration = SpacingItemDecoration(2, spacingGridPixels)
        desertsRecyclerView.addItemDecoration(gridItemDecoration)
    }

    private fun setupLayoutManager() {
        layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return (gridAdapter.spanSizePosition(position))
            }
        }
        desertsRecyclerView.layoutManager = layoutManager
        desertsRecyclerView.adapter = gridAdapter
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        listMenuItem = menu.findItem(R.id.list_view)
        gridMenuItem = menu.findItem(R.id.grid_view)

        return when (gridState) {
            GridState.GRID -> {
                listMenuItem.isEnabled = true
                gridMenuItem.isEnabled = false
                true
            }
            GridState.LIST -> {
                gridMenuItem.isEnabled = true
                listMenuItem.isEnabled = false
                true
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_clear_all -> {
                viewModel.clearAllDeserts()
                true
            }
            R.id.list_view -> {
                desertsRecyclerView.adapter = listAdapter
                gridState = GridState.LIST
                updateRecycleView(1, listItemDecoration, gridItemDecoration)
                return true
            }
            R.id.grid_view -> {
                desertsRecyclerView.adapter = gridAdapter
                gridState = GridState.GRID
                updateRecycleView(2, gridItemDecoration, listItemDecoration)
                return true

            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupItemTouchHelper() {
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(listAdapter))
        itemTouchHelper.attachToRecyclerView(desertsRecyclerView)
        ItemTouchHelper(GridItemTouchHelperCallback(gridAdapter)).attachToRecyclerView(desertsRecyclerView)
    }

    private fun updateRecycleView(spanCount: Int, addItemDecoration: RecyclerView.ItemDecoration,
                                  removeItemDecoration: RecyclerView.ItemDecoration) {
        layoutManager.spanCount = spanCount
        gridAdapter.toManyCaloriesSpanSize = spanCount
        desertsRecyclerView.removeItemDecoration(removeItemDecoration)
        desertsRecyclerView.addItemDecoration(addItemDecoration)
    }

    override fun onItemDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

}
