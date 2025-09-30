package com.example.assign3_3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assign3_3.ui.theme.Assign3_3Theme
import kotlinx.coroutines.launch

data class Contact(val name: String, val phone: String)
data class ContactGroup(val name: String, val contacts: List<Contact>)

val contactsByGroup: List<ContactGroup> = listOf(
    ContactGroup("Family", listOf(
        Contact("Alice Johnson", "123-456-0001"),
        Contact("Bob Johnson", "123-456-0002"),
        Contact("Charlie Johnson", "123-456-0003"),
        Contact("Diana Johnson", "123-456-0004"),
        Contact("Ethan Johnson", "123-456-0005"),
        Contact("Fiona Johnson", "123-456-0006"),
        Contact("George Johnson", "123-456-0007"),
        Contact("Hannah Johnson", "123-456-0008"),
        Contact("Ian Johnson", "123-456-0009"),
        Contact("Julia Johnson", "123-456-0010")
    )),
    ContactGroup("Friends", listOf(
        Contact("Kevin Smith", "123-456-0011"),
        Contact("Laura Smith", "123-456-0012"),
        Contact("Michael Smith", "123-456-0013"),
        Contact("Nina Smith", "123-456-0014"),
        Contact("Oscar Smith", "123-456-0015"),
        Contact("Paula Smith", "123-456-0016"),
        Contact("Quentin Smith", "123-456-0017"),
        Contact("Rachel Smith", "123-456-0018"),
        Contact("Steve Smith", "123-456-0019"),
        Contact("Tina Smith", "123-456-0020"),
        Contact("Uma Smith", "123-456-0021"),
        Contact("Victor Smith", "123-456-0022"),
        Contact("Wendy Smith", "123-456-0023"),
        Contact("Xander Smith", "123-456-0024"),
        Contact("Yvonne Smith", "123-456-0025"),
        Contact("Zach Smith", "123-456-0026")
    )),
    ContactGroup("Work", listOf(
        Contact("Alan Lee", "123-456-0027"),
        Contact("Betty Lee", "123-456-0028"),
        Contact("Carl Lee", "123-456-0029"),
        Contact("Dana Lee", "123-456-0030"),
        Contact("Eli Lee", "123-456-0031"),
        Contact("Faith Lee", "123-456-0032"),
        Contact("Gary Lee", "123-456-0033"),
        Contact("Helen Lee", "123-456-0034"),
        Contact("Isaac Lee", "123-456-0035"),
        Contact("Jackie Lee", "123-456-0036"),
        Contact("Kara Lee", "123-456-0037"),
        Contact("Leo Lee", "123-456-0038"),
        Contact("Mona Lee", "123-456-0039"),
        Contact("Nate Lee", "123-456-0040"),
        Contact("Olivia Lee", "123-456-0041")
    )),
    ContactGroup("Others", listOf(
        Contact("Peter Parker", "123-456-0042"),
        Contact("Clark Kent", "123-456-0043"),
        Contact("Bruce Wayne", "123-456-0044"),
        Contact("Diana Prince", "123-456-0045"),
        Contact("Barry Allen", "123-456-0046"),
        Contact("Arthur Curry", "123-456-0047"),
        Contact("Hal Jordan", "123-456-0048"),
        Contact("Victor Stone", "123-456-0049"),
        Contact("Billy Batson", "123-456-0050"),
        Contact("Kara Danvers", "123-456-0051")
    ))
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Assign3_3Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ContactListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactListScreen(modifier: Modifier) {
    val listState: LazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val showButton: Boolean by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 10 }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(vertical = 30.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Contact List",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.background(Color.Black)
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(40.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (showButton) {
                    SmallFloatingActionButton(
                        onClick = {
                            coroutineScope.launch { listState.animateScrollToItem(0) }
                        }
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
            contactsByGroup.forEach { group ->
                stickyHeader(key = group.name) {
                    ContactListHeader(group.name)
                }
                items(
                    items = group.contacts,
                    key = { contact -> contact.name }
                ) { contact ->
                    ContactListItem(contact)
                }
            }
        }
    }
}

@Composable
fun ContactListHeader(text: String) {
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
fun ContactListItem(contact: Contact) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp, start = 6.dp, end = 6.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Color.Cyan)
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Text(text = contact.name, fontSize = 16.sp)
        Text(text = contact.phone, fontSize = 14.sp, color = Color.DarkGray)
    }
}

@Preview(showBackground = true)
@Composable
fun ContactListScreenPreview() {
    Assign3_3Theme {
        ContactListScreen(modifier = Modifier.fillMaxWidth())
    }
}