package com.example.geradorsenhas.telas

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geradorsenhas.R
import com.example.geradorsenhas.dao.SenhaDAO
import com.example.geradorsenhas.db.DatabaseSenhas
import com.example.geradorsenhas.ui.theme.AzulPrincipal
import com.example.geradorsenhas.ui.theme.GeradorSenhasTheme
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

    var senhas :List<Senha> = senhaDAO.getTodasSenhas()

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = senhaPesquisada,
                onValueChange = { nomeSenhaInformada -> { senhaPesquisada = nomeSenhaInformada } },
                placeholder = {
                    Text(
                        text = "Digite a senha para pesquisar...",
                        fontFamily = fontSora, fontWeight = FontWeight.SemiBold
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = AzulPrincipal),
                shape = RoundedCornerShape(15)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Column(modifier = Modifier.fillMaxSize()){
            senhas.forEach {
                Spacer(modifier = Modifier.height(2.dp))
                CardSenhaSalva(nomeSenha = it.nomeSenha, valorSenha = it.valorSenha)
            }
        }
    }
}

@Composable
fun CardSenhaSalva(nomeSenha: String, valorSenha: String){
    Box(modifier = Modifier
        .height(100.dp)
        .fillMaxWidth()){
        Card(colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp, pressedElevation = 15.dp )){
            Row(modifier = Modifier.fillMaxSize()){
                Column(modifier = Modifier
                    .padding(8.dp)
                    .weight(3.5f)){
                    Text(text = "Nome da Senha " + nomeSenha, fontFamily = fontSora, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(){
                        Text(text = valorSenha,
                            fontFamily = fontSora,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .border(1.5.dp, Color.LightGray, RoundedCornerShape(15))
                                .padding(10.dp)
                                .fillMaxWidth())
                    }
                }
                Column(modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .padding(12.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    ElevatedButton(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(25),
                        colors = ButtonDefaults.buttonColors(containerColor = AzulPrincipal),
                        modifier = Modifier
                            .width(75.dp)
                            .height(75.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 10.dp),
                    ) {
                        Column(modifier = Modifier.fillMaxSize()){
                            Icon(painter = painterResource(id = R.drawable.delete_white_24dp),
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize())
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
        }

    }
}

@Composable
@Preview
fun PreviewSenhasSalvas(){
    GeradorSenhasTheme {
//        SenhasSalvas()
        CardSenhaSalva("Facebook","*******")
    }
}