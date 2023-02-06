package br.senai.sp.jandira.bmicompose

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bmicompose.ui.theme.BMIComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMIComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BMICalculator()
                }
            }
        }
    }
}


@Composable
fun BMICalculator() {

    var weightState by rememberSaveable() {
        mutableStateOf("")
    }

    var heightState by rememberSaveable() {
        mutableStateOf("")
    }


    //Content
    Column(
        modifier = Modifier
            .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
    ) {
        //Header
        Column (
            modifier = Modifier
                .fillMaxWidth()
                //Espaçamento no top
                .padding(top = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                //BUscar a imagem
                painter = painterResource(id = R.drawable.bmi256), contentDescription = "Application Icon",
                //Modifica o tamanho da imagem
                modifier = Modifier.size(120.dp)
            )
            Text(
                //Busca um texto no arquivo de strings
                text = stringResource(id = R.string.app_title),
                color = Color.Blue,
                fontSize = 32.sp,
                letterSpacing = 3.sp
            )
        }
        //Form
        Column(
            modifier = Modifier
                .padding(top = 24.dp, start = 32.dp, end = 32.dp)
                .fillMaxWidth()

        ) {
            Text(
                text = stringResource(id = R.string.weight),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = weightState, onValueChange = {
                weightState = it
            },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)

            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.height),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = heightState, onValueChange = {
                heightState = it
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)

            )

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color(34,139,34))
            ) {
                Text(
                    text = stringResource(id = R.string.button_calculate),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier
                .height(20.dp)
            )

//            OutlinedTextField(
//                value = "",
//                onValueChange = {},
//                modifier = Modifier.fillMaxWidth(),
//                label = {
//                    Text(text = "Digite Algo")
//                }
//            )
        }
        //Footer
        Column(

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
                backgroundColor = MaterialTheme.colors.primary,
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                ) {
                Column(
                    modifier = Modifier
                        .padding(18.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(id = R.string.footer_text),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "0.00",
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Congratulaiotions meu nobre, peso ideal!",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier.height(height = 48.dp),
                            colors = ButtonDefaults.buttonColors(Color(137, 119, 248))
                            ) {
                            Text(text = stringResource(id = R.string.button_reset))
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(onClick = { /*TODO*/ },
                            modifier = Modifier.height(height = 48.dp),
                            colors = ButtonDefaults.buttonColors(Color(137, 119, 248))
                        ) {
                            Text(text = stringResource(id = R.string.button_share))
                        }
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun BmiCalculatoePrevie() {
    BMICalculator()
}