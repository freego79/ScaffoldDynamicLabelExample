package cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
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
    normalTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f), // Barva běžného textu
    boldTextColor: Color = MaterialTheme.colorScheme.onSurface // Barva tučného textu
) {
    val annotatedText = buildAnnotatedString {
        val regex = Pattern.compile("\\*\\*(.*?)\\*\\*") // Hledá text mezi ** **
        val matcher = regex.matcher(text)

        var lastIndex = 0
        while (matcher.find()) {
            // Přidáme normální text před tučným úsekem
            append(
                AnnotatedString(
                    text.substring(lastIndex, matcher.start()),
                    spanStyle = SpanStyle(color = normalTextColor)
                )
            )

            // Přidáme tučný text
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold, color = boldTextColor))
            append(matcher.group(1) ?: "") // Skupina 1 obsahuje samotný text bez hvězdiček
            pop()

            lastIndex = matcher.end()
        }

        // Přidáme zbytek textu, pokud nějaký zůstane
        if (lastIndex < text.length) {
            append(
                AnnotatedString(
                    text.substring(lastIndex),
                    spanStyle = SpanStyle(color = normalTextColor)
                )
            )
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
        DynamicBoldText(
            style = MaterialTheme.typography.titleMedium,
            text = "Toto je **tučné** slovo a **další tučné** slovo.",
            boldTextColor = MaterialTheme.colorScheme.secondary,
            normalTextColor = MaterialTheme.colorScheme.onSurface,
        )
    }
}