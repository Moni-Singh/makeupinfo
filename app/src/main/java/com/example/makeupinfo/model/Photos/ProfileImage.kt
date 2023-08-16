package com.example.makeupinfo.model.Photos

import java.io.Serializable

data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
): Serializable