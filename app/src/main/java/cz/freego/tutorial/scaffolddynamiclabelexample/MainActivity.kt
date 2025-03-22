package cz.freego.tutorial.scaffolddynamiclabelexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.MainScreen
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.theme.ScaffoldDynamicLabelExampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ScaffoldDynamicLabelExampleTheme {
                MainScreen()
            }
        }
    }
}
