package com.mb.pvc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.mb.pvc.presentation.navigation.BottomNavItem
import com.mb.pvc.presentation.navigation.NavGraph
import com.mb.pvc.ui.theme.PVCTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PVCTheme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        },
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        NavGraph(
            navController = navController,
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                bottom = 0.dp
            )
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(24.dp)),
        containerColor = Color.White,
        tonalElevation = 8.dp,
        windowInsets = WindowInsets(0, 0, 0, 0)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        
        BottomNavItem.items.forEach { item ->
            val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
            
            NavigationBarItem(
                icon = { 
                    Icon(
                        imageVector = item.icon, 
                        contentDescription = null
                    ) 
                },
                label = { 
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.labelMedium
                    ) 
                },
                selected = selected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            )
        }
    }
}
