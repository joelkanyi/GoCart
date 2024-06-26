/*
 * Copyright 2023 Eton Otieno
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
package io.devbits.gocart.authentication.ui.password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.devbits.gocart.core.datastore.UserPreferences
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class UpdatePasswordViewModel @Inject constructor(
    private val preferences: UserPreferences,
) : ViewModel() {

    fun setAuthenticated() {
        viewModelScope.launch {
            preferences.setAuthenticated(true)
        }
    }
}
