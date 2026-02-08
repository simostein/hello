package com.etice.lessons.utils

import com.etice.lessons.data.models.Language

object Strings {
    fun appTitle(lang: Language) = when (lang) {
        Language.ARABIC -> "تحميل دروس التعليم الصريح"
        Language.FRENCH -> "Télécharger les leçons explicites"
    }
    
    fun appSubtitle(lang: Language) = when (lang) {
        Language.ARABIC -> "منصة الموارد الرقمية للتعليم الصريح"
        Language.FRENCH -> "Plateforme de ressources numériques pour l'enseignement explicite"
    }
    
    fun selectLevel(lang: Language) = when (lang) {
        Language.ARABIC -> "اختر المستوى"
        Language.FRENCH -> "Choisir le niveau"
    }
    
    fun selectStage(lang: Language) = when (lang) {
        Language.ARABIC -> "اختر المرحلة"
        Language.FRENCH -> "Choisir le cycle"
    }
    
    fun selectWeek(lang: Language) = when (lang) {
        Language.ARABIC -> "اختر الأسبوع"
        Language.FRENCH -> "Choisir la semaine"
    }
    
    fun selectSubject(lang: Language) = when (lang) {
        Language.ARABIC -> "اختر المادة"
        Language.FRENCH -> "Choisir la matière"
    }
    
    fun fetchLessons(lang: Language) = when (lang) {
        Language.ARABIC -> "تحميل الحصص"
        Language.FRENCH -> "Télécharger les leçons"
    }
    
    fun selectSession(lang: Language) = when (lang) {
        Language.ARABIC -> "اختر الحصة"
        Language.FRENCH -> "Choisir la session"
    }
    
    fun download(lang: Language) = when (lang) {
        Language.ARABIC -> "تحميل"
        Language.FRENCH -> "Télécharger"
    }
    
    fun view(lang: Language) = when (lang) {
        Language.ARABIC -> "عرض"
        Language.FRENCH -> "Voir"
    }
    
    fun selectAllFields(lang: Language) = when (lang) {
        Language.ARABIC -> "الرجاء اختيار جميع الحقول"
        Language.FRENCH -> "Veuillez sélectionner tous les champs"
    }
    
    fun documentNotFound(lang: Language) = when (lang) {
        Language.ARABIC -> "لم يتم العثور على المستند"
        Language.FRENCH -> "Document non trouvé"
    }
    
    fun connectionError(lang: Language) = when (lang) {
        Language.ARABIC -> "خطأ في الاتصال"
        Language.FRENCH -> "Erreur de connexion"
    }
    
    fun loading(lang: Language) = when (lang) {
        Language.ARABIC -> "جاري التحميل..."
        Language.FRENCH -> "Chargement..."
    }
    
    fun documentReady(lang: Language) = when (lang) {
        Language.ARABIC -> "المستند جاهز"
        Language.FRENCH -> "Document prêt"
    }
}
