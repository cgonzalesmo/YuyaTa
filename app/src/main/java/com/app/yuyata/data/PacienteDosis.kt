package com.app.yuyata.data

import androidx.room.Embedded
import androidx.room.Relation

data class PacienteDosis(
    @Embedded
    val paciente: Paciente,
    @Relation(
        parentColumn = "paciente_id",
        entityColumn = "pacienteCreatorId"
    )
    val dosis: List<Dosis>
)