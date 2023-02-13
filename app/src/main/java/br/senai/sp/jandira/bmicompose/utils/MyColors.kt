package br.senai.sp.jandira.bmicompose.utils

import androidx.compose.ui.graphics.Color

fun getColors(bmi: Double): Color {
    if (bmi <= 18.5){
        return Color.Red
    }else if(bmi > 18.5 && bmi < 24.9){
        return Color.Green
    }else if (bmi in 25.0..29.9){
        return Color.Yellow
    }else if (bmi in 30.0..34.9){
        return Color.Yellow
    }else if (bmi in 35.0..39.9){
        return Color.Gray
    }else{
        return Color.Blue
    }
}