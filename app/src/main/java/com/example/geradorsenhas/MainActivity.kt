
package com.example.geradorsenhas

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getSystemService
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

    val context = LocalContext.current

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

    var tagSenha by remember {
        mutableStateOf("")
    }

    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxSize(),
        bottomBar = {
            BottomAppBar(containerColor = Color.LightGray,
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth()
                ) {
                Row(horizontalArrangement = Arrangement.Center) {
                    IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                        Icon(
                            painter = painterResource(id = R.drawable.gerar_senhas),
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = { /*TODO*/ },modifier = Modifier.weight(1f)) {
                        Icon(
                            painter = painterResource(id = R.drawable.passwords),
                            contentDescription = null
                        )
                    }
                }
            }
        }
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
                                .padding(start = 6.dp),
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
                                .padding(start = 6.dp),
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
                                OutlinedTextField(value = qtdCaracteres ?: "",
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
                                    colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = AzulPrincipal),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                            }
                        }
                        //Coluna pai do botão
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentHeight()
                                .padding(top = 16.dp)
                                .border(1.5.dp, Color.LightGray, RoundedCornerShape(8))){
                            Box(modifier = Modifier
                                .wrapContentHeight()
                                .padding(start = 8.dp, end = 8.dp, top = 8.dp, bottom = 10.dp)){
                                Column(modifier = Modifier.wrapContentHeight()){
                                    Text(text = "Gere sua senha com o botão abaixo",
                                        fontFamily = fontSora, fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.fillMaxWidth())

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
                                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                                            modifier = Modifier
                                                .height(70.dp)
                                                .wrapContentWidth()
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
                                        Spacer(modifier = Modifier.width(6.dp))

                                        Column(modifier = Modifier.wrapContentHeight()){
                                            Text(text = "Senha gerada ",
                                                fontFamily = fontSora,
                                                fontWeight = FontWeight.Normal,
                                                modifier = Modifier.fillMaxWidth())

                                            Spacer(modifier = Modifier.height(8.dp))

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
                                                        ){
                                                    Button(
                                                        modifier = Modifier.wrapContentWidth(align = Alignment.End),
                                                        onClick = { copiarSenhaAreaTransferencia(
                                                            context = context,
                                                            senhaGerada = senhaFinalGerada
                                                        )},
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
    var quantidadeTotalAuxiliar = quantidadeTotal
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

        if(temEspeciais && temNumeros){
            quantidadeLetras = quantidadeTotal.div(3)
            quantidadeTotalAuxiliar -= quantidadeLetras
            quantidadeEspeciais = quantidadeTotalAuxiliar.div(2)
            quantidadeNumeros = quantidadeTotalAuxiliar.div(2)
            var totalCaracteres = quantidadeLetras + quantidadeEspeciais + quantidadeNumeros;
            if(totalCaracteres < quantidadeTotal){
                var diferenca = quantidadeTotal - totalCaracteres
                quantidadeLetras += diferenca
            }
        }
        else if(temEspeciais && !temNumeros){
            quantidadeLetras = quantidadeTotal.div(2)
            quantidadeTotalAuxiliar -= quantidadeLetras
            quantidadeEspeciais = quantidadeTotalAuxiliar.div(2)
            var totalCaracteres = quantidadeLetras + quantidadeEspeciais
            if(totalCaracteres < quantidadeTotal){
                var diferenca = quantidadeTotal - totalCaracteres
                quantidadeLetras += diferenca
            }
        }
        else if(!temEspeciais && temNumeros){
            quantidadeLetras = quantidadeTotal.div(2)
            quantidadeTotalAuxiliar -= quantidadeLetras
            quantidadeNumeros = quantidadeTotalAuxiliar.div(2)
            var totalCaracteres = quantidadeLetras + quantidadeNumeros
            if(totalCaracteres < quantidadeTotal){
                var diferenca = quantidadeTotal - totalCaracteres
                quantidadeLetras += diferenca
            }
        }
        else {
            quantidadeLetras = quantidadeTotal
        }


        var numerosSenha = ""
        var especiaisSenha = ""
        var letrasSenha = ""
        if(temNumeros){
            for(i in 0 until quantidadeNumeros){
                var posicao = Random.nextInt(0,numeros.length)
                numerosSenha += numeros[posicao]
            }
        }

        if(temEspeciais){
            for(i in 0 until quantidadeEspeciais){
                var posicao = Random.nextInt(0,especiais.length)
                especiaisSenha += especiais[posicao];
            }
        }

        for(i in 0 until quantidadeLetras){
            var posicao = Random.nextInt(0,letras.length)
            letrasSenha += letras[posicao];
        }

        if(!temNumeros && !temEspeciais){
            senhaGerada = letrasSenha
        }
        else if(temNumeros && !temEspeciais){
            senhaGerada = letrasSenha + numerosSenha
        }
        else if(!temNumeros){
            senhaGerada = letrasSenha + especiaisSenha
        }
        else {
            senhaGerada = letrasSenha + especiaisSenha + numerosSenha
        }
    }

    return senhaGerada
}


fun copiarSenhaAreaTransferencia(context: Context , senhaGerada: String){
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Senha gerada", senhaGerada)
    clipboardManager.setPrimaryClip(clip)

}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    GeradorSenhasTheme {
        App()
    }
}