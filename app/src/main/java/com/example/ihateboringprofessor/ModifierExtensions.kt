package com.example.ihateboringprofessor

import androidx.compose.ui.Modifier

fun Modifier.conditional(condition: Boolean, modifier: Modifier.() -> Modifier): Modifier {
    return if (condition) this.then(modifier(this)) else this
}
