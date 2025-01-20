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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.currencyexchangerates.ui.screens.ChooseCurrency
import com.example.currencyexchangerates.ui.screens.MainBackground
import com.example.currencyexchangerates.ui.theme.MyAppTheme

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

                    NavHost(
                        navController = navController,
                        startDestination = "Main"
                    ) {
                        composable("Main") {
                            MainBackground(
                                viewModel.baseCurrency,
                                viewModel.targetCurrency,
                                viewModel.baseAmount,
                                viewModel.targetAmount,
                                { viewModel.updateBaseAmount(it) },
                                { viewModel.updateTargetAmount(it) },
                                navToChooseCurrency = {
                                    navController.navigate("ChooseCurrency")
                                }
                            )
                        }

                        composable("ChooseCurrency") {
                            ChooseCurrency(
                                onBackButton = { navController.navigate("Main") }
                            )
                        }
                    }
                }

                /*Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background // This will apply your Compose background
                ) {
                    MainBackground(
                        viewModel.baseCurrency,
                        viewModel.targetCurrency,
                        viewModel.baseAmount,
                        viewModel.targetAmount,
                        { viewModel.updateBaseAmount(it) },
                        { viewModel.updateTargetAmount(it) }
                    )
                }*/
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        //save the current currencies and amounts to a local database
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