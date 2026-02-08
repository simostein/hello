package com.etice.lessons.ui.theme

import androidx.compose.ui.graphics.Color

// Primary Colors - Moroccan Blues
val MajorelleBlue = Color(0xFF0C4DA2)
val SkyBlue = Color(0xFF5B9BD5)
val OceanBlue = Color(0xFF2E5C8A)

// Secondary Colors - Warm Earth Tones
val SandBeige = Color(0xFFF5E6D3)
val WarmGold = Color(0xFFD4AF37)
val Terracotta = Color(0xFFC77B58)
val OliveGreen = Color(0xFF8B9556)
val ActionRed = Color(0xFFD32F2F) // Red 700

// Neutral Colors
val Cream = Color(0xFFFAF8F3)
val WarmWhite = Color(0xFFFFFFFF)
val Charcoal = Color(0xFF2C2C2C)
val WarmGray = Color(0xFF6B6B6B)

// Pattern Overlay
val ZellijPattern = MajorelleBlue.copy(alpha = 0.05f)
val ZellijAccent = WarmGold.copy(alpha = 0.08f)

// Legacy compatibility - map to new Moroccan colors
val Primary = MajorelleBlue
val PrimaryDark = OceanBlue
val PrimaryLight = SkyBlue
val AccentOrange = Terracotta
val AccentOrangeDark = Terracotta.copy(alpha = 0.8f)
val AccentGreen = OliveGreen
val AccentPurple = Color(0xFF6B5B95)
val Background = Cream
val Surface = WarmWhite
val Error = Color(0xFFD32F2F)
val TextPrimary = Charcoal
val TextSecondary = WarmGray
val Secondary = OliveGreen
val OnPrimary = WarmWhite
val OnSecondary = WarmWhite
val OnBackground = Charcoal
val OnSurface = Charcoal
val CardBackground = WarmWhite
val DividerColor = SandBeige
