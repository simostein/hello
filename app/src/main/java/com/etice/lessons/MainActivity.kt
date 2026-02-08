package com.etice.lessons

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import com.etice.lessons.data.models.Language
import com.etice.lessons.ui.screens.MainScreen
import com.etice.lessons.ui.theme.ETiceLessonsTheme
import com.etice.lessons.ui.viewmodel.MainViewModel

import com.google.android.gms.ads.MobileAds

import com.etice.lessons.utils.InterstitialAdHelper

class MainActivity : ComponentActivity() {
    private lateinit var interstitialAdHelper: InterstitialAdHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize AdMob
        MobileAds.initialize(this) {}
        
        // Initialize Interstitial Ad Helper
        interstitialAdHelper = InterstitialAdHelper(this)
        interstitialAdHelper.loadAd()
        
        setContent {
            val viewModel: MainViewModel = viewModel()
            
            // Observe language changes to update layout direction
            val layoutDirection = if (viewModel.language == Language.ARABIC) {
                LayoutDirection.Rtl
            } else {
                LayoutDirection.Ltr
            }
            
            ETiceLessonsTheme(layoutDirection = layoutDirection) {
                MainScreen(
                    viewModel = viewModel,
                    showInterstitialAd = { onAction ->
                        interstitialAdHelper.showAd(this@MainActivity) {
                            onAction()
                        }
                    }
                )
            }
        }
    }
}
