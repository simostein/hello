package com.etice.lessons.utils

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast

object DownloadHelper {
    /**
     * Converts a Google Drive sharing link to a direct download link for DownloadManager
     * Example: https://drive.google.com/file/d/FILE_ID/view?usp=sharing
     * Becomes: https://drive.google.com/uc?export=download&id=FILE_ID&confirm=t
     */
    fun convertGoogleDriveUrl(url: String): String {
        return if (url.contains("drive.google.com/file/d/")) {
            // Extract file ID from the URL
            val fileIdRegex = """/file/d/([^/]+)""".toRegex()
            val match = fileIdRegex.find(url)
            if (match != null) {
                val fileId = match.groupValues[1]
                // Use direct download URL with confirm parameter to bypass virus scan warning
                "https://drive.google.com/uc?export=download&id=$fileId&confirm=t"
            } else {
                url
            }
        } else {
            url
        }
    }
    
    fun downloadDocument(
        context: Context,
        url: String,
        title: String
    ) {
        try {
            // Convert Google Drive sharing links to direct download links
            val downloadUrl = convertGoogleDriveUrl(url)
            
            val sanitizedTitle = title.replace(Regex("[^a-zA-Z0-9._ -]"), "_")
            val fileName = "$sanitizedTitle.pdf"
            
            val request = DownloadManager.Request(Uri.parse(downloadUrl))
                .setTitle(title)
                .setDescription("Downloading lesson document")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)
            
            val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
            
            Toast.makeText(context, "Download started", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Log.e("DownloadHelper", "Download failed: ${e.message}")
            // Fallback to opening in browser
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            } catch (ex: Exception) {
                Toast.makeText(context, "Could not open document", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
