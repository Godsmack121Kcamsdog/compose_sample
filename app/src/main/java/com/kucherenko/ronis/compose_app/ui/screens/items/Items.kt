package com.kucherenko.ronis.compose_app.ui.screens.items

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import coil.transform.Transformation
import com.kucherenko.ronis.compose_app.data.models.UserProfile
import com.kucherenko.ronis.compose_app.data.models.network.MealResponse
import com.kucherenko.ronis.compose_app.ui.theme.UserActiveGreen
import com.kucherenko.ronis.compose_app.ui.theme.UserOfflineGray
import com.kucherenko.ronis.compose_app.vm.LoginViewModel
import com.kucherenko.ronis.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    icon: ImageVector = Icons.Default.Home,
    title: String,
    action: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(title) },
        navigationIcon = {
            Icon(
                imageVector = icon,
                contentDescription = "Icon",
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .clickable { action.invoke() },
            )
        }
    )
}

@Composable
fun LoginPasswordFields(vm: LoginViewModel) {
    var showPassword by rememberSaveable { mutableStateOf(false) }
    val email = vm.emailValue
    TextField(
        value = email,
        onValueChange = vm::setEmail,
        label = {
            Text(text = "E-Mail")
        },
        placeholder = { Text("email@gmail.com") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Email,
                contentDescription = "Leading Icon",
                tint = Color.Blue,
                modifier = Modifier.size(20.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Close,
                contentDescription = "Trailing Icon",
                tint = Color.Blue,
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        vm.setEmail("")
                    }
            )
        }
    )
    Spacer(modifier = Modifier.height(8.dp))
    TextField(
        value = vm.passwordValue,
        onValueChange = vm::setPassword,
        label = {
            Text(text = "Password")
        },
        placeholder = { Text("password") },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Lock,
                contentDescription = "Leading Icon",
                tint = Color.Blue,
                modifier = Modifier.size(20.dp)
            )
        },
        trailingIcon = {
            Icon(
                imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                contentDescription = if (showPassword) "Show Password" else "Hide Password",
                tint = Color.Gray,
                modifier = Modifier
                    .size(24.dp)
                    .clickable { showPassword = !showPassword }
            )
        },
        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
    )
}

@Composable
fun ProfileCard(userProfile: UserProfile, action: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(align = Alignment.Top)
            .padding(start = 16.dp, end = 16.dp, top = 5.dp, bottom = 5.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = { action.invoke() }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            ProfilePicture(userProfile, 72.dp)
            ProfileContent(userProfile)
        }
    }
}

@Composable
fun ProfilePicture(userProfile: UserProfile, size: Dp) {
    Card(
        shape = CircleShape,
        border = BorderStroke(
            width = 2.dp,
            color = if (userProfile.isActive) UserActiveGreen else UserOfflineGray
        ),
        modifier = Modifier.padding(16.dp),
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(userProfile.imageUrl)
                    .size(Size.ORIGINAL)
                    .placeholder(R.drawable.default_avatar) // Placeholder while loading
                    .error(R.drawable.default_avatar) // Error image if loading fails
                    .fallback(R.drawable.default_avatar) // Shown if URL is null
                    .build()
            ),
            contentDescription = "user image",
            modifier = Modifier.size(size),
            contentScale = ContentScale.Crop
        )
    }
}

val LocalAlpha = compositionLocalOf { 1f }

@Composable
fun ProfileContent(userProfile: UserProfile, alignment: Alignment.Horizontal = Alignment.Start) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = userProfile.name,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.align(alignment)
        )
        CompositionLocalProvider(LocalAlpha provides 0.5f) {
            val alpha = LocalAlpha.current
            Text(
                text = if (userProfile.isActive) "Active now" else "Offline",
                modifier = Modifier
                    .graphicsLayer(alpha = alpha)
                    .align(alignment),
                style = MaterialTheme.typography.labelSmall,
                color = if (userProfile.isActive) Color.Gray else Color.Red,
                maxLines = 1
            )
        }
    }
}

