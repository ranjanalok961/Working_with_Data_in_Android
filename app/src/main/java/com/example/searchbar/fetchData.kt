package com.example.searchbar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.platform.LocalContext
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

@Composable
fun fetchAndDisplayData(): SnapshotStateList<Item> {
    val list = remember { mutableStateListOf<Item>() }

    val requestQueue: RequestQueue = Volley.newRequestQueue(LocalContext.current)
    val apiurl = "https://api.jsonbin.io/v3/b/651a4d8054105e766fbc821a?meta=false"
    val request = remember(apiurl) {
        JsonObjectRequest(Request.Method.GET, apiurl, null, { result ->
            val menuArray = result.getJSONArray("menu")
            for (i in 0 until menuArray.length()) {
                val categoryObject = menuArray.getJSONObject(i)
                val itemsArray = categoryObject.getJSONArray("items")

                // Iterate through itemsArray and add each item to the list
                for (j in 0 until itemsArray.length()) {
                    val itemObject = itemsArray.getJSONObject(j)
                    val itemName = itemObject.getString("name")
                    val itemCost = itemObject.getInt("cost_inr")
                    val item = Item(itemName, itemCost)
                    list.add(item)
                }

            }
            list.addAll(list)
            Log.d("Data1", list.toString())
        }, { err ->

        })
    }
    DisposableEffect(Unit) {
        requestQueue.add(request)
        onDispose {
            // Clean up or cancel any ongoing requests if needed
            requestQueue.cancelAll(this)
        }
    }
    LaunchedEffect(list) {}
    return list
}