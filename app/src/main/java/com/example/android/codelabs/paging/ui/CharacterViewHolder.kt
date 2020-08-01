/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.codelabs.paging.ui

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.R.anim.scale_xy
import com.example.android.codelabs.paging.R.anim.slide_from_top
import com.example.android.codelabs.paging.model.Result


/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.character_name)
    private val avatar: ImageView = view.findViewById(R.id.character_image)
    private val card: CardView = view.findViewById(R.id.card)
    private var result: Result? = null
    private val resources = itemView.resources
    private val context = itemView.context
    var scrollDirection = ScrollDirection.DOWN


    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.repo_view_item, parent, false)
            return CharacterViewHolder(view)
        }
    }

    fun bind(result: Result?) {
        if (result == null) {
            name.text = resources.getString(R.string.loading)
        } else {
            showRepoData(result)
        }
    }

    private fun showRepoData(result: Result) {
        this.result = result
        if (result.name.isEmpty()) {
            name.text = resources.getString(R.string.no_results)
        } else name.text = result.name
        Glide.with(itemView.context)
                .load(result.image)
                .transform(CircleCrop())
                .error(R.drawable.loading)
                .into(avatar)
        setCardDecoration()
        animateView(card)

        card.setOnClickListener {
            val startIntent = Intent(it.context, CharacterDetailsActivity::class.java)
            startIntent.putExtra("image", result.image)
            startIntent.putExtra("gender", result.gender)
            startIntent.putExtra("name", result.name)
            startIntent.putExtra("status", result.status)
            startIntent.putExtra("location_current", result.location.name)
            startIntent.putExtra("location_origin", result.origin.name)
            it.context.startActivity(startIntent)
        }
    }

    private fun setCardDecoration() {
        try {
            Log.i("avatar.drawable :", avatar.drawable.toString())
            val drawable = avatar.drawable
            val bmp: Bitmap = (drawable.current as BitmapDrawable).bitmap
            Palette.from(bmp).generate { palette ->
                val backgroundColor = palette!!.getDominantColor(ContextCompat.getColor(context, R.color.colorPrimaryDark))
                card.setBackgroundColor(backgroundColor)
                val textColor = if (isColorDark(backgroundColor)) Color.WHITE else Color.BLACK
                name.setTextColor(textColor)
            }

        } catch (e: Exception) {
            println("Error $e")
        }
    }


    private fun isColorDark(color: Int): Boolean {
        val darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255
        return darkness >= 0.5
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