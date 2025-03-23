package cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun ScreenTitleText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        text = text,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}