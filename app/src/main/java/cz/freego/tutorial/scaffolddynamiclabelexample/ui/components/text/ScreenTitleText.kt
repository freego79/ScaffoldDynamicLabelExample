package cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.PreviewLightDark
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase

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

@PreviewLightDark
@Composable
private fun ScreenTitleTextPreview() {
    PreviewShowcase {
        ScreenTitleText("Screen title text")
    }
}