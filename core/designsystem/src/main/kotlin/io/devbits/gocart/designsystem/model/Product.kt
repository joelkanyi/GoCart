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
package io.devbits.gocart.designsystem.model

import androidx.annotation.DrawableRes
import java.math.BigDecimal

data class Product(
    val id: Int,
    @DrawableRes
    val image: Int,
    val name: String,
    val price: String,
    val unit: String,
    val unitQuantity: String,
    val bookmarked: Boolean,
    val description: String,
    val category: ProductCategory,
    val offer: Int = 0,
    val finalPrice: BigDecimal = BigDecimal(price).multiply(offer.toBigDecimal()),
)
