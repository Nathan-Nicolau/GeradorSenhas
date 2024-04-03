package com.example.geradorsenhas.telas

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.geradorsenhas.R
import com.example.geradorsenhas.dao.SenhaDAO
import com.example.geradorsenhas.db.DatabaseSenhas
import com.example.geradorsenhas.ui.theme.AzulPrincipal
import com.example.geradorsenhas.ui.theme.GeradorSenhasTheme
import com.example.geradorsenhas.ui.theme.VermelhoCancelar
import com.example.geradorsenhas.ui.theme.fontSora
import com.example.geradorsenhas.vo.Senha
import kotlin.random.Random

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun GerarSenhas(){

    var contextAplicacao = LocalContext.current

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


    var showDialogSalvar by remember {
        mutableStateOf(false)
    }


    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
        ){
            Card(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)) {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(
                        text = "Gerador de Senhas",
                        fontFamily = fontSora,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                        color = AzulPrincipal,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 50.dp))
            {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)) {
                    Card(
                        Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)){
                        Row (modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 12.dp, top = 6.dp, bottom = 6.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically){
                            Text(text = "Caracteres Especiais ?",
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
                            .padding(start = 12.dp, bottom = 6.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically){
                            Text(text = "Com números ?",
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
                            .padding(start = 8.dp, bottom = 8.dp, end = 8.dp),
                            horizontalArrangement = Arrangement.Start){
                            OutlinedTextField(
                                value = qtdCaracteres ?: "",
                                onValueChange = {qtdNova -> qtdCaracteres = qtdNova},
                                label = {
                                    Text(text = "Quantidade de Caracteres",
                                    fontFamily = fontSora,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = AzulPrincipal
                                )
                                },
                                textStyle = TextStyle.Default,
                                maxLines = 1,
                                shape = RoundedCornerShape(20),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp),
                                placeholder = { Text(text = "6", color = Color.LightGray, fontSize = 12.sp, fontFamily = fontSora, fontWeight = FontWeight.SemiBold) },
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    focusedBorderColor = AzulPrincipal,
                                    unfocusedBorderColor = Color.LightGray,),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                            )
                        }
                    }
                    //Coluna pai do botão
                    Card(
                        Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(top = 16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 20.dp)){
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
                                            context = contextAplicacao,
                                            temEspeciais = caracteresEspeciais,
                                            temNumeros = comNumeros,
                                            quantidade = qtdCaracteres ?: "6"
                                        )},
                                        shape = RoundedCornerShape(20),
                                        colors = ButtonDefaults.buttonColors(Color.Blue),
                                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 20.dp),
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
                                                2.5.dp,
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
                                                IconButton(
                                                    modifier = Modifier.wrapContentWidth(align = Alignment.End),
                                                    onClick = { showDialogSalvar = true
                                                        copiarSenhaAreaTransferencia(
                                                            context = contextAplicacao,
                                                            senhaGerada = senhaFinalGerada
                                                        )}
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
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp)
                        .height(200.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(
                            modifier = Modifier.fillMaxSize(),
                            painter = painterResource(id = R.drawable.imagem_pessoa_chave),
                            contentDescription = null)
                    }
                }
            }
        }
    }
    if(showDialogSalvar) {
        showDialogSalvar = dialogSalvarSenha(
            senhaGerada = senhaFinalGerada,
            show = showDialogSalvar,
            contextAplicacao,
        )
    }

}

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun dialogSalvarSenha(senhaGerada: String, show: Boolean, context: Context): Boolean{
    var nomeNovaSenha by remember {
        mutableStateOf("")
    }

    var mostrar by remember {
        mutableStateOf(show)
    }

    var senhaDAO =  SenhaDAO(context)
    var novoIdSenha = senhaDAO.getNovoId()


    if(mostrar) {
        Dialog(onDismissRequest = { mostrar = false }) {
            Card(shape = RoundedCornerShape(5)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.7f), horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { mostrar = false }, modifier = Modifier
                                    .background(VermelhoCancelar, ShapeDefaults.ExtraLarge)
                                    .height(30.dp)
                                    .width(30.dp),
                                colors = IconButtonDefaults.iconButtonColors(contentColor = Color.White)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.close_white),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = nomeNovaSenha,
                            onValueChange = { senhaNovaGerada -> nomeNovaSenha = senhaNovaGerada },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp),
                            shape = RoundedCornerShape(20),
                            label = {
                                Text(
                                    text = "Nome da Senha",
                                    fontFamily = fontSora,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 14.sp,
                                    color = AzulPrincipal
                                )
                            },
                            placeholder = {
                                Text(
                                    text = "6",
                                    color = Color.LightGray,
                                    fontSize = 12.sp,
                                    fontFamily = fontSora,
                                    fontWeight = FontWeight.SemiBold
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = AzulPrincipal),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = senhaGerada,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = fontSora, color = Color.Black,
                        modifier = Modifier
                            .border(2.dp, Color.LightGray, RoundedCornerShape(20))
                            .fillMaxWidth()
                            .padding(8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            ElevatedButton(
                                onClick = { mostrar = false},
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(VermelhoCancelar),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                                modifier = Modifier
                                    .height(50.dp)
                                    .wrapContentWidth()
                            ) {
                                Text(
                                    text = "Cancelar",
                                    fontFamily = fontSora,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.End
                        ) {
                            ElevatedButton(
                                onClick = { mostrar = false
                                    toastSenhaSalva(context)
                                          senhaDAO.insertNovaSenha(Senha(novoIdSenha,nomeNovaSenha,senhaGerada))},
                                shape = RoundedCornerShape(20),
                                colors = ButtonDefaults.buttonColors(AzulPrincipal),
                                elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                                modifier = Modifier
                                    .height(50.dp)
                                    .wrapContentWidth()
                            ) {
                                Text(
                                    text = "Salvar",
                                    fontFamily = fontSora,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Image(
                                    painter = painterResource(id = R.drawable.salvar_senha),
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    return mostrar
}

fun toastSenhaSalva(context: Context){
    Toast.makeText(context,"Senha salva com sucesso!", Toast.LENGTH_SHORT).show()
}

fun gerarSenha(context: Context, temEspeciais: Boolean, temNumeros: Boolean, quantidade: String): String{

    var mensagemInformativa: String
    var quantidadeTotal = quantidade.toInt()
    var senhaGerada = ""
    var quantidadeTotalAuxiliar = quantidadeTotal
    val especiais = "!()@#$%&*_"
    val numeros = "0123456789"
    val letras = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"

    var quantidadeEspeciais = 0
    var quantidadeNumeros = 0
    var quantidadeLetras = 0

    if(quantidadeTotal < 6){
        mensagemInformativa = "A senha deve conter no mínimo 6 caracteres"
        Toast.makeText(context,mensagemInformativa, Toast.LENGTH_LONG).show()
    } else if(quantidadeTotal > 20){
        mensagemInformativa = "A senha deve conter no máximo 20 caracteres"
        Toast.makeText(context,mensagemInformativa, Toast.LENGTH_LONG).show()
    }

    else{

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


fun copiarSenhaAreaTransferencia(context: Context, senhaGerada: String){
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Senha gerada", senhaGerada)
    clipboardManager.setPrimaryClip(clip)
    Toast.makeText(context,"Senha copiada para a Área de Transferência", Toast.LENGTH_SHORT).show()
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
@Preview
fun GerarSenhasPreview(){
    GeradorSenhasTheme {
        GerarSenhas()
    }
}