package com.example.geradorsenhas.telas

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.os.Build
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
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
import androidx.compose.ui.text.font.FontWeight
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

@RequiresApi(Build.VERSION_CODES.P)
@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SenhasSalvas(){

    var senhaPesquisada by remember {
        mutableStateOf("Senha")
    }

    val context = LocalContext.current
    var senhaDAO = SenhaDAO(context)
    var senhas = senhaDAO.getTodasSenhas()

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {
        if(senhas.isNotEmpty()) {
            Spacer(modifier = Modifier.height(4.dp))
            Column(modifier = Modifier.fillMaxSize()) {
                senhas.forEach {
                    Spacer(modifier = Modifier.height(2.dp))
                    CardSenhaSalva(it)
                }
            }
        }else{
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Sem nenhuma senha salva", fontFamily = fontSora, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun CardSenhaSalva(senha: Senha){

    var mostrarDialogExclusao by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val senhaDAO = SenhaDAO(context)

    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .padding(4.dp)
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
                        text = "Nome da Senha " + senha.nomeSenha,
                        fontFamily = fontSora,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row() {
                        Text(
                            text = senha.valorSenha,
                            fontFamily = fontSora,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .border(1.5.dp, Color.LightGray, RoundedCornerShape(15))
                                .padding(10.dp)
                                .fillMaxWidth()
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .wrapContentWidth()
                        .fillMaxHeight()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ElevatedButton(
                        onClick = { mostrarDialogExclusao = true },
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                        modifier = Modifier
                            .width(75.dp)
                            .height(75.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
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

    if(mostrarDialogExclusao){
        mostrarDialogExclusao =
            DialogExcluirSenha(
            senhaDAO = senhaDAO,
            senha = senha ,
            mostrar = mostrarDialogExclusao)
    }
}
@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun DialogExcluirSenha(senhaDAO: SenhaDAO, senha: Senha, mostrar: Boolean): Boolean{

    var mostrarDialog by remember {
        mutableStateOf(mostrar)
    }

    if(mostrarDialog) {
        Dialog(
            onDismissRequest = { mostrarDialog = false }) {
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
                            .weight(1f)
                            .padding(8.dp)
                            .height(50.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        ElevatedButton(
                            onClick = {
                                mostrarDialog = false
                            },
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
                            .weight(1f)
                            .padding(8.dp)
                            .height(50.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        ElevatedButton(
                            onClick = {senhaDAO.deleteSenhaById(senha.idSenha)
                                      mostrarDialog  = false},
                            shape = RoundedCornerShape(20),
                            colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                            modifier = Modifier.fillMaxHeight()
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
    return mostrarDialog
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
@Preview
fun PreviewSenhasSalvas(){
    GeradorSenhasTheme {
//        CardSenhaSalva("Facebook","*******")
        SenhasSalvas()
//        DialogExcluirSenha()
    }
}