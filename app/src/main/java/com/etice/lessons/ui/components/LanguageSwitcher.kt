package com.etice.lessons.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.etice.lessons.data.models.Language
import com.etice.lessons.ui.theme.WarmWhite
import com.etice.lessons.ui.theme.MajorelleBlue

@Composable
fun LanguageSwitcher(
    currentLanguage: Language,
    onLanguageChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(WarmWhite.copy(alpha = 0.2f))
            .padding(6.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        LanguageOption(
            flag = "🇲🇦",
            text = "AR",
            isSelected = currentLanguage == Language.ARABIC,
            onClick = { if (currentLanguage != Language.ARABIC) onLanguageChange() }
        )
        
        LanguageOption(
            flag = "🇫🇷",
            text = "FR",
            isSelected = currentLanguage == Language.FRENCH,
            onClick = { if (currentLanguage != Language.FRENCH) onLanguageChange() }
        )
    }
}

@Composable
private fun LanguageOption(
    flag: String,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected) WarmWhite
                else Color.Transparent
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Text(
            text = flag,
            fontSize = 18.sp
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            ),
            color = if (isSelected) MajorelleBlue else WarmWhite
        )
    }
}
