
package com.example.geradorsenhas

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geradorsenhas.ui.theme.AzulPrincipal
import com.example.geradorsenhas.ui.theme.GeradorSenhasTheme
import com.example.geradorsenhas.ui.theme.fontSora
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun App(){
    //Variável para armazenar valor da primeira CheckBox
    var caracteresEspeciais by remember {
        mutableStateOf(false)
    }

    var comNumeros by remember {
        mutableStateOf(false)
    }

    var qtdCaracteres:String? by remember {
        mutableStateOf(null)
    }

    var senhaFinalGerada by remember {
        mutableStateOf("")
    }

    var senhaCopiada by remember {
        mutableStateOf("")
    }

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Gerador de Senhas",
                    fontFamily = fontSora,
                    fontWeight = FontWeight.ExtraBold,
                    color = AzulPrincipal,
                    modifier = Modifier.padding(8.dp)
                )

                Image(painter = painterResource(id = R.drawable.lock), contentDescription = null)
            }

            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 35.dp)){
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .border(1.5.dp, Color.LightGray, RoundedCornerShape(5))
                                .padding(8.dp)){
                            Row (modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically){
                                Text(text = "Caracteres Especiais ?",
                                    modifier = Modifier.padding(end = 4.dp),
                                    fontFamily = fontSora, fontWeight = FontWeight.SemiBold,
                                    color = Color.Black)
                                Checkbox(checked = caracteresEspeciais,
                                    onCheckedChange = {isChecked:Boolean -> caracteresEspeciais = isChecked},
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(15)),
                                    colors = CheckboxDefaults.colors(checkedColor = Color.Blue, uncheckedColor = Color.LightGray))
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 8.dp),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically){
                                Text(text = "Com números ?",
                                    modifier = Modifier.padding(end = 4.dp),
                                    fontFamily = fontSora, fontWeight = FontWeight.SemiBold,
                                    color = Color.Black)
                                Checkbox(checked = comNumeros,
                                    onCheckedChange = {isChecked:Boolean -> comNumeros = isChecked},
                                    modifier = Modifier
                                        .background(Color.White, RoundedCornerShape(15)),
                                    colors = CheckboxDefaults.colors(checkedColor = Color.Blue, uncheckedColor = Color.LightGray, checkmarkColor = Color.White))
                            }
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp, start = 8.dp, end = 8.dp),
                                horizontalArrangement = Arrangement.Start){
                                OutlinedTextField(value = qtdCaracteres ?: "6",
                                    onValueChange = {qtdNova -> qtdCaracteres = qtdNova},
                                    label = {Text(text = "Quantidade de Caracteres",
                                        fontFamily = fontSora,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        color = AzulPrincipal)},
                                    textStyle = TextStyle.Default,
                                    maxLines = 1,
                                    shape = RoundedCornerShape(20),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(60.dp),
                                    placeholder = {Text(text = "6", color = Color.LightGray, fontSize = 12.sp, fontFamily = fontSora, fontWeight = FontWeight.SemiBold)},
                                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = AzulPrincipal))
                            }
                        }
                        //Coluna pai do botão
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 16.dp)
                                .border(1.5.dp, Color.LightGray, RoundedCornerShape(5))){
                            Box(modifier = Modifier
                                .wrapContentHeight()
                                .padding(8.dp)){
                                Column(modifier = Modifier.wrapContentHeight()){
                                    Text(text = "Gere sua senha com o botão abaixo",
                                        fontFamily = fontSora, fontWeight = FontWeight.SemiBold)
                                    Row(modifier = Modifier.wrapContentHeight(),
                                    verticalAlignment = Alignment.Bottom) {
                                        ElevatedButton(
                                            onClick = {senhaFinalGerada = gerarSenha(
                                                    temEspeciais = caracteresEspeciais,
                                                    temNumeros = comNumeros,
                                                    quantidade = qtdCaracteres ?: "6"
                                                )
                                            },
                                            shape = RoundedCornerShape(20),
                                            colors = ButtonDefaults.buttonColors(Color.Blue),
                                            elevation = ButtonDefaults.buttonElevation(
                                                defaultElevation = 10.dp
                                            ),
                                            modifier = Modifier.height(70.dp)
                                        )
                                        {
                                            Row() {
                                                Text(
                                                    text = "Gerar",
                                                    fontFamily = fontSora,
                                                    fontWeight = FontWeight.ExtraBold
                                                )

                                                Spacer(modifier = Modifier.width(8.dp))
                                                Image(painter = painterResource(id = R.drawable.password),
                                                    contentDescription = null,)
                                            }
                                        }
                                        Spacer(modifier = Modifier.width(4.dp))

                                        Column(modifier = Modifier.wrapContentHeight()){
                                            Text(text = "Senha gerada: ",
                                                fontFamily = fontSora,
                                                fontWeight = FontWeight.Normal)

                                            Row(modifier = Modifier
                                                .height(50.dp)
                                                .border(
                                                    2.dp,
                                                    Color.LightGray,
                                                    RoundedCornerShape(20)
                                                )
                                                .fillMaxWidth()
                                                .padding(4.dp),
                                                horizontalArrangement = Arrangement.End)
                                            {

                                                Column(verticalArrangement = Arrangement.Center,
                                                    horizontalAlignment = Alignment.CenterHorizontally,
                                                    modifier = Modifier
                                                        .fillMaxHeight()
                                                        .fillMaxWidth()
                                                        .weight(2f)) {
                                                    Text(
                                                        text = senhaFinalGerada,
                                                        fontFamily = fontSora,
                                                        fontWeight = FontWeight.SemiBold,
                                                        color = AzulPrincipal
                                                    )
                                                }
                                                Spacer(modifier = Modifier.width(4.dp))

                                                Column(verticalArrangement = Arrangement.Center,
                                                    horizontalAlignment = Alignment.End,
                                                    modifier = Modifier
                                                        .fillMaxHeight()
                                                        .wrapContentWidth()
                                                        .weight(1f)){
                                                    Button(
                                                        modifier = Modifier.wrapContentWidth(align = Alignment.End),
                                                        onClick = { /*TODO*/ },
                                                        colors = ButtonDefaults.buttonColors(Color.White),
                                                    ) {
                                                        Image(painter = painterResource(id = R.drawable.copy),
                                                            contentDescription = null)
                                                    }
                                                }
                                            }
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }


fun gerarSenha(temEspeciais: Boolean, temNumeros: Boolean, quantidade: String): String{

    var mensagemInformativa: String
    var quantidadeTotal = quantidade.toInt()
    var senhaGerada: String = ""
    val quantidadeTotalAuxiliar = quantidadeTotal
    val especiais = "!()@#$%&*_"
    val numeros = "0123456789"
    val letras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    var quantidadeEspeciais = 0
    var quantidadeNumeros = 0
    var quantidadeLetras = 0
    if(quantidadeTotal < 6){
        mensagemInformativa = "A senha deve conter no mínimo 6 caracteres"
//        Toast.makeText(LocalContext.current,mensagemInformativa, Toast.LENGTH_LONG).show()
    }else{
        if(temEspeciais){
            //6 / 3 = 2
            quantidadeLetras = quantidadeTotal.div(3)
            //subtrai a quantidade de especiais
            quantidadeTotal -= quantidadeLetras
        }

        if(temNumeros){
            //Significa que é par
            if(quantidadeTotal / 2 == 0){
                quantidadeEspeciais = quantidadeTotal / 2
                quantidadeNumeros = quantidadeTotal / 2
            }else {
                quantidadeEspeciais = quantidadeTotal.div(2)
                quantidadeTotal -= quantidadeEspeciais
                quantidadeNumeros = quantidadeTotal
            }
        }

        if(!temNumeros && !temEspeciais){
            quantidadeLetras = quantidadeTotal
        }

        var numerosSenha = ""
        var especiaisSenha = ""
        var letrasSenha = ""
        if(temNumeros){
            for(i in 0..quantidadeNumeros){
                var posicao = Random.nextInt(0,numeros.length)
                numerosSenha += numeros[posicao]
            }
        }

        if(temEspeciais){
            for(i in 0..quantidadeEspeciais){
                var posicao = Random.nextInt(0,especiais.length)
                especiaisSenha += especiais[posicao];
            }
        }

        for(i in 0..quantidadeLetras){
            var posicao = Random.nextInt(0,letras.length)
            letrasSenha += letras[posicao];
        }

        if(!temNumeros && !temEspeciais){
            senhaGerada = letrasSenha;
        }else {
            senhaGerada = letrasSenha + especiaisSenha + numerosSenha
        }
    }

    return senhaGerada
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    GeradorSenhasTheme {
        App()
    }
}