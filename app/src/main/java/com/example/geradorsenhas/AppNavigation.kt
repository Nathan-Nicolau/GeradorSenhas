package com.example.geradorsenhas

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.geradorsenhas.enums.TelasEnum
import com.example.geradorsenhas.navegacao.listaIconesNavegacao
import com.example.geradorsenhas.telas.GerarSenhas
import com.example.geradorsenhas.telas.SenhasSalvas
import com.example.geradorsenhas.ui.theme.AzulPrincipal
import com.example.geradorsenhas.ui.theme.CinzaContainer
import com.example.geradorsenhas.ui.theme.GeradorSenhasTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation( ){
    val navcontroller = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBar(containerColor = Color.White) {
                val navBackEntry by navcontroller.currentBackStackEntryAsState()
                val currentDestination = navBackEntry?.destination
                listaIconesNavegacao.forEach { itemNavegacao ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any{ it.route == itemNavegacao.rotaTela} == true
                        , onClick = {
                                    navcontroller.navigate(itemNavegacao.rotaTela){
                                        popUpTo(navcontroller.graph.findStartDestination().id){
                                            saveState = true
                                        }
                                        launchSingleTop =  true
                                        restoreState =  true

                                    }
                        },
                        icon = {
                               Icon(painter = painterResource(id = itemNavegacao.idIcone), contentDescription = null )
                        },
                        label = {
                            Text(text = itemNavegacao.nomeIcone)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            unselectedIconColor = Color.Black,
                            selectedIconColor = AzulPrincipal,
                            unselectedTextColor = Color.Black,
                            selectedTextColor = AzulPrincipal,
                            indicatorColor = CinzaContainer))
                }
            }
        }) {
        NavHost(navController = navcontroller, startDestination = TelasEnum.GerarSenhas.name){
            composable(route = TelasEnum.GerarSenhas.name){
                GerarSenhas()
            }
            composable(route = TelasEnum.SenhasSalvas.name){
                SenhasSalvas()
            }

        }
    }
}

fun inicializarBancoDeDados(){

}
@Composable
@Preview
fun previewBarNavigation(){
    GeradorSenhasTheme {
//        AppNavigation()
    }
}