package com.example.flixterapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.example.flixterapp.R.id.progress
import com.example.flixterapp.R.id.list
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

// --------------------------------//
// CHANGE THIS TO BE YOUR API KEY  //
// --------------------------------//
private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"


class PopularMovieFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_most_popular_movie, container, false)
        val progressBar = view.findViewById<View>(progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(list) as RecyclerView
        updateAdapter(progressBar, recyclerView)
        return view
    }

    fun createJson() = Json {
        isLenient = true
        ignoreUnknownKeys = true
        useAlternativeNames = false
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api-key"] = API_KEY

        // Using the client, perform the HTTP request
        client[
        "https://api.themoviedb.org/3/movie/popular?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
        params,
        object : JsonHttpResponseHandler() {
            /*
             * The onSuccess function gets called when
             * HTTP response status is "200 OK"
             */
            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                json: JsonHttpResponseHandler.JSON
            ) {
                // The wait for a response is over
                progressBar.hide()

                val resultsJSON : JSONArray = json.jsonObject.getJSONArray("results") as JSONArray
                val moviesRawJSON : String = resultsJSON.toString()

                val gson = Gson()

                val arrayMovieType = object : TypeToken<List<Movies>>() {}.type
                val models : List<Movies> = gson.fromJson(moviesRawJSON,arrayMovieType)
                recyclerView.adapter = PopularMovieRecyclerViewAdapter(models, this@PopularMovieFragment)

                // Look for this in Logcat:
                Log.d("PopularMoviesFragment", "response successful")
            }

            /*
             * The onFailure function gets called when
             * HTTP response status is "4XX" (eg. 401, 403, 404)
             */
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                // The wait for a response is over
                progressBar.hide()

                // If the error is not null, log it!
                t?.message?.let {
                    Log.e("PopularMoviesFragment", errorResponse)
                }
            }
        }]

    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: Movies) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}