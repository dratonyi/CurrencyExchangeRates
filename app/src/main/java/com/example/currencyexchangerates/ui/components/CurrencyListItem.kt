package com.example.currencyexchangerates.ui.components

import android.icu.util.Currency
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.currencyexchangerates.R
import com.example.currencyexchangerates.UserEvent
import com.example.currencyexchangerates.ui.theme.MyAppTheme

@Composable
fun CurrencyListItem(
    currency: Map.Entry<String, String>,
    onEvent: (event: UserEvent) -> Unit,
    onBackButton: () -> Unit,
    getFlagId: (code: String) -> Int
    ) {
    Button(
        onClick = {
            onEvent(UserEvent.ChangeCurrency(currency.key))
            onBackButton()
        },
        modifier = Modifier
            /*.background(
                color = MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(16.dp)
            )*/
            .clip(RoundedCornerShape(16.dp))
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary,
            disabledContentColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.surface
        )
    ) {

        /*Surface(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(16.dp)
                )
                .clip(RoundedCornerShape(16.dp))
                .height(100.dp)
        ) {*/
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(getFlagId(currency.key)),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 30.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = currency.key,
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp
                    )

                    //Spacer(modifier = Modifier.height(1.dp))


                    Text(
                        text = currency.value,
                        fontSize = 20.sp,
                        lineHeight = 20.sp
                    )
                }
            }

    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCurrencyListItem() {
    MyAppTheme {
        //CurrencyListItem(mapOf("CAD" to "Canadian Dollar").entries.toList()[0], {}, {})
    }
}
