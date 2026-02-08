package com.etice.lessons.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.etice.lessons.data.models.*
import com.etice.lessons.data.repository.DocumentRepository
import com.etice.lessons.data.repository.DocumentResult
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val documents: List<LessonDocument>) : UiState()
    data class Error(val message: String) : UiState()
}

class MainViewModel : ViewModel() {
    private val repository = DocumentRepository()
    
    var language by mutableStateOf(Language.ARABIC)
        private set
    
    var selectedLevel by mutableStateOf<Level?>(null)
        private set
    
    var selectedStage by mutableStateOf<Stage?>(null)
        private set
    
    var selectedWeek by mutableStateOf<Week?>(null)
        private set
    
    var selectedSubject by mutableStateOf<Subject?>(null)
        private set
    
    var availableSessions by mutableStateOf<List<LessonDocument>>(emptyList())
        private set
    
    var selectedSession by mutableStateOf<LessonDocument?>(null)
        private set
    
    var uiState by mutableStateOf<UiState>(UiState.Idle)
        private set
    
    val levels = Level.getAll()
    val stages = Stage.getAll()
    val weeks = Week.getAll()
    val subjects = Subject.getAll()
    
    fun toggleLanguage() {
        language = if (language == Language.ARABIC) Language.FRENCH else Language.ARABIC
    }
    
    fun selectLevel(level: Level) {
        Log.d("MainViewModel", "selectLevel: ${level.id}, selectedLevel will be: $level")
        selectedLevel = level
        // Reset ALL downstream selections
        selectedStage = null
        selectedWeek = null
        selectedSubject = null
        uiState = UiState.Idle
        Log.d("MainViewModel", "After selectLevel: selectedLevel=${selectedLevel?.id}")
    }
    
    fun selectStage(stage: Stage) {
        Log.d("MainViewModel", "selectStage: ${stage.id}, selectedStage will be: $stage")
        selectedStage = stage
        // Reset downstream selections (Week, Subject)
        selectedWeek = null
        selectedSubject = null
        uiState = UiState.Idle
        Log.d("MainViewModel", "After selectStage: selectedStage=${selectedStage?.id}")
    }
    
    fun selectWeek(week: Week) {
        selectedWeek = week
        // Reset downstream selections (Subject)
        selectedSubject = null
        uiState = UiState.Idle
    }
    
    fun selectSubject(subject: Subject) {
        selectedSubject = subject
        // Subject is now the LAST step, so do NOT reset anything else!
        uiState = UiState.Idle
    }
    
    // Called when user clicks the "Fetch Lessons" button
    fun fetchDocuments() {
        Log.d("MainViewModel", "fetchDocuments() called!")
        Log.d("MainViewModel", "Selected values: level=${selectedLevel?.id}, subject=${selectedSubject?.code}, stage=${selectedStage?.id}, week=${selectedWeek?.id}")
        fetchDocument()
    }
    
    private fun fetchDocument() {
        Log.d("MainViewModel", "fetchDocument() called")
        val level = selectedLevel ?: run {
            Log.d("MainViewModel", "fetchDocument: level is null, returning")
            return
        }
        val stage = selectedStage ?: run {
            Log.d("MainViewModel", "fetchDocument: stage is null, returning")
            return
        }
        val week = selectedWeek ?: run {
            Log.d("MainViewModel", "fetchDocument: week is null, returning")
            return
        }
        val subject = selectedSubject ?: run {
            Log.d("MainViewModel", "fetchDocument: subject is null, returning")
            return
        }
        
        Log.d("MainViewModel", "All values present, launching coroutine...")
        
        viewModelScope.launch {
            uiState = UiState.Loading
            
            val result = repository.fetchDocument(
                level = level.id,
                phase = stage.id,
                week = week.id,
                subject = subject.code
            )
            
            uiState = when (result) {
                is DocumentResult.Success -> {
                    availableSessions = result.documents
                    selectedSession = result.documents.firstOrNull()
                    UiState.Success(result.documents)
                }
                is DocumentResult.Error -> UiState.Error(result.message)
                is DocumentResult.NotFound -> UiState.Error(
                    if (language == Language.ARABIC) 
                        "لم يتم العثور على المستند" 
                    else 
                        "Document non trouvé"
                )
            }
        }
    }
    
    fun selectSession(session: LessonDocument) {
        selectedSession = session
    }
    
    fun canFetchDocument(): Boolean {
        return selectedLevel != null && 
               selectedStage != null && 
               selectedWeek != null
    }
}
