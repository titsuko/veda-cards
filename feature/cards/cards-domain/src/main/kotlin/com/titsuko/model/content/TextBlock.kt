package com.titsuko.model.content

import com.titsuko.model.ContentBlock

data class TextBlock(
    val text: String,
    val isBold: Boolean = false,
    val isItalic: Boolean = false,
    val color: String? = null
) : ContentBlock
