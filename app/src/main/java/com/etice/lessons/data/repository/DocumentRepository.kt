package com.etice.lessons.data.repository

import com.etice.lessons.data.api.RetrofitClient
import com.etice.lessons.data.models.LessonDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

sealed class DocumentResult {
    data class Success(val documents: List<LessonDocument>) : DocumentResult()
    data class Error(val message: String) : DocumentResult()
    object NotFound : DocumentResult()
}

class DocumentRepository {
    private val apiService = RetrofitClient.apiService
    
    suspend fun fetchDocument(
        level: Int,
        phase: Int,
        week: String,
        subject: String
    ): DocumentResult = withContext(Dispatchers.IO) {
        try {
            val response = apiService.fetchDocument(level, phase, week, subject)
            
            if (response.isSuccessful) {
                val body = response.body()?.string()
                
                if (body.isNullOrEmpty()) {
                    return@withContext DocumentResult.NotFound
                }
                
                // Try to parse as JSON first
                try {
                    // The new API returns an array of documents
                    if (body.trim().startsWith("[")) {
                        val jsonArray = org.json.JSONArray(body)
                        val documents = mutableListOf<LessonDocument>()
                        
                        for (i in 0 until jsonArray.length()) {
                            val doc = jsonArray.getJSONObject(i)
                            val title = doc.optString("title", "Session ${i + 1}")
                            val url = doc.optString("link", "")
                            
                            if (url.isNotEmpty()) {
                                documents.add(LessonDocument(title, url))
                            }
                        }
                        
                        if (documents.isNotEmpty()) {
                            DocumentResult.Success(documents)
                        } else {
                            DocumentResult.NotFound
                        }
                    } else {
                        // Try parsing as single object (old API format)
                        val json = JSONObject(body)
                        val title = json.optString("title", "Document")
                        val url = json.optString("url", "")
                        
                        if (url.isNotEmpty()) {
                            DocumentResult.Success(listOf(LessonDocument(title, url)))
                        } else {
                            DocumentResult.NotFound
                        }
                    }
                } catch (e: Exception) {
                    // If not JSON, check if it's an HTML error or a direct URL
                    if (body.contains("http") && body.contains(".pdf")) {
                        // Extract PDF URL from HTML
                        val urlPattern = """https?://[^\s<>"]+\.pdf""".toRegex()
                        val match = urlPattern.find(body)
                        
                        if (match != null) {
                            DocumentResult.Success(
                                listOf(LessonDocument(
                                    title = "Document",
                                    url = match.value
                                ))
                            )
                        } else {
                            DocumentResult.NotFound
                        }
                    } else {
                        DocumentResult.NotFound
                    }
                }
            } else {
                DocumentResult.Error("خطأ في الاتصال: ${response.code()}")
            }
        } catch (e: Exception) {
            DocumentResult.Error("خطأ في الاتصال: ${e.message ?: "Unknown error"}")
        }
    }
}
