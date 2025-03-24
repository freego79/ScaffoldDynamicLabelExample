package cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase
import java.util.regex.Pattern

@Composable
fun DynamicBoldText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodySmall,
) {
    val annotatedText = buildAnnotatedString {
        val regex = Pattern.compile("\\*\\*(.*?)\\*\\*") // Hledá text mezi ** **
        val matcher = regex.matcher(text)

        var lastIndex = 0
        while (matcher.find()) {
            // Přidáme normální text před tučným úsekem
            append(text.substring(lastIndex, matcher.start()))

            // Přidáme tučný text
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
            append(matcher.group(1) ?: "") // Skupina 1 obsahuje samotný text bez hvězdiček
            pop()

            lastIndex = matcher.end()
        }

        // Přidáme zbytek textu, pokud nějaký zůstane
        if (lastIndex < text.length) {
            append(text.substring(lastIndex))
        }
    }

    Text(
        modifier = modifier,
        text = annotatedText,
        style = style,
    )
}

@PreviewLightDark
@Composable
private fun PreviewBoldText() {
    PreviewShowcase {
        DynamicBoldText("Toto je **tučné** slovo a **další tučné** slovo.")
    }
}