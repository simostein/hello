package com.etice.lessons.data.models

data class Stage(
    val id: Int,
    val labelAr: String,
    val labelFr: String
) {
    companion object {
        fun getAll() = listOf(
            Stage(1, "المرحلة الأولى", "Cycle 1"),
            Stage(2, "المرحلة الثانية", "Cycle 2"),
            Stage(3, "المرحلة الثالثة", "Cycle 3"),
            Stage(4, "المرحلة الرابعة", "Cycle 4"),
            Stage(5, "المرحلة الخامسة", "Cycle 5"),
            Stage(6, "المرحلة السادسة", "Cycle 6")
        )
    }
    
    fun getLabel(language: Language) = when (language) {
        Language.ARABIC -> labelAr
        Language.FRENCH -> labelFr
    }
}
