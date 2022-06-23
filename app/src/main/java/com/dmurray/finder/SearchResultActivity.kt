package com.dmurray.finder

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.text.TextUtils.indexOf
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class SearchResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val searchTerm = intent.getStringExtra("searchTerm")

        val callback = object : Callback<GitHubSearchResult> {
            override fun onResponse(call: Call<GitHubSearchResult>, response: Response<GitHubSearchResult>?) {
                val searchResult = response?.body()
                if (searchResult != null) {
                    for (repo in searchResult!!.items) {
                        println(repo.full_name)
                    }
                    val listView  = findViewById<ListView>(R.id.repoListView)
                    listView.setOnItemClickListener { adapterView, view, i, l ->
                        val selectedRepo = searchResult.items[i]
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(selectedRepo.html_url))
                        startActivity(intent)
                    }





                    val adapter = RepoAdapter(this@SearchResultActivity, android.R.layout.simple_list_item_1, searchResult!!.items)

//                        val selectedRepo = searchResult.items[]
//                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(selectedRepo.html_url))
//                        startActivity(intent)

//                    val adapter = RepoAdapter(this@SearchResultActivity,android.R.layout.simple_list_item_1, searchResult!!.items)
                    listView.adapter = adapter


                }
            }

            override fun onFailure(call: Call<GitHubSearchResult>?, t: Throwable?) {
                println("It Ain't")
            }

        }
        val retriever = GutHubRetriever()
        retriever.searchRepos(callback, searchTerm)
         }
    }



class RepoAdapter(context: Context?, resource: Int, objects: List<Repo>?) : ArrayAdapter<Repo>(
    context!!, resource, objects!!
) {
    override fun getCount(): Int {
        return super.getCount()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflator = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val repoView = inflator.inflate(android.R.layout.simple_list_item_1,parent, false) as TextView

        val repo = getItem(position)
        if (repo != null) {
            repoView.text = repo.full_name
        }

        return repoView
    }
}








