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

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.android.codelabs.paging.model.Result
import com.example.android.codelabs.testutil.EspressoIdlingResource

/**
 * Adapter for the list of repositories.
 */
class CharacterAdapter : ListAdapter<Result, ViewHolder>(REPO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return CharacterViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        EspressoIdlingResource.increment()
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as CharacterViewHolder).bind(repoItem)
        }
        EspressoIdlingResource.decrement()
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Result>() {
            override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean =
                    oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean =
                    oldItem == newItem
        }
    }
}
