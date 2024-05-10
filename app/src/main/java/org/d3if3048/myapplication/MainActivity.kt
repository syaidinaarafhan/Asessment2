package org.d3if3048.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.d3if3048.myapplication.navigation.Screen
import org.d3if3048.myapplication.ui.screen.DetailScreen
import org.d3if3048.myapplication.ui.screen.KEY_ID_MAHASISWA
import org.d3if3048.myapplication.ui.screen.MainScreen
import org.d3if3048.myapplication.ui.theme.Asessment2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Asessment2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    //navController.navigate("aboutScreen")

                    NavHost(
                        navController = navController,
                        startDestination = "mainScreen"
                    ){
                        composable("mainScreen"){
                            MainScreen(navController)
                        }
                        composable("detailScreen"){
                            DetailScreen(navController)
                        }
                        composable(
                            route = Screen.FormUbah.route,
                            arguments = listOf(
                                navArgument(KEY_ID_MAHASISWA) {type = NavType.LongType}
                            )
                        ){ navBackStackEntry ->
                            val id = navBackStackEntry.arguments?.getLong(KEY_ID_MAHASISWA)
                            DetailScreen(navController,id)
                        }
                    }
                }
            }
        }
    }
}

