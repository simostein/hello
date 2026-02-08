package com.etice.lessons.data.models

data class Week(
    val id: String,
    val labelAr: String,
    val labelFr: String
) {
    companion object {
        fun getAll() = listOf(
            Week("SEM1", "الأسبوع الأول", "Semaine 1"),
            Week("SEM2", "الأسبوع الثاني", "Semaine 2"),
            Week("SEM3", "الأسبوع الثالث", "Semaine 3"),
            Week("SEM4", "الأسبوع الرابع", "Semaine 4"),
            Week("SEM5", "الأسبوع الخامس", "Semaine 5"),
            Week("SEM6", "الأسبوع السادس", "Semaine 6")
        )
    }
    
    fun getLabel(language: Language) = when (language) {
        Language.ARABIC -> labelAr
        Language.FRENCH -> labelFr
    }
}
