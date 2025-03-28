package cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase
import cz.freego.tutorial.scaffolddynamiclabelexample.utils.emojiCodeToEscapeSequence
import java.util.regex.Pattern

@Composable
fun RichText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.bodySmall,
    normalTextColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.85f),
    boldTextColor: Color = MaterialTheme.colorScheme.onSurface,
    leadingIconSize: TextUnit = style.fontSize, // Přidána velikost ikony
    leadingIcon: (@Composable () -> Unit)? = null // Přidána ikona jako parametr
) {
    val annotatedText = buildAnnotatedString {
        if (leadingIcon != null) {
            appendInlineContent("iconId", "[icon] ") // Placeholder pro ikonu
        }

        val regex = Pattern.compile("\\*\\*(.*?)\\*\\*") // Hledá text mezi ** **
        val matcher = regex.matcher(text)

        var lastIndex = 0
        while (matcher.find()) {
            append(
                AnnotatedString(
                    text.substring(lastIndex, matcher.start()),
                    spanStyle = SpanStyle(color = normalTextColor)
                )
            )

            pushStyle(SpanStyle(fontWeight = FontWeight.Bold, color = boldTextColor))
            append(matcher.group(1) ?: "")
            pop()

            lastIndex = matcher.end()
        }

        if (lastIndex < text.length) {
            append(
                AnnotatedString(
                    text.substring(lastIndex),
                    spanStyle = SpanStyle(color = normalTextColor)
                )
            )
        }
    }

    val inlineContent = if (leadingIcon != null) {
        mapOf(
            "iconId" to InlineTextContent(
                Placeholder(
                    width = leadingIconSize,
                    height = leadingIconSize,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                leadingIcon()
            }
        )
    } else {
        emptyMap()
    }

    Text(
        modifier = modifier,
        text = annotatedText,
        style = style,
        inlineContent = inlineContent
    )
}


@PreviewLightDark
@Composable
private fun PreviewRichText() {
    PreviewShowcase {
        RichText(
            style = MaterialTheme.typography.titleMedium,
            text = " Text s leading ikonou. Toto je **tučné** slovo a **další tučné** slovo. " +
                    "Skvělé ${"1f44d".emojiCodeToEscapeSequence()} I love Compose ${"2764".emojiCodeToEscapeSequence()}",
            boldTextColor = MaterialTheme.colorScheme.secondary,
            normalTextColor = MaterialTheme.colorScheme.onSurface,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Info Icon",
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(20.dp)
                )
            },
        )
    }
}