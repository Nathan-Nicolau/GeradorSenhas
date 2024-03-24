package com.example.geradorsenhas.navegacao

import com.example.geradorsenhas.R
import com.example.geradorsenhas.enums.TelasEnum

data class ItemNavegacao(
    var idIcone: Int,
    var nomeIcone: String,
    var rotaTela: String
)

//Lista imutável
val listaIconesNavegacao = listOf(
    ItemNavegacao(R.drawable.gerar_senhas,"Gerar Senhas",TelasEnum.GerarSenhas.name),
    ItemNavegacao(R.drawable.passwords,"Senhas Salvas",TelasEnum.SenhasSalvas.name)
)


