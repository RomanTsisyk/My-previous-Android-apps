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

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.android.codelabs.paging.R
import com.example.android.codelabs.paging.model.Repo
import com.example.android.codelabs.paging.model.Result

/**
 * View Holder for a [Repo] RecyclerView list item.
 */
class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.character_name)
    private val description: TextView = view.findViewById(R.id.repo_location)
    private val stars: TextView = view.findViewById(R.id.chapter_status)
    private val language: TextView = view.findViewById(R.id.chapter_gender)
    private val forks: TextView = view.findViewById(R.id.chapter_id)

    private var result: Result? = null

    init {
        view.setOnClickListener {
//            repo?.url?.let { url ->
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                view.context.startActivity(intent)
//            }
        }
    }

    fun bind(result: Result?) {
        if (result == null) {
            val resources = itemView.resources
            name.text = resources.getString(R.string.loading)
            description.visibility = View.GONE
            language.visibility = View.GONE
            stars.text = resources.getString(R.string.unknown)
            forks.text = resources.getString(R.string.unknown)
        } else {
            showRepoData(result)
        }
    }

    private fun showRepoData(result: Result) {
        this.result = result
        name.text = result.name

        // if the description is missing, hide the TextView
        var descriptionVisibility = View.GONE
        if (result.name != null) {
            description.text = result.name
            descriptionVisibility = View.VISIBLE
        }
        description.visibility = descriptionVisibility

        stars.text = result.status
        forks.text = result.gender

        // if the language is missing, hide the label and the value
        var languageVisibility = View.GONE
        if (!result.location.name.isNullOrEmpty()) {
            val resources = this.itemView.context.resources
            language.text = resources.getString(R.string.language, result.location.name)
            languageVisibility = View.VISIBLE
        }
        language.visibility = languageVisibility
    }

    companion object {
        fun create(parent: ViewGroup): CharacterViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.repo_view_item, parent, false)
            return CharacterViewHolder(view)
        }
    }
}
