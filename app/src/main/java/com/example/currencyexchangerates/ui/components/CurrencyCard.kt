package com.example.currencyexchangerates.ui.components

import java.math.BigDecimal
import java.math.RoundingMode
import android.icu.text.DecimalFormat
import android.icu.util.Currency
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
fun CurrencyCard(
    currency: Currency,
    amount: Double
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .height(100.dp)
            .background(color = MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = currency.currencyCode
            )

            Text(
                text = "%.${currency.defaultFractionDigits}f".format(amount)
                 //BigDecimal(amount).setScale(currency.defaultFractionDigits, RoundingMode.HALF_UP).toString()
                //BigDecimal(amount).setScale(currency.defaultFractionDigits, RoundingMode.HALF_UP).toString()
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewCurrencyCard() {
    MyAppTheme {
        CurrencyCard(Currency.getInstance("EUR"), 100.21123)
    }
}