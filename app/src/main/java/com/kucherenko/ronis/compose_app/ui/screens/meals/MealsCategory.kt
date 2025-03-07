package com.kucherenko.ronis.compose_app.ui.screens.meals

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.kucherenko.ronis.compose_app.data.models.network.MealResponse

@Composable
fun MealsCategory(meal: MealResponse, action: () -> Unit) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContentColor = Color.Gray,
            disabledContainerColor = Color.Gray
        ),
        onClick = { action.invoke() }
    ) {
        Row(modifier = Modifier.animateContentSize()) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(meal.imageUrl)
                        .size(Size.ORIGINAL)
                        .build()
                ),
                contentDescription = "meal image",
                modifier = Modifier
                    .size(100.dp)
                    .padding(4.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Inside
            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.8f)
                    .padding(16.dp)
            ) {
                Text(text = meal.name, style = MaterialTheme.typography.titleMedium)
                CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.onSurfaceVariant) {
                    Text(
                        text = meal.description,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = if (isExpanded) 10 else 4
                    )
                }
            }
            Icon(
                imageVector = if (isExpanded)
                    Icons.Filled.KeyboardArrowUp
                else
                    Icons.Filled.KeyboardArrowDown,
                contentDescription = "Expand row icon",
                modifier = Modifier
                    .padding(16.dp)
                    .align(
                        if (isExpanded)
                            Alignment.Bottom
                        else
                            Alignment.CenterVertically
                    )
                    .clickable { isExpanded = !isExpanded }
            )
        }
    }
}
