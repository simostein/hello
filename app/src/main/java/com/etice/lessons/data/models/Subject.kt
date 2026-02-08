package com.etice.lessons.data.models

data class Subject(
    val code: String,
    val labelAr: String,
    val labelFr: String,
    val icon: String
) {
    companion object {
        fun getAll() = listOf(
            Subject("AR", "اللغة العربية", "Langue Arabe", "📖"),
            Subject("FR", "اللغة الفرنسية", "Langue Française", "🇫🇷"),
            Subject("MATH", "الرياضيات", "Mathématiques", "🔢")
        )
    }
    
    fun getLabel(language: Language) = when (language) {
        Language.ARABIC -> labelAr
        Language.FRENCH -> labelFr
    }
}
