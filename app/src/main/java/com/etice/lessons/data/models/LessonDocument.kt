package com.etice.lessons.data.models

data class LessonDocument(
    val title: String,
    val url: String,
    val fileType: String = "PDF"
)
