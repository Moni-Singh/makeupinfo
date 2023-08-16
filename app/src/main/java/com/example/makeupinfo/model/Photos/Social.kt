package com.example.makeupinfo.model.Photos

import java.io.Serializable

data class Social(
    val instagram_username: String,
    val paypal_email: Any,
    val portfolio_url: String,
    val twitter_username: String
): Serializable