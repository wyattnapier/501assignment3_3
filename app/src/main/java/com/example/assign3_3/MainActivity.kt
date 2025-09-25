package com.example.assign3_3

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assign3_3.ui.theme.Assign3_3Theme
import kotlinx.coroutines.launch

data class Animal(val name: String)
data class AnimalType(val name: String, val animals: List<Animal>)

val animalsByType: List<AnimalType> = listOf(
    AnimalType("Mammals", listOf(
        Animal("Lion"), Animal("Tiger"), Animal("Elephant"), Animal("Giraffe"), Animal("Zebra"),
        Animal("Kangaroo"), Animal("Panda"), Animal("Gorilla"), Animal("Chimpanzee"), Animal("Wolf"),
        Animal("Fox"), Animal("Bear"), Animal("Deer"), Animal("Rabbit"), Animal("Squirrel") // 15
    )),
    AnimalType("Birds", listOf(
        Animal("Eagle"), Animal("Sparrow"), Animal("Owl"), Animal("Penguin"), Animal("Flamingo"),
        Animal("Parrot"), Animal("Peacock"), Animal("Swan"), Animal("Hummingbird"), Animal("Ostrich"),
        Animal("Robin"), Animal("Blue Jay"), Animal("Canary") // 13
    )),
    AnimalType("Reptiles", listOf(
        Animal("Crocodile"), Animal("Alligator"), Animal("Snake"), Animal("Turtle"), Animal("Lizard"),
        Animal("Iguana"), Animal("Chameleon"), Animal("Gecko"), Animal("Tortoise") // 9
    )),
    AnimalType("Amphibians", listOf(
        Animal("Frog"), Animal("Toad"), Animal("Salamander"), Animal("Newt") // 4
    )),
    AnimalType("Fish", listOf(
        Animal("Goldfish"), Animal("Shark"), Animal("Clownfish"), Animal("Salmon"), Animal("Tuna"),
        Animal("Angelfish"), Animal("Guppy"), Animal("Piranha"), Animal("Catfish") // 9
    )),
    AnimalType("Insects", listOf(
        Animal("Ant"), Animal("Bee"), Animal("Butterfly"), Animal("Ladybug"), Animal("Grasshopper"),
        Animal("Dragonfly"), Animal("Beetle"), Animal("Moth") // 8
    ))
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assign3_3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimalListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimalListScreen(modifier: Modifier) {
    val listState: LazyListState = rememberLazyListState() // hold info about lazyColumn scroll pos
    val coroutineScope = rememberCoroutineScope() // used to scrollToItem
    val showButton: Boolean by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 10
        }
    } // boolean that is true if scrolled past 10th item

    Column(modifier = modifier.fillMaxWidth()) {

        // jump to top button
        Row(modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .padding(vertical = 30.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Animal List",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.background(Color.Black)
                )
            }
            Box(
                modifier = Modifier.weight(1f).height(40.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (showButton) {
                    SmallFloatingActionButton(
                        onClick = {
                            coroutineScope.launch {
                                listState.scrollToItem(0)
                            }
                        },
                    ) {
                        Icon(Icons.Filled.KeyboardArrowUp, "Jump to top!")
                    }
                }
            }
        }

        HorizontalDivider()

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            state = listState
        ) {
            // loop through all of the data by type
            animalsByType.forEach { animalType ->
                stickyHeader(key = animalType.name) {
                    AnimalListHeader(animalType.name)
                }
                items(
                    items = animalType.animals,
                    key = {animal -> animal.name}
                ) { animal ->
                    AnimalListItem(animal.name)
                }
            }
        }
    }
}

@Composable
fun AnimalListHeader(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
            .background(color = Color.LightGray)
            .padding(horizontal = 12.dp, vertical = 16.dp)
        )
}

@Composable
fun AnimalListItem(text: String, modifier: Modifier = Modifier) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 6.dp, end = 6.dp, top = 0.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.Cyan)
            .padding(horizontal = 12.dp, vertical = 16.dp),
        text = text
    )
}

@Preview(showBackground = true)
@Composable
fun AnimalListScreenPreview() {
    Assign3_3Theme {
        AnimalListScreen(modifier = Modifier.fillMaxWidth())
    }
}