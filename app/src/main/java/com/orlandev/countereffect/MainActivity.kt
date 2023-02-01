package com.orlandev.countereffect

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.orlandev.countereffect.ui.theme.CounterEffectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CounterEffectTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {

                    var currentValue by remember {
                        mutableStateOf(0)
                    }

                    Column {

                        Text(text = "Valor Actual: $currentValue")

                        CounterEffect(maxValue = 10) { value ->
                            currentValue = value
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CounterEffect(maxValue: Int = 20, cantSelected: (Int) -> Unit) {
    var counter by remember {
        mutableStateOf(0)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Row(
            modifier = Modifier.padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(modifier = Modifier.size(60.dp), enabled = counter > 0, onClick = {
                counter--
                cantSelected(counter)
            }) {
                Text(text = "-")
            }

            AnimatedContent(
                modifier = Modifier.weight(4f),
                targetState = counter,
                transitionSpec = {
                    if (targetState > initialState) {
                        slideInVertically(initialOffsetY = { height -> height }) + fadeIn() with slideOutVertically(
                            targetOffsetY = { height -> -height }) + fadeOut()
                    } else {
                        slideInVertically(initialOffsetY = { height -> -height }) + fadeIn() with slideOutVertically(
                            targetOffsetY = { height -> height }) + fadeOut()
                    }.using(SizeTransform(clip = false))
                }) {
                Text(
                    text = "$counter",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineMedium
                )
            }


            Button(modifier = Modifier.size(60.dp), enabled = counter < maxValue, onClick = {
                counter++
                cantSelected(counter)
            }) {
                Text(text = "+")
            }

        }
    }

}
