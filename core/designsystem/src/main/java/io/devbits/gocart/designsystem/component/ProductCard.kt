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
package io.devbits.gocart.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.devbits.gocart.designsystem.model.Product
import io.devbits.gocart.designsystem.theme.GoCartTheme
import io.devbits.gocart.resources.R as resourcesR

@Composable
fun ProductCard(
    product: Product,
    onBookmark: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var quantity by remember { mutableIntStateOf(0) }
    var bookmarked by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp)),
        ) {
            Image(
                painter = painterResource(product.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(16.dp),
                    ),
            )

            val color = if (bookmarked) {
                MaterialTheme.colorScheme.secondary
            } else {
                MaterialTheme.colorScheme.surface
            }
            IconContainer(
                color = color,
                modifier = Modifier
                    .padding(top = 8.dp, end = 8.dp)
                    .size(32.dp)
                    .clip(CircleShape)
                    .align(Alignment.TopEnd)
                    .clickable {
                        bookmarked = !bookmarked
                        onBookmark()
                    },
            ) {
                val tint = if (bookmarked) {
                    MaterialTheme.colorScheme.onSecondary
                } else {
                    MaterialTheme.colorScheme.primary
                }

                Icon(
                    imageVector = Icons.Outlined.FavoriteBorder,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(16.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = product.name.uppercase(),
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.labelMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "KSH ${product.price}",
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.titleSmall,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            if (product.unit.isNotBlank()) {
                Text(
                    text = product.unit,
                    style = MaterialTheme.typography.bodySmall,
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            if (product.unitQuantity.isNotBlank()) {
                val unitQuantity =
                    if (product.unit.isBlank()) product.unitQuantity else "(${product.unitQuantity})"
                Text(
                    text = unitQuantity,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        if (quantity <= 0) {
            SecondaryButton(
                text = "Add to cart",
                onClick = {
                    quantity += 1
                    onAddToCart()
                },
                modifier = Modifier.fillMaxWidth(),
            )
        } else {
            QuantityControl(
                quantity = quantity,
                onAdd = { quantity++ },
                onRemove = { quantity-- },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
fun ProductDetails(
    products: List<Product>,
    onBookmark: () -> Unit,
    onAddToCart: () -> Unit,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp),
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(160.dp),
        contentPadding = contentPadding,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(products) { product ->
            ProductCard(
                product = product,
                onBookmark = onBookmark,
                onAddToCart = onAddToCart,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProductDetailsPreview() {
    GoCartTheme {
        ProductDetails(
            products = sampleProducts,
            onBookmark = {},
            onAddToCart = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProductCardEmptyPreview() {
    GoCartTheme {
        ProductCard(product = sampleProducts[0], onBookmark = {}, onAddToCart = {})
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun ProductCardPreview() {
    GoCartTheme {
        ProductCard(product = sampleProducts[1], onBookmark = {}, onAddToCart = {})
    }
}

val sampleProducts = listOf(
    Product(
        id = 1,
        image = resourcesR.drawable.ic_products_capsicum,
        name = "Capsicum",
        price = "70.00",
        unit = "500 grams",
        unitQuantity = "4 Pieces",
        bookmarked = false,
    ),
    Product(
        id = 2,
        image = resourcesR.drawable.ic_products_bananas,
        name = "Bananas",
        price = "110.00",
        unit = "1 Bunch",
        unitQuantity = "5 Pieces",
        bookmarked = false,
    ),
    Product(
        id = 3,
        image = resourcesR.drawable.ic_products_cucumber,
        name = "Cucumber",
        price = "103.00",
        unit = "1 Kg",
        unitQuantity = "4 Pieces",
        bookmarked = false,
    ),
    Product(
        id = 4,
        image = resourcesR.drawable.ic_products_carrots,
        name = "Carrots",
        price = "110.00",
        unit = "1 Bunch",
        unitQuantity = "5 Pieces",
        bookmarked = false,
    ),
    Product(
        id = 5,
        image = resourcesR.drawable.ic_products_sweet_potatoes,
        name = "Sweet Potatoes",
        price = "70.00",
        unit = "1 Kg",
        unitQuantity = "2 Pieces",
        bookmarked = true,
    ),
    Product(
        id = 6,
        image = resourcesR.drawable.ic_products_green_apple,
        name = "Fresh Green Apples",
        price = "120.00",
        unit = "1 Kg",
        unitQuantity = "4 Pieces",
        bookmarked = true,
    ),
    Product(
        id = 7,
        image = resourcesR.drawable.ic_products_oranges,
        name = "Fresh Oranges",
        price = "110.00",
        unit = "1 Bunch",
        unitQuantity = "5 Pieces",
        bookmarked = false,
    ),
    Product(
        id = 8,
        image = resourcesR.drawable.ic_products_golden_mangoe,
        name = "Golden Mangoes",
        price = "60.00",
        unit = "1 Kg",
        unitQuantity = "2 Pieces",
        bookmarked = false,
    ),
    Product(
        id = 9,
        image = resourcesR.drawable.ic_products_avocado,
        name = "Avocados",
        price = "50.00",
        unit = "1 Kg",
        unitQuantity = "2 Pieces",
        bookmarked = false,
    ),
    Product(
        id = 10,
        image = resourcesR.drawable.ic_products_red_apple,
        name = "Fresh Red Apples",
        price = "105.00",
        unit = "1.5 Kg",
        unitQuantity = "3 Pieces",
        bookmarked = false,
    ),
    Product(
        id = 11,
        image = resourcesR.drawable.ic_products_strawberry,
        name = "Strawberries",
        price = "225.00",
        unit = "1 Packet",
        unitQuantity = "",
        bookmarked = true,
    ),
    Product(
        id = 12,
        image = resourcesR.drawable.ic_product_stripped_watermelon,
        name = "Stripped Watermelon",
        price = "90.00",
        unit = "6.9 Kg",
        unitQuantity = "Pieces",
        bookmarked = false,
    ),
    Product(
        id = 13,
        image = resourcesR.drawable.ic_products_broccoli,
        name = "Broccoli",
        price = "109.00",
        unit = "1 Kg",
        unitQuantity = "1 large piece",
        bookmarked = false,
    ),
    Product(
        id = 14,
        image = resourcesR.drawable.ic_products_kale,
        name = "Green Kale",
        price = "50.00",
        unit = "",
        unitQuantity = "1 bunch",
        bookmarked = false,
    ),
    Product(
        id = 15,
        image = resourcesR.drawable.ic_products_red_onions,
        name = "Red Onions",
        price = "300.00",
        unit = "2 Kg",
        unitQuantity = "15 piece",
        bookmarked = false,
    ),
    Product(
        id = 16,
        image = resourcesR.drawable.ic_products_potatoes,
        name = "Potatoes",
        price = "30.00",
        unit = "2 Kg",
        unitQuantity = "15 piece",
        bookmarked = false,
    ),
    Product(
        id = 17,
        image = resourcesR.drawable.ic_grocery_bag,
        name = "Grocery Bag",
        price = "1550.00",
        unit = "1 Mega bundle",
        unitQuantity = "",
        bookmarked = false,
    ),
    Product(
        id = 18,
        image = resourcesR.drawable.ic_products_tomatoes,
        name = "Fresh Tomatoes",
        price = "100.00",
        unit = "2 Kg",
        unitQuantity = "14 Pieces",
        bookmarked = false,
    ),
    Product(
        id = 19,
        image = resourcesR.drawable.ic_products_papaya,
        name = "Pawpaw",
        price = "80.00",
        unit = "",
        unitQuantity = "2 Pieces",
        bookmarked = false,
    ),
    Product(
        id = 20,
        image = resourcesR.drawable.ic_products_red_berries,
        name = "Red Berries",
        price = "240.00",
        unit = "",
        unitQuantity = "1 Packed Bundle",
        bookmarked = false,
    ),
)