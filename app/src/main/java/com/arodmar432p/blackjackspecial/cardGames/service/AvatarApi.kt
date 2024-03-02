package com.arodmar432p.blackjackspecial.cardGames.service

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * An interface for the Avatar API.
 */
interface AvatarApi {
    @GET("{name}.png")
    suspend fun getAvatarData(@Path("name") name: String): Response<ResponseBody>
}