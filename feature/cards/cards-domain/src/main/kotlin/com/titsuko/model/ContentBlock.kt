package com.titsuko.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.titsuko.model.content.HeaderBlock
import com.titsuko.model.content.ImageBlock
import com.titsuko.model.content.TextBlock

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = HeaderBlock::class, name = "header"),
    JsonSubTypes.Type(value = TextBlock::class, name = "text"),
    JsonSubTypes.Type(value = ImageBlock::class, name = "image"),
)
interface ContentBlock
