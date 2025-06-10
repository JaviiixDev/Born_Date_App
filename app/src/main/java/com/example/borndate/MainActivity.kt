package com.example.borndate

import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.borndate.ui.theme.BornDateTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BornDateTheme {
                BornDateUI()
            }
        }
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BornDateUI(){
    //obtiene el año actual
    val currentYear = LocalDate.now().year

    //recuerda el año escrito en el textfield
    var birthYear by remember { mutableStateOf("") }

    //calcula la edad
    val age = birthYear.toIntOrNull()?.let { currentYear - it }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFEFEFEF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        //texto de instruccion
        Text(
            text = stringResource(R.string.label_in_born_year),
            fontWeight = FontWeight.Light,
            fontSize = 20.sp
        )
        //crea el text field con todos los componentes nesesarios
        EditNumberField(
            label = R.string.textfield,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            leadingIcon = R.drawable.baseline_cake_24,
            value = birthYear,
            onValueChange = { birthYear = it },
            modifier = Modifier
                .padding(bottom = 32.dp, top = 20.dp)
        )
        //texto que indica la edad
        Text(
            text = stringResource(R.string.label_out_age),
            fontWeight = FontWeight.Light,
            fontSize = 20.sp
        )
        //muestra la edad en un texto si esta es correcta
        if (age != null && age >= 0){
            Text(
                text = "$age",
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp
            )
            //muestra un error si la edad es menor a 0
        }else if(age != null && age < 0){
            Text(
                text = stringResource(R.string.label_out_incorrect_age),
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp
            )
            //si la edad es nula muestra un generico
        }else{
            Text(
                text = stringResource(R.string.label_out_null_age),
                fontWeight = FontWeight.Bold,
                fontSize = 45.sp
            )
        }
        //muestra la imagen que obtiene desde la funcion getImageForAge
        Image(
            painter = painterResource(id = getImageForAge(age)),
            contentDescription = null,
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
                .padding(16.dp),
            contentScale = ContentScale.Crop
        )

    }
}
//es el textfield configurado con el tipo de teclado, nombre, icono etc.
@Composable
fun EditNumberField(
    @StringRes label: Int,
    keyboardOptions: KeyboardOptions,
    @DrawableRes leadingIcon: Int,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null) },
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(stringResource(label)) },
        modifier = modifier,
        keyboardOptions = keyboardOptions,
        shape = RoundedCornerShape(12.dp)
    )
}
//retorna la imagen correcta dependiendo de la edad o si es nulo
fun getImageForAge(age: Int?): Int {
    return when {
        age == null -> R.drawable.interrogacion
        age < 0 -> R.drawable.incorrecto
        age < 5 -> R.drawable.baby
        age < 18 -> R.drawable.kid
        age < 30 -> R.drawable.young
        age < 60 -> R.drawable.adult
        age < 100 -> R.drawable.old
        else -> R.drawable.tomb
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BornDateTheme {
        BornDateUI()
    }
}