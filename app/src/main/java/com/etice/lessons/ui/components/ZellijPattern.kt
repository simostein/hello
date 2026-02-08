package com.etice.lessons.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import com.etice.lessons.ui.theme.WarmGold
import com.etice.lessons.ui.theme.MajorelleBlue
import kotlin.math.cos
import kotlin.math.sin

/**
 * Subtle Zellij (Moroccan geometric tile) pattern overlay
 * Used as decorative background element with very low opacity
 */
@Composable
fun ZellijPatternOverlay(
    modifier: Modifier = Modifier,
    color: Color = MajorelleBlue.copy(alpha = 0.05f)
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val patternSize = 80f
        val cols = (size.width / patternSize).toInt() + 1
        val rows = (size.height / patternSize).toInt() + 1
        
        for (row in 0..rows) {
            for (col in 0..cols) {
                val x = col * patternSize
                val y = row * patternSize
                
                // Draw subtle geometric star pattern
                drawZellijStar(
                    center = Offset(x, y),
                    radius = patternSize / 3,
                    color = color
                )
            }
        }
    }
}

/**
 * Zellij corner decoration for cards
 */
@Composable
fun ZellijCornerDecoration(
    modifier: Modifier = Modifier,
    color: Color = WarmGold.copy(alpha = 0.15f),
    size: Float = 40f
) {
    Canvas(modifier = modifier) {
        drawZellijCorner(
            topLeft = Offset(0f, 0f),
            size = size,
            color = color
        )
    }
}

// Helper function to draw 8-pointed star (common in Moroccan art)
private fun DrawScope.drawZellijStar(
    center: Offset,
    radius: Float,
    color: Color
) {
    val points = 8
    val path = Path()
    
    for (i in 0 until points * 2) {
        val angle = (i * Math.PI / points).toFloat()
        val r = if (i % 2 == 0) radius else radius * 0.5f
        val x = center.x + r * cos(angle)
        val y = center.y + r * sin(angle)
        
        if (i == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    
    drawPath(
        path = path,
        color = color,
        style = Stroke(width = 1f)
    )
}

// Helper function to draw corner decoration
private fun DrawScope.drawZellijCorner(
    topLeft: Offset,
    size: Float,
    color: Color
) {
    // Draw simple geometric corner motif
    val path = Path().apply {
        moveTo(topLeft.x, topLeft.y + size)
        lineTo(topLeft.x, topLeft.y)
        lineTo(topLeft.x + size, topLeft.y)
        
        // Add small decorative notches
        moveTo(topLeft.x + size * 0.3f, topLeft.y)
        lineTo(topLeft.x + size * 0.3f, topLeft.y + size * 0.15f)
        lineTo(topLeft.x, topLeft.y + size * 0.3f)
    }
    
    drawPath(
        path = path,
        color = color,
        style = Stroke(width = 2f)
    )
}
