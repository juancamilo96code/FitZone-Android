package com.vamaju.fitzone.presentation.book_class.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vamaju.fitzone.presentation.book_class.model.SubscriptionTypeUiModel
import java.text.NumberFormat
import java.util.Locale

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/
@Composable
fun SubscriptionTypeCard(
    subscription: SubscriptionTypeUiModel,
    isSelected: Boolean,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currencyFormat = remember { NumberFormat.getCurrencyInstance(Locale("es", "CO")) }

    Card(
        onClick = { onClick(subscription.id) },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .then(
                if (isSelected) {
                    Modifier.border(
                        BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                        RoundedCornerShape(12.dp)
                    )
                } else {
                    Modifier
                }
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = subscription.icon,
                contentDescription = subscription.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = subscription.name,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = currencyFormat.format(subscription.price),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subscription.type,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSubscriptionTypeCard() {
    MaterialTheme {
        val sampleSubscription = SubscriptionTypeUiModel(
            id = "1",
            name = "Gold",
            icon = Icons.Default.Star,
            price = 99.99,
            type = "Mensual"
        )
        Column {
            SubscriptionTypeCard(subscription = sampleSubscription, isSelected = true, onClick = {})
            Spacer(modifier = Modifier.height(16.dp))
            SubscriptionTypeCard(
                subscription = sampleSubscription.copy(
                    id = "2",
                    name = "Platino",
                    icon = Icons.Default.Star
                ), isSelected = false, onClick = {})
        }
    }
}