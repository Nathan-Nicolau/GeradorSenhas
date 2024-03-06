
package com.example.geradorsenhas

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.geradorsenhas.ui.theme.GeradorSenhasTheme
import com.example.geradorsenhas.ui.theme.corAzulPrincipal
import com.example.geradorsenhas.ui.theme.fontSora

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
    Scaffold(
        containerColor = Color.White,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
        ){
            Text(text = "Gerador de Senhas", fontFamily = fontSora, fontWeight = FontWeight.ExtraBold,
                color = Color.White,
                modifier = Modifier.background(Color.Blue, RoundedCornerShape(16))
                    .padding(8.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 50.dp)){
                    Column(modifier = Modifier.fillMaxWidth()
                        .border(1.5.dp, Color.LightGray, RoundedCornerShape(15))
                        .padding(8.dp)) {
                        Row (modifier = Modifier.fillMaxWidth()){
                            Text(text = "Caracteres Especiais ?",
                                modifier = Modifier.padding(end = 10.dp),
                                fontFamily = fontSora, fontWeight = FontWeight.SemiBold,
                                color = Color.Black)
                            Checkbox(checked = true, onCheckedChange = validarEscolhaQtdCaracters(), modifier = Modifier
                                .background(Color.White, RoundedCornerShape(15))
                                .border(1.5.dp, Color.LightGray, RoundedCornerShape(15))
                            )
                        }
                         Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)){
                             Text(text = "Com números ?",
                                 modifier = Modifier.padding(end = 10.dp),
                                 fontFamily = fontSora, fontWeight = FontWeight.SemiBold,
                                 color = Color.Black)
                             Checkbox(checked = true, onCheckedChange = validarEscolhaQtdCaracters(), modifier = Modifier
                                 .background(Color.White, RoundedCornerShape(15))
                                 .border(1.5.dp, Color.LightGray, RoundedCornerShape(15))
                             )
                         }

                    }
            }
        }
    }
}

fun validarEscolhaQtdCaracters(): ((Boolean) -> Unit)? {
    return null
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    GeradorSenhasTheme {
        App()
    }
}