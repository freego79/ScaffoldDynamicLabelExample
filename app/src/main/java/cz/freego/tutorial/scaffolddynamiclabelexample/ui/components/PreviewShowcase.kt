package cz.freego.tutorial.scaffolddynamiclabelexample.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.theme.ScaffoldDynamicLabelExampleTheme

@Composable
fun PreviewShowcase(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    ScaffoldDynamicLabelExampleTheme {
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.background
        ) {
            content()
        }
    }
}