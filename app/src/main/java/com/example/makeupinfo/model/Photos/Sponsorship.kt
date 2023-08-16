package com.example.makeupinfo.model.Photos

import java.io.Serializable

data class Sponsorship(
    val impression_urls: List<String>,
    val sponsor: Sponsor,
    val tagline: String,
    val tagline_url: String
): Serializable