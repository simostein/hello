package com.etice.lessons.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudDownload
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.etice.lessons.R
import com.etice.lessons.ui.components.*
import com.etice.lessons.ui.viewmodel.MainViewModel
import com.etice.lessons.ui.viewmodel.UiState
import com.etice.lessons.utils.DownloadHelper
import com.etice.lessons.utils.Strings
import com.etice.lessons.ui.theme.*

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    showInterstitialAd: (() -> Unit) -> Unit
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    
    // Dialog states
    var showLevelDialog by remember { mutableStateOf(false) }
    var showSubjectDialog by remember { mutableStateOf(false) }
    var showStageDialog by remember { mutableStateOf(false) }
    var showWeekDialog by remember { mutableStateOf(false) }
    var showSessionDialog by remember { mutableStateOf(false) }
    
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // White Background
        Box(modifier = Modifier.fillMaxSize().background(Color.White))

        // Top Zellij Pattern
        Image(
            painter = painterResource(R.drawable.bg_zellij_top),
            contentDescription = "Moroccan header",
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp) // Minimal header height
                .align(Alignment.TopCenter),
            contentScale = ContentScale.Crop
        )


        
        // Scrollable Content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(120.dp)) // Increased spacer to prevent header overlap
            
            // Arabic Title
            Text(
                text = "دروس المدرسة الرائدة",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                color = MajorelleBlue, // Or a darker shade if needed, Majorelle is good
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // French Subtitle
            Text(
                text = "Les cours de l'École pionnière",
                fontSize = 24.sp,
                fontFamily = androidx.compose.ui.text.font.FontFamily.Cursive,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Level Button (المرحلة)
            Spacer(modifier = Modifier.height(24.dp))
            
            // Level Button (المرحلة)
            BrushStrokeButton(
                text = if (viewModel.selectedLevel != null) 
                    "__ ${viewModel.selectedLevel?.getLabel(viewModel.language)} __"
                else 
                    "__ ${Strings.selectLevel(viewModel.language)} __",
                onClick = { showLevelDialog = true },
                brushResId = R.drawable.btn_brush_thick_blue
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Stage Button (المستوى)
            BrushStrokeButton(
                text = if (viewModel.selectedStage != null)
                    "__ ${viewModel.selectedStage?.getLabel(viewModel.language)} __"
                else
                    "__ ${Strings.selectStage(viewModel.language)} __",
                onClick = { showStageDialog = true },
                brushResId = R.drawable.btn_brush_thick_blue,
                enabled = viewModel.selectedLevel != null
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Week Button (الأسبوع)
            BrushStrokeButton(
                text = if (viewModel.selectedWeek != null)
                    "__ ${viewModel.selectedWeek?.getLabel(viewModel.language)} __"
                else
                    "__ ${Strings.selectWeek(viewModel.language)} __",
                onClick = { showWeekDialog = true },
                brushResId = R.drawable.btn_brush_thick_blue,
                enabled = viewModel.selectedStage != null
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Subject Selector (المادة)
            SubjectSelector(
                selectedSubject = viewModel.selectedSubject,
                onSubjectSelected = {
                    viewModel.selectSubject(it)
                    showSubjectDialog = false // Just in case
                },
                language = viewModel.language,
                enabled = viewModel.selectedWeek != null
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Fetch/Download Button
            if (viewModel.selectedLevel != null && 
                viewModel.selectedStage != null && 
                viewModel.selectedWeek != null) {
                
                BrushStrokeButton(
                    text = Strings.fetchLessons(viewModel.language),
                    onClick = { 
                        // Show Ad, then Fetch
                        showInterstitialAd {
                            viewModel.fetchDocuments()
                        }
                    },
                    brushResId = R.drawable.btn_brush_thick_red // User liked Red
                )
                
                Spacer(modifier = Modifier.height(32.dp))
            }
            
            // Removed static subject icons row defined here
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // Results Section
            when (val state = viewModel.uiState) {
                is UiState.Loading -> {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(32.dp)
                    )
                }
                is UiState.Success -> {
                    // Session selector if multiple
                    // Session selector if multiple
                    if (viewModel.availableSessions.size > 1) {
                        OutlinedButton(
                            onClick = { showSessionDialog = true },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = viewModel.selectedSession?.title ?: Strings.selectSession(viewModel.language),
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                                )
                                Icon(
                                    imageVector = Icons.Default.KeyboardArrowDown,
                                    contentDescription = null
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                    
                    viewModel.selectedSession?.let { document ->
                        DocumentCard(
                            document = document,
                            language = viewModel.language,
                            onDownload = {
                                DownloadHelper.downloadDocument(
                                    context,
                                    document.url,
                                    document.title
                                )
                            },
                            showInterstitialAd = showInterstitialAd
                        )
                    }
                }
                is UiState.Error -> {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                is UiState.Idle -> {
                    // Show nothing
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Footer
            Text(
                text = "نسخة 2026",
                fontSize = 14.sp,
                color = TextSecondary,
                textAlign = TextAlign.Center
            )
            
            Text(
                text = "هذا التطبيق صُمم من طرف فريق تربوي متخصص",
                fontSize = 12.sp,
                color = TextSecondary.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Spacer(modifier = Modifier.height(100.dp)) // Increased spacer to clear the sticky banner
        }

        // Bottom Zellij Pattern
        Image(
            painter = painterResource(R.drawable.bg_zellij_bottom),
            contentDescription = "Moroccan footer",
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp) // Minimal footer height
                .align(Alignment.BottomCenter)
                .offset(y = (-50).dp), // Move it up slightly so it sits above the banner? 
                // A better approach is to just let the banner sit ON TOP of it or at the very bottom.
                // Let's Just keep it simple: Banner at absolute bottom, Zellij behind it.
                // Or better: Banner at bottom, Zellij removed? No, keep it.
                // Let's place Banner at BottomCenter.
            contentScale = ContentScale.Crop
        )

        // Sticky Ad Banner
        Box(
             modifier = Modifier
                 .fillMaxWidth()
                 .align(Alignment.BottomCenter)
                 .background(Color.White) // Ensure background is white so it doesn't overlap messily
                 .padding(bottom = 4.dp)
        ) {
            AdBanner()
        }
    }
    
    // Selection Bottom Sheets
    if (showLevelDialog) {
        SelectionBottomSheet(
            title = Strings.selectLevel(viewModel.language),
            items = viewModel.levels,
            selectedItem = viewModel.selectedLevel,
            onItemSelected = { 
                viewModel.selectLevel(it)
                showLevelDialog = false
            },
            onDismiss = { showLevelDialog = false },
            itemLabel = { it.getLabel(viewModel.language) }
        )
    }
    
    if (showSubjectDialog) {
        SelectionBottomSheet(
            title = Strings.selectSubject(viewModel.language),
            items = viewModel.subjects,
            selectedItem = viewModel.selectedSubject,
            onItemSelected = { 
                viewModel.selectSubject(it)
                showSubjectDialog = false
            },
            onDismiss = { showSubjectDialog = false },
            itemLabel = { it.getLabel(viewModel.language) }
        )
    }
    
    if (showStageDialog) {
        SelectionBottomSheet(
            title = Strings.selectStage(viewModel.language),
            items = viewModel.stages,
            selectedItem = viewModel.selectedStage,
            onItemSelected = { 
                viewModel.selectStage(it)
                showStageDialog = false
            },
            onDismiss = { showStageDialog = false },
            itemLabel = { it.getLabel(viewModel.language) }
        )
    }
    
    if (showWeekDialog) {
        SelectionBottomSheet(
            title = Strings.selectWeek(viewModel.language),
            items = viewModel.weeks,
            selectedItem = viewModel.selectedWeek,
            onItemSelected = { 
                viewModel.selectWeek(it)
                showWeekDialog = false
            },
            onDismiss = { showWeekDialog = false },
            itemLabel = { it.getLabel(viewModel.language) }
        )
    }

    if (showSessionDialog) {
        SelectionBottomSheet(
            title = Strings.selectSession(viewModel.language),
            items = viewModel.availableSessions,
            selectedItem = viewModel.selectedSession,
            onItemSelected = { 
                viewModel.selectSession(it)
                showSessionDialog = false
            },
            onDismiss = { showSessionDialog = false },
            itemLabel = { it.title }
        )
    }
// SubjectIconImage removed
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun <T> SelectionBottomSheet(
    title: String,
    items: List<T>,
    selectedItem: T?,
    onItemSelected: (T) -> Unit,
    onDismiss: () -> Unit,
    itemLabel: (T) -> String
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = Color.White,
        shape = androidx.compose.foundation.shape.RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            items.forEach { item ->
                val isSelected = item == selectedItem
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onItemSelected(item) }
                        .background(
                            color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                        )
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = itemLabel(item),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontSize = 20.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                        ),
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}
