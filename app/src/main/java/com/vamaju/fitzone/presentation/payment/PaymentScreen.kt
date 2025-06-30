package com.vamaju.fitzone.presentation.payment

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

private val OnBackgroundPrimary = Color(0xFF111418)
private val OnBackgroundSecondary = Color(0xFF60758a)
private val BackgroundGray = Color(0xFFf0f2f5)
private val BorderGray = Color(0xFFdbe0e6)
private val PrimaryBlue = Color(0xFF3d98f4)

/**
 * Composable principal de la pantalla de pago.
 *
 * Utiliza un `Scaffold` para la estructura básica y un `Column` para el contenido.
 */
@Composable
fun PaymentScreen(onClose: () -> Unit) {
    var selectedPaymentMethod by remember { mutableStateOf("**** 1234") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvc by remember { mutableStateOf("") }
    var cardHolderName by remember { mutableStateOf("") }
    var postalCode by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            PaymentTopBar(onClose = onClose)
        },
        bottomBar = {
            PaymentBottomBar(totalAmount = 20, onPay = { /* Handle payment logic */ })
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
        ) {

            SectionTitle(title = "Payment Method")
            PaymentMethodSelector(
                paymentMethods = listOf("**** 1234", "Add Payment Method"),
                selectedMethod = selectedPaymentMethod,
                onMethodSelected = { method -> selectedPaymentMethod = method }
            )

            PaymentInputField(
                placeholder = "Card Number",
                value = cardNumber,
                onValueChange = { cardNumber = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PaymentInputField(
                    placeholder = "MM/YY",
                    value = expiryDate,
                    onValueChange = { expiryDate = it },
                    modifier = Modifier.weight(1f)
                )
                PaymentInputField(
                    placeholder = "CVC",
                    value = cvc,
                    onValueChange = { cvc = it },
                    modifier = Modifier.weight(1f)
                )
            }
            PaymentInputField(
                placeholder = "Name on Card",
                value = cardHolderName,
                onValueChange = { cardHolderName = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
            )
            PaymentInputField(
                placeholder = "Postal Code",
                value = postalCode,
                onValueChange = { postalCode = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp)
            )
        }
    }
}

/**
 * Barra superior de la pantalla de pago.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentTopBar(onClose: () -> Unit) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Payment",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnBackgroundPrimary
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 48.dp)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = OnBackgroundPrimary
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White
        )
    )
}

/**
 * Título de sección reutilizable.
 */
@Composable
fun SectionTitle(title: String, modifier: Modifier = Modifier) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = OnBackgroundPrimary,
            lineHeight = 24.sp,
            letterSpacing = (-0.015).sp
        ),
        modifier = modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 8.dp)
    )
}

/**
 * Selector de método de pago (chips).
 */
@Composable
fun PaymentMethodSelector(
    paymentMethods: List<String>,
    selectedMethod: String,
    onMethodSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        paymentMethods.forEach { method ->
            PaymentMethodChip(
                method = method,
                isSelected = method == selectedMethod,
                onMethodSelected = onMethodSelected
            )
        }
    }
}

/**
 * Chip para seleccionar un método de pago.
 */
@Composable
fun PaymentMethodChip(method: String, isSelected: Boolean, onMethodSelected: (String) -> Unit) {
    val borderColor = if (isSelected) PrimaryBlue else BorderGray
    val borderWidth = if (isSelected) 3.dp else 1.dp
    val padding = if (isSelected) 14.dp else 16.dp

    Box(
        modifier = Modifier
            .widthIn(min = 96.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .border(borderWidth, borderColor, RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { onMethodSelected(method) }
            .padding(horizontal = padding, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = method,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = OnBackgroundPrimary
        )
    }
}

/**
 * Campo de entrada de texto para el formulario de pago.
 */
@Composable
fun PaymentInputField(
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(BackgroundGray)
            .padding(16.dp),
        textStyle = TextStyle(
            color = OnBackgroundPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        ),
        singleLine = true,
        decorationBox = { innerTextField ->
            Box(contentAlignment = Alignment.CenterStart) {
                if (value.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = OnBackgroundSecondary,
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal)
                    )
                }
                innerTextField()
            }
        }
    )
}

/**
 * Barra inferior con el botón de pago.
 */
@Composable
fun PaymentBottomBar(totalAmount: Int, onPay: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Button(
            onClick = onPay,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PrimaryBlue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            Text(
                text = "Pay $$totalAmount",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.015.sp
                )
            )
        }
    }
}

/**
 * Vista previa de la pantalla de pago.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PaymentScreenPreview() {
    FitZoneTheme {
        PaymentScreen(onClose = {})
    }
}