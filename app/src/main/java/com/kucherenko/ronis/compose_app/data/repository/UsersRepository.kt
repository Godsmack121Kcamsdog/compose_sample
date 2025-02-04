package com.kucherenko.ronis.compose_app.data.repository

import com.kucherenko.ronis.compose_app.data.models.UserProfile
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UsersRepository(val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    suspend fun loadUsers(): List<UserProfile> {
        val users = mutableListOf<UserProfile>()
        withContext(dispatcher) {
            users.add(
                UserProfile(
                    id = 0,
                    name = "Oleksii Kucherenko",
                    isActive = true,
                    imageUrl = "https://modof.club/uploads/posts/2023-09/thumbs/1694750640_modof-club-p-obraz-muzhchini-v-stile-avatar-19.jpg"
                )
            )
            users.add(
                UserProfile(
                    id = 1,
                    name = "Andrii Piskov",
                    isActive = true,
                    imageUrl = null
                )
            )
            users.add(
                UserProfile(
                    id = 2,
                    name = "Valerii Gustavo",
                    isActive = false,
                    imageUrl = "https://static.wikia.nocookie.net/netflix/images/f/f3/Fake_Profile_-_Pedro_in_a_suit.png/revision/latest?cb=20230618034413"
                )
            )
            users.add(
                UserProfile(
                    id = 3,
                    name = "Pedro Paskal",
                    isActive = true,
                    imageUrl = "https://s5.afisha.ru/mediastorage/ca/47/ee3b15ac0ef246fc8937805847ca.jpg"
                )
            )
            for (i in 0..10) {
                users.add(
                    UserProfile(
                        id = 10 + i,
                        name = "Test_$i",
                        isActive = false,
                        imageUrl = null
                    )
                )
            }
        }
        return users
    }
}