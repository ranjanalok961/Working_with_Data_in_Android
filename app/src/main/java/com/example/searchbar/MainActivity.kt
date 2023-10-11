package com.example.searchbar

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.searchbar.ui.theme.SearchBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SearchBarTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var list = remember { mutableStateListOf<Item>() }
                    list = fetchAndDisplayData()
                    App(list)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(list: SnapshotStateList<Item>) {

    val select :List<Item> = list
    val selectitem : List<Item> = select.filter{
        if (DataManager.datatext.isEmpty()) {
            true
        } else {
            it.name.contains(DataManager.datatext)
        }
    }
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        TopAppBar (
            title = {
                Text(
                    "Food App",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.LightGray, RectangleShape),
            navigationIcon = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Localized description"
                    )
                }
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(
                containerColor = Color.LightGray
            )
        )
        var text by remember { mutableStateOf("") }
        DataManager.datatext = text
        OutlinedTextField(

            value = text,
            onValueChange = { text = it },
            label = { Text("Search") },
            leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .paddingFromBaseline(10.dp)
        )

        Column (
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            LazyVerticalGrid(columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(top = 5.dp)
                ){
                items(selectitem){data->
                    val name = data.name
                    Item(data)
                }
            }
        }
    }
}



@Composable
fun Item(data: Item) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = data.name ,
                style = TextStyle(
                 fontSize = 24.sp
                )
                )
            Text(text = "${data.cost}",
                style = TextStyle(
                    fontSize = 24.sp
                )
                )
        }
    }
    Divider(
        modifier = Modifier.fillMaxWidth(),
        thickness = 2.dp,
        color = Color.LightGray
    )
}

//        Divider(
//            modifier = Modifier.fillMaxSize(0.8f).paddingFromBaseline(10.dp , 10.dp),
//            thickness = 2.dp,
//            color = Color.Black
//        )
