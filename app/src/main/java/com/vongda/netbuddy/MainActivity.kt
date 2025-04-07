package com.vongda.netbuddy

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vongda.handgestureprototype.ui.handgesture.PoseCameraScreen
import com.vongda.netbuddy.data.viewmodels.MatchViewModel
import com.vongda.netbuddy.ui.home.HomeScreen
import com.vongda.netbuddy.ui.instructions.InstructionsScreen
import com.vongda.netbuddy.ui.instructions.components.Instruction
import com.vongda.netbuddy.ui.matchsettings.MatchSettingsScreen
import com.vongda.netbuddy.ui.results.ResultsScreen
import com.vongda.netbuddy.ui.theme.PoseDetectorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PoseDetectorTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WithPermission(
                        modifier = Modifier.padding(innerPadding),
                        permission = Manifest.permission.CAMERA
                    ) {
                        App (
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}