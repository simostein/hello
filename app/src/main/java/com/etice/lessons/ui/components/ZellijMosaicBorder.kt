package com.etice.lessons.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Moroccan Zellij mosaic border with geometric star/flower pattern
 * Fades from dark blue → light blue → white
 */
@Composable
fun ZellijMosaicBorder(
    modifier: Modifier = Modifier,
    isTop: Boolean = true
) {
    val deepBlue = Color(0xFF1E3A8A)
    val skyBlue = Color(0xFF3B82F6)
    val lightBlue = Color(0xFF93C5FD)
    val veryLightBlue = Color(0xFFDEEBFF)
    
    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        val tileSize = 40f
        val cols = (size.width / tileSize).toInt() + 2
        val rows = 3
        
        for (row in 0..rows) {
            for (col in 0..cols) {
                val x = col * tileSize
                val y = if (isTop) row * tileSize else size.height - (row + 1) * tileSize
                
                // Calculate color based on row (gradient effect)
                val color = when (row) {
                    0 -> deepBlue
                    1 -> skyBlue
                    2 -> lightBlue
                    else -> veryLightBlue
                }
                
                // Draw Moroccan star tile
                drawMoroccanStar(
                    center = Offset(x, y),
                    radius = tileSize / 2.5f,
                    color = color.copy(alpha = 0.7f - (row * 0.15f))
                )
            }
        }
    }
}

private fun DrawScope.drawMoroccanStar(
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
    
    drawPath(path = path, color = color)
    
    // Draw small center circle
    drawCircle(
        color = color.copy(alpha = 0.5f),
        radius = radius * 0.2f,
        center = center
    )
}
