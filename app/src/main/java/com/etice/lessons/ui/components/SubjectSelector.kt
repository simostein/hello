package com.etice.lessons.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etice.lessons.R
import com.etice.lessons.data.models.Language
import com.etice.lessons.data.models.Subject

@Composable
fun SubjectSelector(
    selectedSubject: Subject?,
    onSubjectSelected: (Subject) -> Unit,
    language: Language,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .alpha(if (enabled) 1f else 0.5f),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Math (Order in screenshot: Math, French, Arabic from left to right?)
        // Screenshot shows: Math, French, Arabic (Left to Right) if RTL?
        // Let's look at the screenshot again.
        // It says "رياضيات" (Math) on left, "فرنسية" (French) middle, "عربية" (Arabic) right.
        // Since it's Arabic UI, it's RTL. So "Arabic" is start (right), "Math" is end (left).
        // My code does: AR, FR, MATH. In LTR that's AR(left), FR, MATH(right).
        // In RTL that's AR(right), FR, MATH(left).
        // So the order in code (AR, FR, MATH) is correct for RTL (AR first/right).
        // Wait, the screenshot shows Math on the LEFT.
        // In Arabic (RTL), "Start" is Right.
        // If the screenshot shows Math on the Left, then Math is at the END.
        // Arabic on the Right (Start).
        // So: Arabic (Right/Start), French (Middle), Math (Left/End).
        // My code currently does:
        // AR (found code="AR")
        // FR
        // MATH
        // In a Row, items are placed Start to End.
        // In LTR: AR (Left), FR, MATH (Right).
        // In RTL: AR (Right), FR, MATH (Left).
        // This MATCHES the screenshot (Arabic Right, Math Left).
        
        // Arabic
        Subject.getAll().find { it.code == "AR" }?.let { subject ->
            SubjectItem(
                subject = subject,
                isSelected = selectedSubject == subject,
                iconRes = R.drawable.icon_subject_arabic,
                language = language,
                onClick = { if (enabled) onSubjectSelected(subject) }
            )
        }

        // Divider
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(80.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
        )

        // French
        Subject.getAll().find { it.code == "FR" }?.let { subject ->
            SubjectItem(
                subject = subject,
                isSelected = selectedSubject == subject,
                iconRes = R.drawable.icon_subject_french,
                language = language,
                onClick = { if (enabled) onSubjectSelected(subject) }
            )
        }

        // Divider
        Box(
            modifier = Modifier
                .width(2.dp)
                .height(80.dp)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
        )

        // Math
        Subject.getAll().find { it.code == "MATH" }?.let { subject ->
            SubjectItem(
                subject = subject,
                isSelected = selectedSubject == subject,
                iconRes = R.drawable.icon_subject_math,
                language = language,
                onClick = { if (enabled) onSubjectSelected(subject) }
            )
        }
    }
}

@Composable
private fun SubjectItem(
    subject: Subject,
    isSelected: Boolean,
    iconRes: Int,
    language: Language,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(if (isSelected) 80.dp else 70.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = if (isSelected) 3.dp else 0.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = subject.getLabel(language),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = subject.getLabel(language),
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = if (isSelected) 16.sp else 14.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            ),
            color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )
    }
}
