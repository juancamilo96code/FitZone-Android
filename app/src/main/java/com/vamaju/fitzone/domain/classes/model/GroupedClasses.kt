package com.vamaju.fitzone.domain.classes.model

/**
 * @author Juan Camilo Collantes Tovar on 01/07/2025
 * **/
data class GroupedClasses(
    val upcoming: List<ClassModel>,
    val past: List<ClassModel>
)
