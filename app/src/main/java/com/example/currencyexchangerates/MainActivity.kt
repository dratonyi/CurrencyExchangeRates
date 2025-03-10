package com.example.currencyexchangerates

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.currencyexchangerates.data.AppDatabase
import com.example.currencyexchangerates.data.DatabaseDAO
import com.example.currencyexchangerates.ui.screens.ChooseCurrency
import com.example.currencyexchangerates.ui.screens.MainBackground
import com.example.currencyexchangerates.ui.theme.MyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel: MainViewModel by viewModels()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        window.setBackgroundDrawableResource(android.R.color.transparent)

        setContent {
            MyAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { internalPadding ->
                    val navController = rememberNavController()
                    internalPadding

                    val baseCurrency = viewModel.baseCurrency.collectAsState()
                    val targetCurrency = viewModel.targetCurrency.collectAsState()
                    val search = viewModel.currSearch.collectAsState()
                    val currencyList = viewModel.allCurrencies.collectAsState()

                    NavHost(
                        navController = navController,
                        startDestination = "Main"
                    ) {
                        composable("Main") {
                            MainBackground(
                                baseCurrency.value,
                                targetCurrency.value,
                                { viewModel.onEvent(it) },
                                navToChooseCurrency = {
                                    navController.navigate("ChooseCurrency")
                                }
                            )
                        }

                        composable("ChooseCurrency") {
                            ChooseCurrency(
                                onBackButton = {
                                    navController.navigateUp()
                                },
                                currencyList.value,
                                search.value,
                                { viewModel.onEvent(it) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
     MyAppTheme {

        Greeting("Android")
    }
}