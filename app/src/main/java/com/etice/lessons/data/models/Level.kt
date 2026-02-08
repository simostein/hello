package com.etice.lessons.data.models

data class Level(
    val id: Int,
    val labelAr: String,
    val labelFr: String
) {
    companion object {
        fun getAll() = listOf(
            Level(1, "المستوى الأول", "Niveau 1"),
            Level(2, "المستوى الثاني", "Niveau 2"),
            Level(3, "المستوى الثالث", "Niveau 3"),
            Level(4, "المستوى الرابع", "Niveau 4"),
            Level(5, "المستوى الخامس", "Niveau 5"),
            Level(6, "المستوى السادس", "Niveau 6")
        )
    }
    
    fun getLabel(language: Language) = when (language) {
        Language.ARABIC -> labelAr
        Language.FRENCH -> labelFr
    }
}
