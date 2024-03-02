package com.arodmar432p.blackjackspecial.cardGames.repository

import android.util.Log
import com.arodmar432p.blackjackspecial.cardGames.service.AvatarApi

/**
 * A repository class for handling avatar data.
 *
 * @property avatarApi The [AvatarApi] instance used to retrieve avatar data.
 */
class AvatarRepository(private val avatarApi: AvatarApi) {

    /**
     * Retrieves avatar data for a given name.
     *
     * @param name The name of the avatar.
     * @return A [ByteArray] containing the avatar data.
     * @throws Exception If there is an error retrieving the avatar data.
     */
    suspend fun getAvatarData(name: String): ByteArray {
        val response = avatarApi.getAvatarData(name)
        if (response.isSuccessful) {
            val imageData = response.body()?.bytes() ?: ByteArray(0)
            Log.d("AvatarRepository", "Image data: ${imageData.contentToString()}")
            return imageData
        } else {
            throw Exception("Error al obtener los datos del avatar")
        }
    }
}
