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
fun fetchCat(): SnapshotStateList<String> {
    val list = remember { mutableStateListOf<String>() }
    val requestQueue: RequestQueue = Volley.newRequestQueue(LocalContext.current)
    val apiurl = "https://api.jsonbin.io/v3/b/651a4d8054105e766fbc821a?meta=false"
    val request = remember(apiurl) {
        JsonObjectRequest(Request.Method.GET, apiurl, null, { result ->
            val menuArray = result.getJSONArray("menu")
            for (i in 0 until menuArray.length()) {
                val categoryObject = menuArray.getJSONObject(i)
                val menuCat : String = categoryObject.getString("category")
                list.add(menuCat)
            }
            list.addAll(list)
            Log.d("DataCAt",list.toString())
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