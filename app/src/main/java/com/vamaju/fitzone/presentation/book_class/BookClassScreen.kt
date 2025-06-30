package com.vamaju.fitzone.presentation.book_class

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vamaju.fitzone.R
import com.vamaju.fitzone.ui.theme.FitZoneTheme

/**
 * @author Juan Camilo Collantes Tovar on 28/06/2025
 * **/

// Definir los colores y la tipografía para reutilizarlos.
private val OnBackgroundPrimary = Color(0xFF111418)
private val OnBackgroundSecondary = Color(0xFF60758a)
private val PrimaryBlue = Color(0xFF3d98f4)
private val BorderGray = Color(0xFFdbe0e6)

// Modelos de datos para la UI
data class ClassInfo(
    val gymName: String,
    val className: String,
    val instructor: String,
    val imageUrl: String
)

data class CalendarState(
    val currentMonth: String,
    val daysOfWeek: List<String>,
    val daysOfMonth: List<Pair<Int?, Boolean>>, // Pair<Day, isSelected>
    val selectedDay: Int?
)

/**
 * Composable principal de la pantalla de reserva de clase.
 *
 * Usa un `Scaffold` con una barra superior, un contenido desplazable y una barra inferior.
 */
@Composable
fun BookClassScreen(onClose: () -> Unit) {
    // Estado para el calendario y la hora seleccionada
    var selectedDate by remember { mutableStateOf(5) } // Día 5 de octubre
    var selectedTime by remember { mutableStateOf("10:00 AM") }

    Scaffold(
        topBar = {
            BookClassTopBar(onClose = onClose)
        },
        bottomBar = {
            BookClassBottomBar(onBookClass = { /* Handle booking logic */ })
        },
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Información de la clase
            ClassSummaryCard(
                classInfo = ClassInfo(
                    gymName = "FitZone",
                    className = "Yoga Flow",
                    instructor = "Instructor: Sarah Miller",
                    imageUrl = "https://picsum.photos/400/225?random=8"
                )
            )

            // Sección de selección de fecha (Calendario)
            SectionTitle(title = "Select Date")
            /*CalendarPicker(
                month = "October 2024",
                selectedDay = selectedDate,
                onDaySelected = { day -> selectedDate = day }
            )*/

            // Sección de selección de hora
            SectionTitle(title = "Select Time")
            TimeSlotSelector(
                timeSlots = listOf("10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM"),
                selectedTime = selectedTime,
                onTimeSelected = { time -> selectedTime = time }
            )
        }
    }
}

/**
 * Barra superior de la pantalla de reserva.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookClassTopBar(onClose: () -> Unit) {
    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Book Class",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnBackgroundPrimary
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(end = 48.dp) // Offset para centrar con el icono.
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
 * Tarjeta de resumen de la clase.
 */
@Composable
fun ClassSummaryCard(classInfo: ClassInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(2f)) {
            Text(
                text = classInfo.gymName,
                style = MaterialTheme.typography.bodySmall,
                color = OnBackgroundSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = classInfo.className,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = OnBackgroundPrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = classInfo.instructor,
                style = MaterialTheme.typography.bodySmall,
                color = OnBackgroundSecondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        AsyncImage(
            model = classInfo.imageUrl,
            contentDescription = "Class image",
            modifier = Modifier
                .weight(1f)
                .aspectRatio(16f / 9f)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
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
 * Selector de fecha (Calendario).
 */
@Composable
fun CalendarPicker(
    month: String,
    selectedDay: Int,
    onDaySelected: (Int) -> Unit
) {
    // Ejemplo de datos para el calendario de octubre de 2024 (empieza en miércoles)
    val days = remember {
        val emptyDays = listOf(null, null, null) // Para los días vacíos antes del 1
        val monthDays = (1..30).toList()
        emptyDays + monthDays
    }

    val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Cabecera del mes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { /* Previous month */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_caret_left_regular),
                    contentDescription = "Previous month",
                    tint = OnBackgroundPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
            Text(
                text = month,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = OnBackgroundPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { /* Next month */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_caret_right_regular),
                    contentDescription = "Next month",
                    tint = OnBackgroundPrimary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // Días de la semana
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = OnBackgroundPrimary,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    textAlign = TextAlign.Center
                )
            }
        }

        // Días del mes
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Column(modifier = Modifier.weight(1f)) {
                repeat(5) { i ->
                    CalendarRow(
                        weekDays = days.subList(i * 7, (i * 7) + 7),
                        selectedDay = selectedDay,
                        onDaySelected = onDaySelected
                    )
                }
            }
        }
    }
}

/**
 * Fila de la semana en el calendario.
 */
@Composable
fun CalendarRow(weekDays: List<Int?>, selectedDay: Int, onDaySelected: (Int) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        weekDays.forEach { day ->
            DayCell(
                day = day,
                isSelected = day == selectedDay,
                onDaySelected = onDaySelected
            )
        }
    }
}

/**
 * Celda individual del día en el calendario.
 */
@Composable
fun DayCell(day: Int?, isSelected: Boolean, onDaySelected: (Int) -> Unit) {
    Box(
        modifier = Modifier
            //.weight(1f)
            .aspectRatio(1f) // Cuadrado
            .padding(4.dp)
            .clip(CircleShape)
            .background(if (isSelected) PrimaryBlue else Color.Transparent)
            .clickable(enabled = day != null) {
                if (day != null) onDaySelected(day)
            },
        contentAlignment = Alignment.Center
    ) {
        if (day != null) {
            Text(
                text = "$day",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = if (isSelected) Color.White else OnBackgroundPrimary
            )
        }
    }
}

/**
 * Selector de franjas horarias.
 */
@Composable
fun TimeSlotSelector(
    timeSlots: List<String>,
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        timeSlots.forEach { time ->
            TimeSlotChip(
                time = time,
                isSelected = time == selectedTime,
                onTimeSelected = onTimeSelected
            )
        }
    }
}

/**
 * Chip para seleccionar una franja horaria.
 */
@Composable
fun TimeSlotChip(time: String, isSelected: Boolean, onTimeSelected: (String) -> Unit) {
    val borderColor = if (isSelected) PrimaryBlue else BorderGray
    val borderWidth = if (isSelected) 3.dp else 1.dp
    val padding = if (isSelected) 10.dp else 16.dp

    Box(
        modifier = Modifier
            .widthIn(min = 96.dp)
            .wrapContentHeight()
            .clip(RoundedCornerShape(8.dp))
            .border(borderWidth, borderColor, RoundedCornerShape(8.dp))
            .background(Color.White)
            .clickable { onTimeSelected(time) }
            .padding(horizontal = padding, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = time,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
            color = OnBackgroundPrimary
        )
    }
}

/**
 * Barra inferior con el botón de reserva.
 */
@Composable
fun BookClassBottomBar(onBookClass: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Button(
            onClick = onBookClass,
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
                text = "Book Class",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.015.sp
                )
            )
        }
    }
}

/**
 * Vista previa de la pantalla de reserva de clase.
 *
 * NOTA: Para que los iconos personalizados funcionen en la vista previa,
 * debes agregarlos como Drawables vectoriales en `app/src/main/res/drawable`.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BookClassScreenPreview() {
    FitZoneTheme {
        BookClassScreen(onClose = {})
    }
}