package com.example.currencyexchangerates.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.ui.theme.MyAppTheme

@Composable
fun CurrencyCard() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(100.dp)
            .background(color = MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {

    }
}

@Preview(showBackground = false)
@Composable
fun PreviewCurrencyCard() {
    MyAppTheme {
        CurrencyCard()
    }
}