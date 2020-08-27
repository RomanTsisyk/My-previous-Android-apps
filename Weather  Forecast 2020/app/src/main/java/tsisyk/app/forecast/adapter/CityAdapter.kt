package tsisyk.app.forecast.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import tsisyk.app.forecast.R
import tsisyk.app.forecast.R.anim.scale_xy
import tsisyk.app.forecast.R.anim.slide_from_top
import tsisyk.app.forecast.network.model.Location
import tsisyk.app.forecast.ForecastActivity
import kotlinx.android.synthetic.main.city_card_item.view.*


class CityAdapter(private val context: Context) :
    RecyclerView.Adapter<CityAdapter.ViewHolder>() {
    private var list: List<Location> = ArrayList()
    private var scrollDirection = ScrollDirection.DOWN

    fun setLocationList(list: List<Location>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].title
        holder.rootView.setOnClickListener {
            val intent = Intent(context, ForecastActivity::class.java)
            intent.putExtra("Location", list[position].woeid)
            intent.putExtra("LocationName", list[position].title)
            context.startActivity(intent)
        }
        animateView(holder.rootView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.city_card_item, parent, false
            )
        )
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val name = v.tv_location_name!!
        val rootView = v.child_root!!
    }

    fun animateView(viewAnimated: View) {
        if (viewAnimated.animation == null) {
            val animId = if (scrollDirection == ScrollDirection.DOWN) scale_xy else slide_from_top
            val animation = AnimationUtils.loadAnimation(viewAnimated.context, animId)
            viewAnimated.animation = animation
        }
    }

}


enum class ScrollDirection {
    UP, DOWN
}