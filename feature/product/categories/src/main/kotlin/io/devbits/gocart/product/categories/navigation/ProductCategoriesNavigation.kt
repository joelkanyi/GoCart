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
package io.devbits.gocart.product.categories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import io.devbits.gocart.product.categories.ui.ProductCategoriesRoute

const val productCategoriesRoute = "product/categories"

fun NavController.navigateToProductCategories(navOptions: NavOptions? = null) {
    this.navigate(productCategoriesRoute, navOptions)
}

fun NavGraphBuilder.productCategoriesScreen(
    onBack: () -> Unit,
    onClickCategory: (String) -> Unit,
) {
    composable(route = productCategoriesRoute) {
        ProductCategoriesRoute(
            onBack = onBack,
            onClickCategory = onClickCategory,
        )
    }
}
