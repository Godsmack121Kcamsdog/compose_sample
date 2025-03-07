package com.kucherenko.ronis.compose_app.ui.screens.meals.details

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.kucherenko.ronis.compose_app.data.models.network.MealResponse
import com.kucherenko.ronis.myapplication.R
import kotlin.math.min

@Composable
fun MealDetailsScreen(meal: MealResponse?) {
    val scrollState = rememberLazyListState()
    val offset = min(
        1f,
        1 - (scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex)
    )

    //updating mealPictureState updates all transition values
    val mealPictureState by remember { mutableStateOf(MealPictureState.Normal) }

    val transition = updateTransition(targetState = mealPictureState)
    val color by transition.animateColor(targetValueByState = { it.color })
    val widthSize by transition.animateDp(targetValueByState = { it.borderWidth })
    val txtSize by transition.animateDp(targetValueByState = { it.textSize })

    val size by animateDpAsState(targetValue = max(100.dp, 200.dp * offset))
    Surface(color = MaterialTheme.colorScheme.background) {
        Column {
            Surface(shadowElevation = 4.dp) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.padding(16.dp),
                        shape = CircleShape,
                        border = BorderStroke(
                            width = widthSize,
                            color = color
                        )
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(meal?.imageUrl)
                                    .size(Size.ORIGINAL).transformations(CircleCropTransformation())
                                    .placeholder(R.drawable.default_avatar) // Placeholder while loading
                                    .error(R.drawable.default_avatar) // Error image if loading fails
                                    .fallback(R.drawable.default_avatar) // Shown if URL is null
                                    .build()
                            ),
                            contentDescription = "meal image",
                            modifier = Modifier.size(size)
                        )
                    }
                    Text(
                        meal?.name ?: "unknown",
                        style = TextStyle(color = Color.Black),
                        modifier = Modifier
                            .padding(16.dp)
                            .size(txtSize)
                            .align(Alignment.CenterVertically)
                    )
                }
            }
            val mockedList = (0..100).map { it.toString() }
            LazyColumn(state = scrollState) {
                items(mockedList) { item ->
                    Text(
                        text = item, modifier = Modifier
                            .padding(24.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}

enum class MealPictureState(val color: Color, val textSize: Dp, val borderWidth: Dp) {
    Normal(Color.Black, 16.dp, 1.dp),
    Expanded(Color.Yellow, 20.dp, 2.dp)
}