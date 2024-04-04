package com.example.geradorsenhas.telas

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.geradorsenhas.R
import com.example.geradorsenhas.dao.SenhaDAO
import com.example.geradorsenhas.ui.theme.AzulPrincipal
import com.example.geradorsenhas.ui.theme.GeradorSenhasTheme
import com.example.geradorsenhas.ui.theme.VermelhoCancelar
import com.example.geradorsenhas.ui.theme.fontSora
import com.example.geradorsenhas.vo.Senha

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SenhasSalvas(){

    val context = LocalContext.current
    var senhaDAO = SenhaDAO(context)
    var senhas by remember {
        mutableStateOf(senhaDAO.getTodasSenhas())
    }

    var temSenhas by remember {
        mutableStateOf(senhas.isNotEmpty())
    }


    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        if(temSenhas) {
            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White)) {
                senhas.forEach {
                    temSenhas = CardSenhaSalva(it, senhas).isNotEmpty()
                    temSenhas = senhaDAO.getTodasSenhas().isNotEmpty()
                }
            }
        }else{
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Sem nenhuma senha salva", fontFamily = fontSora, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun CardSenhaSalva(senha: Senha, senhas: MutableList<Senha>): List<Senha>{

    val contexto = LocalContext.current

    var visivelExclusao by remember {
        mutableStateOf(false)
    }

    var visivel by remember {
        mutableStateOf(true)
    }

    var senhasAtualizadas by remember {
        mutableStateOf(senhas)
    }

    var senhaVisivel by remember {
        mutableStateOf(false)
    }

    val iconeVisivel by remember {
        mutableIntStateOf(R.drawable.view_on_keys)
    }
    val iconeInvisivel by remember {
        mutableIntStateOf(R.drawable.view_off_keys)
    }

    val valorSenhaVisivel by remember {
        mutableStateOf(senha.valorSenha)
    }

    val valorSenhaMask by remember {
        var tamanho = senha.valorSenha.length
        var valorMask = "*"
        for(i in 0 until tamanho){
            valorMask += "*"
        }
        mutableStateOf(valorMask)
    }

    var valorSenha by remember {
        mutableStateOf(valorSenhaMask)
    }

    val context = LocalContext.current
    val senhaDAO = SenhaDAO(context)

    if(visivel) {
        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(4.dp)
                .background(Color.White)
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 15.dp
                )
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(3.5f)
                    ) {
                        Text(
                            text = "Nome: " + senha.nomeSenha,
                            fontFamily = fontSora,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.border(1.5.dp, Color.LightGray, RoundedCornerShape(15))) {
                            Text(
                                text = valorSenha,
                                fontFamily = fontSora,
                                fontWeight = FontWeight.ExtraBold,
                                modifier = Modifier
                                    .padding(12.dp)
                                    .fillMaxSize()
                                    .weight(1f)
                            )
                            IconButton(onClick = {
                                if(senhaVisivel){
                                    senhaVisivel = false
                                    valorSenha = valorSenhaMask
                                }else {
                                    senhaVisivel = true
                                    valorSenha = valorSenhaVisivel
                                }
                            }) {
                                if(senhaVisivel) {
                                    Icon(
                                        painter = painterResource(iconeVisivel),
                                        contentDescription = null
                                    )
                                }else {
                                    Icon(
                                        painter = painterResource(iconeInvisivel),
                                        contentDescription = null
                                    )
                                }
                            }
                            IconButton(onClick = { copiarSenha(contexto,valorSenhaVisivel) }) {
                                Icon(painter = painterResource(id = R.drawable.copy), contentDescription =  null)
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .width(85.dp)
                            .fillMaxHeight()
                            .padding(top = 10.dp, bottom = 10.dp, end = 6.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        ElevatedButton(
                            onClick = { visivelExclusao = true },
                            shape = RoundedCornerShape(25),
                            colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                            modifier = Modifier
                                .width(70.dp),
                            elevation = ButtonDefaults.buttonElevation(defaultElevation = 25.dp, pressedElevation = 10.dp),
                        ) {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Icon(
                                    painter = painterResource(id = R.drawable.delete_white_24dp),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize()
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(2.dp))
            }
        }
    }
    if(visivelExclusao) {
//        senhasAtualizadas.remove(senha)
        senhaDAO.deleteSenhaById(senha.idSenha)
        visivel = false
    }
    return senhasAtualizadas
}

fun copiarSenha(context: Context, senhaGerada: String){
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText("Senha gerada", senhaGerada)
    clipboardManager.setPrimaryClip(clip)
    Toast.makeText(context,"Senha copiada para a Área de Transferência", Toast.LENGTH_SHORT).show()
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun DialogExcluirSenha(mostrar: Boolean): Boolean{

    var mostrarDialog by remember {
        mutableStateOf(mostrar)
    }

    var excluir by remember {
        mutableStateOf(false)
    }

    if(mostrarDialog) {
        Dialog(
            onDismissRequest = {
            mostrarDialog = false }) {
            Card(
                modifier = Modifier
                    .wrapContentSize(),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(15)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {

                    Text(
                        text = "Deseja realmente excluir a Senha ?",
                        fontFamily = fontSora, fontWeight = FontWeight.Medium
                    )
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(8.dp)
                            .height(50.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        ElevatedButton(
                            onClick = { mostrarDialog = false
                                      excluir =  false},
                            shape = RoundedCornerShape(20),
                            colors = ButtonDefaults.buttonColors(containerColor = VermelhoCancelar),
                            modifier = Modifier.fillMaxHeight()
                        ) {
                            Text(
                                text = "Cancelar",
                                fontFamily = fontSora,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .weight(0.5f)
                            .padding(8.dp)
                            .height(50.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ElevatedButton(
                            onClick = {
                                mostrarDialog = false
                                excluir = true },
                            shape = RoundedCornerShape(20),
                            colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                            modifier = Modifier
                                .fillMaxHeight()
                        ) {
                            Text(
                                text = "Excluir",
                                fontFamily = fontSora,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.delete_white_24dp),
                                contentDescription = null
                            )
                        }
                    }
                }
            }
        }
    }
    return excluir
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
@Preview
fun PreviewSenhasSalvas(){
    GeradorSenhasTheme {
        CardSenhaSalva(Senha(1,"Facebook","******"), mutableListOf())
//        SenhasSalvas()
    }
}