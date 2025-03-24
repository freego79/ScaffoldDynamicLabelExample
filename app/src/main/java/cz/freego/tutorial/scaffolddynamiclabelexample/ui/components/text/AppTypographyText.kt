package cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase

@Composable
fun TitleLargeText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        text = text,
    )
}

@Composable
fun TitleMediumText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.titleMedium,
        text = text,
    )
}

@Composable
fun BodySmallText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall,
        text = text,
    )
}

@Composable
fun ButtonText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        text = text,
    )
}

@PreviewLightDark
@Composable
private fun TextsPreview() {
    PreviewShowcase {
        Column {
            TitleLargeText("TitleLargeText")
            TitleMediumText("TitleMediumText")
            BodySmallText("BodySmallText")
            ButtonText("ButtonText")
        }
    }
}
