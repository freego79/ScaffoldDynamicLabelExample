package cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.PreviewShowcase
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.BodyMediumText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.ButtonText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.components.text.TitleLargeText
import cz.freego.tutorial.scaffolddynamiclabelexample.ui.screen.main.TopBannerUIState

@Composable
fun TopBanner(
    modifier: Modifier = Modifier,
    topBannerUIState: TopBannerUIState,
) {
    Column(
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        topBannerUIState.title?.let {
            TitleLargeText(text = it)
            Spacer(Modifier.height(16.dp))
        }

        topBannerUIState.message?.let {
            BodyMediumText(text = it)
            Spacer(Modifier.height(16.dp))
        }

        Row {
            Box(modifier = Modifier.weight(1f)) {
                topBannerUIState.action1Label?.let {
                    Button(onClick = { topBannerUIState.onAction1Click?.invoke() }
                    ) { ButtonText(it) }
                }
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.TopEnd
            ) {
                topBannerUIState.action2Label?.let {
                    Button(onClick = { topBannerUIState.onAction2Click?.invoke() }
                    ) { ButtonText(it) }
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Preview
@Composable
private fun TopBannerPreview() {
    PreviewShowcase {
        TopBanner(
            topBannerUIState = TopBannerUIState(
                title = "Ups, něco se pokazilo!",
                message = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.",
                action1Label = "Akce 1",
                action2Label = "Akce 2",
            )
        )
    }
}