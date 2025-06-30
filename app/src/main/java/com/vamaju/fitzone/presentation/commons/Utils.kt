package com.vamaju.fitzone.presentation.commons

import android.os.Build
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

/**
 * @author Juan Camilo Collantes Tovar on 30/06/2025
 * **/



/**
 * Calcula la hora de finalización sumando la duración a la hora de inicio.
 * Asume que 'time' y 'duration' están en formato "HH:mm".
 */
fun calculateEndTime(startTime: String, duration: String): String {
    return try {
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("HH:mm")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val startLocalTime = LocalTime.parse(startTime, formatter)

        val parts = duration.split(":")
        val hours = parts[0].toLongOrNull() ?: 0
        val minutes = parts[1].toLongOrNull() ?: 0

        val endLocalTime = startLocalTime.plusHours(hours).plusMinutes(minutes)
        endLocalTime.format(formatter)
    } catch (e: DateTimeParseException) {
        "Hora inválida"
    } catch (e: Exception) {
        "Error al calcular"
    }
}