package com.example.geradorsenhas.interfaces

import com.example.geradorsenhas.vo.Senha

interface SenhaInterfaceDAO {
    fun getTodasSenhas(): List<Senha>
    fun getSenhaByNome(nome: String) : List<Senha>
    fun insertNovaSenha(senhaNova: Senha)
    fun getNovoId(): Int
    fun deleteSenhaById(idSenha: Int): Boolean
}