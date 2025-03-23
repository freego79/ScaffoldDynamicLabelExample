package cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.theme.ScaffoldDynamicLabelExampleTheme

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

@Preview
@Composable
private fun TextsPreview() {
    ScaffoldDynamicLabelExampleTheme {
        Surface {
            Column {
                TitleLargeText("TitleLargeText")
                TitleMediumText("TitleMediumText")
                BodySmallText("BodySmallText")
                ButtonText("ButtonText")
            }
        }
    }
}
