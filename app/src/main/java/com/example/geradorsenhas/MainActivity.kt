
package com.example.geradorsenhas

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import com.example.geradorsenhas.db.DatabaseSenhas

class MainActivity : ComponentActivity() {

    private lateinit var database: DatabaseSenhas
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inicializarDatabaseSenhas()
        setContent {
            AppNavigation()
        }
    }


    @RequiresApi(Build.VERSION_CODES.P)
    fun inicializarDatabaseSenhas(){
        database = DatabaseSenhas(this)
    }
}
