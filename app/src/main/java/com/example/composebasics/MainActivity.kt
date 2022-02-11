package com.example.composebasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composebasics.ui.theme.ComposeBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyComposableContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    ComposeBasicsTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            content()
        }

    }
}

@Composable
fun MyComposableContent(
    names: List<BaseItem> = listOf(
        BaseItem.First("This is First"),
        BaseItem.Second("This is Second"),
        BaseItem.First("This is First"),
        BaseItem.Second("This is Second"),
        BaseItem.First("This is First"),
        BaseItem.Second("This is Second"),
        BaseItem.First("This is First"),
    )
) {
    var counterState by remember {
        mutableStateOf(0)
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        NamesList(names = names, modifier = Modifier.weight(1f))

        Counter(counter = counterState,
            updateCounter = { newCount ->
                counterState = newCount
            }
        )
    }
    if (counterState > 5) {
        Text(text = "You have Crossed Five")
    }
}

sealed class BaseItem {
    data class First(val data: String) : BaseItem()
    data class Second(val data: String) : BaseItem()
}

@Composable
fun NamesList(names: List<BaseItem>, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(items = names) {
            when (it) {
                is BaseItem.First -> {
                    Greeting(name = it.data)
                    Divider()
                }
                is BaseItem.Second -> {
                    Greeting(name = it.data)
                }
            }
        }
    }
}

@Composable
fun Counter(counter: Int, updateCounter: (Int) -> Unit) {
    Button(onClick = { updateCounter(counter + 1) }) {
        Text(text = "I have been clicked $counter times")
    }
}

@Composable
fun Greeting(name: String) {
    var isSelected by remember {
        mutableStateOf(false)
    }

    val targetColor by animateColorAsState(
        targetValue = if (isSelected) MaterialTheme.colors.primary else Color.Transparent,
        animationSpec = tween(4000)
    )
    Surface(color = targetColor) {
        Text(text = "Hello $name!",
            modifier = Modifier
                .clickable { isSelected = !isSelected }
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyComposableContent()
    }
}