package com.example.volley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.*
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.volley.R
import com.example.volley.adapter.GithubAdaptetr
import com.example.volley.databinding.ActivityGithubBinding
import com.example.volley.model.GithubUser
import com.example.volley.util.NetworkUtil
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import org.json.JSONArray
import java.lang.ref.ReferenceQueue
import java.lang.reflect.Type

class GithubActivity : AppCompatActivity() {
    private val binding by lazy { ActivityGithubBinding.inflate(layoutInflater) }
    private val githubAdaptetr by lazy { GithubAdaptetr() }
    private lateinit var requirestQueue: RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.rv.apply {
            adapter = githubAdaptetr
            layoutManager = LinearLayoutManager(this@GithubActivity)
        }
        val networkUtils = NetworkUtil(this)
        if (networkUtils.isNetworkConnected()){
            requirestQueue = Volley.newRequestQueue(this)
            loadUsers()
        }
    }
    private fun loadUsers(){
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET,
            "https://api.github.com/users",
            null,
            object :Response.Listener<JSONArray>{
                override fun onResponse(response: JSONArray?) {
                    binding.progressbar.isVisible = false
                    val type : Type = object : TypeToken<List<GithubUser>>(){}.type
                    val userList = Gson().fromJson<List<GithubUser>>(response.toString(),type)
                    githubAdaptetr.submitList(userList)
                }
            }, object : Response.ErrorListener{

                override fun onErrorResponse(error: VolleyError?) {
                 Log.d("@@@",error.toString())
                }
            }
        )
        requirestQueue.add(jsonArrayRequest)
    }
}