package com.example.currencyexchangerates.ui.components

import java.math.BigDecimal
import java.math.RoundingMode
import android.icu.text.DecimalFormat
import android.icu.util.Currency
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.UserEvent
import com.example.currencyexchangerates.data.CurrencyData
import com.example.currencyexchangerates.ui.theme.MyAppTheme

@Composable
fun CurrencyCard(
    currency: CurrencyData,
    type: String,
    updateAmount: (event: UserEvent) -> Unit,
    onCardClick: () -> Unit
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
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = currency.currency.currencyCode
            )

            Box(
                modifier = Modifier.background(MaterialTheme.colorScheme.surface)
            ) {
                OutlinedTextField(
                    value = currency.amount,
                    onValueChange = {newVal ->
                        if (newVal.all { it.isDigit() || it == '.' } && newVal.count { it == '.' } <= 1) {
                            if (type == "base") {
                                updateAmount(UserEvent.ChangeBaseAmount(newVal))
                            }
                            else {
                                updateAmount(UserEvent.ChangeTargetAmount(newVal))
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(0.dp)
                        .width(150.dp)
                        .fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    shape = RoundedCornerShape(16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number // Show number pad
                    ),
                    //label = {Text("Enter amount")}
                )
            }

            IconButton(onClick = { onCardClick() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = "null",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewCurrencyCard() {
    MyAppTheme {
        CurrencyCard(CurrencyData(Currency.getInstance("EUR"), "100.21123"), "base", {/* TODO */}, {})
    }
}