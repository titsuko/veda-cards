package com.titsuko.model.content

import com.titsuko.model.ContentBlock

data class HeaderBlock(
    val text: String,
    val level: Int = 0
) : ContentBlock