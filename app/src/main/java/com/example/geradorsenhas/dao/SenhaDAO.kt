package com.example.geradorsenhas.dao

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.geradorsenhas.db.DatabaseSenhas
import com.example.geradorsenhas.interfaces.SenhaInterfaceDAO
import com.example.geradorsenhas.vo.Senha

class SenhaDAO(var database: DatabaseSenhas): SenhaInterfaceDAO {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun getTodasSenhas(): List<Senha> {
        return database.getTodasSenhas();
    }

    override fun getSenhaByNome(nome: String): List<Senha> {
        TODO("Not yet implemented")
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun insertNovaSenha(senhaNova: Senha) {
        database.insertNovaSenha(senhaNova)
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun getNovoId(): Int {
        return database.getUltimoIdSenha()
    }
}