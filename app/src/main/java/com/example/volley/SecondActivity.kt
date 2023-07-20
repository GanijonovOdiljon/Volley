package com.example.volley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.volley.R
import com.example.volley.adapter.UserAdapter
import com.example.volley.databinding.ActivitySecondBinding
import com.example.volley.model.User
import com.example.volley.util.NetworkUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SecondActivity : AppCompatActivity() {
    private lateinit var requestQuare: RequestQueue
    private val binding by
    lazy { ActivitySecondBinding.inflate(layoutInflater) }
    private val userAdapter by lazy { UserAdapter() }
    private val url  = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rv.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@SecondActivity)

        }
        val networkUtils = NetworkUtil(this)
        if (networkUtils.isNetworkConnected()){
            requestQuare = Volley.newRequestQueue(this)
            loadUsers()
        }
    }

    private fun loadUsers(){
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                binding.progressBar.isVisible = false
                val type : Type =object : TypeToken<List<User>>(){}.type
                val userList = Gson().fromJson<List<User>>(response.toString(),type)
                userAdapter.submitList(userList)
            }
        ){ error ->
            binding.progressBar.isVisible = false
            Log.d("@@@", error.toString())
        }
        requestQuare.add(jsonArrayRequest)
    }
}