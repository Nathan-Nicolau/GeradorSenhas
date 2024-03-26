package com.example.geradorsenhas.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.geradorsenhas.vo.Senha
import java.sql.SQLException

@RequiresApi(Build.VERSION_CODES.P)
class DatabaseSenhas(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, DATABASE_VERSION, SQLiteDatabase.OpenParams.Builder().build()) {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val sqlCriacao = "CREATE TABLE IF NOT EXISTS $TABLE_NAME " +
                    "( $ID INTEGER PRIMARY KEY ," +
                    "$NOME TEXT, " +
                    "$VALOR TEXT )"

            db?.execSQL(sqlCriacao)
        }catch(e: SQLException){

        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        const val DATABASE_NAME = "database_senhas.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "SENHA"
        const val ID = "ID_SENHA"
        const val NOME = "NOME_SENHA"
        const val VALOR = "VALOR_SENHA"

    }

    fun insertNovaSenha(senha: Senha) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(ID, senha.idSenha)
            put(NOME, senha.nomeSenha)
            put(VALOR, senha.valorSenha)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }


    fun getTodasSenhas(): List<Senha> {
        val senhas = mutableListOf<Senha>()
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ID))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow(NOME))
            val valor = cursor.getString(cursor.getColumnIndexOrThrow(VALOR))
            val senha = Senha(id,nome,valor)
            senhas.add(senha)
        }
        cursor.close()
        return senhas
    }

    fun getUltimoIdSenha(): Int {
        var novoId: Int = 0
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT MAX($ID) FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            novoId = cursor.getInt(0) + 1
        }
        cursor.close()
        return novoId
    }
}