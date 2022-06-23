package com.dmurray.finder

import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GitHubService {
    @GET("search/repositories?")
    fun searchRepos(@Query("q") searchTerm: String?) : retrofit2.Call<GitHubSearchResult>

//    @GET("users/{user}/repos")
//    fun userRepos(@Path("user") username: String): Call<List<Repo>>
}

class GitHubSearchResult(val items: List<Repo>)
class Repo(val full_name: String, val owner: GitHubUser, val html_url: String)
class GitHubUser(val avatar_url: String)

class GutHubRetriever {
    val service: GitHubService
    init {
        val retrofit = Retrofit.Builder().baseUrl("https://api.github.com/").addConverterFactory(GsonConverterFactory.create()).build()
        service = retrofit.create(GitHubService::class.java)
    }

    fun searchRepos(callback: Callback<GitHubSearchResult>, searchTerm: String?){
        var search = searchTerm
        if (search == ""){
        search = "eff!"
        }
//        val q = "sfhwo"
        val call = service.searchRepos(search)
        call.enqueue(callback)
    }
//    fun userRepos(callback: Callback<List<Repo>>, username: String) {
//        val call = service.userRepos(username)
//        call.enqueue(callback)
//
//    }
}