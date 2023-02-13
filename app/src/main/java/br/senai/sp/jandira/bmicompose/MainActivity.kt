package br.senai.sp.jandira.bmicompose

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.booleanResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.senai.sp.jandira.bmicompose.ui.theme.BMIComposeTheme
import br.senai.sp.jandira.bmicompose.utils.bmiCalculate
import br.senai.sp.jandira.bmicompose.utils.getColors

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BMIComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
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

    var expandState by remember {
        mutableStateOf(false)
    }

    var bmiScorState by remember {
        mutableStateOf(0.0)
    }

    var isWeightError by remember {
        mutableStateOf(false)
    }

    var isHeightState by remember {
        mutableStateOf(false)
    }

    var colorScore by remember {
        mutableStateOf(false)
    }

//    var isTextHeightError by remember {
//        mutableStateOf(false)
//    }
//
//    var isTextWeightError by remember {
//        mutableStateOf(false)
//    }

    //Objeto que controla a requisicao de foco(RequestFocus)
    var weightFocusRequester = FocusRequester()

    //Content
    Column(
        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
    ) {
        //Header
        Column(
            modifier = Modifier
                .fillMaxWidth()
                //Espaçamento no top
                .padding(top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                //BUscar a imagem
                painter = painterResource(id = R.drawable.bmi256),
                contentDescription = "Application Icon",
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
                value = weightState,
                onValueChange = { newWeight ->
                    var lastChar = if (newWeight.length == 0) {
                        isWeightError = true
                        newWeight
                    } else {
                        newWeight.get(newWeight.length - 1)
                        isWeightError = false
                    }

                    var newValue = if (lastChar == '.' || lastChar == ',') newWeight.dropLast(1)
                    else newWeight

                    weightState = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(weightFocusRequester),
                leadingIcon = {
                    Icon(imageVector = Icons.Default.MonitorWeight, contentDescription = "")
                },
                trailingIcon = {
                    if (isWeightError) Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = ""
                    )
                },
                isError = isWeightError,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(16.dp)

            )
            if (isWeightError) {
                Text(
                    text = stringResource(id = R.string.error_text_weight),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    textAlign = TextAlign.End
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.height),
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = heightState,
                onValueChange = { newHeight ->
                    var lastChar = if (newHeight.length == 0) {
                        isHeightState = true
                        newHeight
                    } else {
                        newHeight.get(newHeight.length - 1)
                        isHeightState = false
                    }
                    var newValue = if (lastChar == '.' || lastChar == ',') newHeight.dropLast(1)
                    else newHeight

                    heightState = newHeight
                },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Height, contentDescription = "")
                },
                trailingIcon = {
                    if (isHeightState) Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = ""
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                isError = isHeightState,
                singleLine = true,
                shape = RoundedCornerShape(16.dp)

            )
            if (isHeightState) {
                Text(
                    text = stringResource(id = R.string.error_text_height),
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.Red,
                    textAlign = TextAlign.End
                )
            }
            Button(
                onClick = {
                    isWeightError = weightState.length == 0
                    isHeightState = heightState.length == 0
                    if (isHeightState == false && isWeightError == false) {
                        bmiScorState = bmiCalculate(weightState.toInt(), heightState.toDouble())
                        expandState = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color(34, 139, 34))
            ) {
                Text(
                    text = stringResource(id = R.string.button_calculate),
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
            Spacer(
                modifier = Modifier.height(20.dp)
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
        AnimatedVisibility(visible = expandState, enter = slideIn(tween(durationMillis = 400)) {
            IntOffset(it.width, 10000)
        }, exit = slideOut(tween(durationMillis = 500)) {
            IntOffset(it.width, 10000)
        }

        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(1f),
                shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
                //Chamando a fun de colors criada no utils
                backgroundColor = getColors(bmiScorState)
            )
            {
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
                        text = String.format("%.2f", bmiScorState),
                        fontSize = 48.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Congratulaiotions meu nobre, peso ideal!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight(300),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                expandState = false
                                weightState = ""
                                heightState = ""
                                weightFocusRequester.requestFocus()
                            },
                            modifier = Modifier.height(height = 48.dp),
                            colors = ButtonDefaults.buttonColors(Color(137, 119, 248))
                        ) {
                            Text(text = stringResource(id = R.string.button_reset))
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Button(
                            onClick = { /*TODO*/ },
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
    showBackground = true, showSystemUi = true
)
@Composable
fun BmiCalculatoePrevie() {
    BMICalculator()
}