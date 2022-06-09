package mirea.it.mypets.mainactivities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import mirea.it.mypets.R

class advicesPage : AppCompatActivity() {

    var request: Disposable? = null
    lateinit var vRecycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advices_page)



        vRecycleView = findViewById<RecyclerView>(R.id.act1_RecView)

        val o =
            createRequest("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fanimalreader.ru%2Ffeed")
                .map { Gson().fromJson(it, Feed::class.java) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

        request = o.subscribe({
            showRecView(it.items)
        }, {
            Log.e("test", "error", it)
        })
    }

    fun showRecView(feedList: ArrayList<FeedItems>) {
        vRecycleView.adapter = RecAdapter(feedList)
        vRecycleView.layoutManager = LinearLayoutManager(this)
    }

    class RecAdapter(val items: ArrayList<FeedItems>) : RecyclerView.Adapter<RecHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {

            val inflater = LayoutInflater.from(parent!!.context)
            val view = inflater.inflate(R.layout.list_item, parent, false)
            return RecHolder(view)
        }

        override fun onBindViewHolder(holder: RecHolder, position: Int) {
            val item = items[position]

            holder?.bind(item)
        }

        override fun getItemCount(): Int {
            return items.size
        }

    }

    class RecHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: FeedItems) {
            val vTitle = itemView.findViewById<TextView>(R.id.item_title)
            val vDesc = itemView.findViewById<TextView>(R.id.item_desc)
            val vThumb = itemView.findViewById<ImageView>(R.id.item_thumb)
            vTitle.text = item.title
            vDesc.text = item.description



            try {
                Picasso.get()
                    .load(item.thumbnail)
                    .resize(100, 100)
                    .into(vThumb)
            } catch (e: Exception) {
                Log.e("ImgLoadingError", e.message ?: "null")
                Picasso.get()
                    .load(R.drawable.catanddogicon)
                    .into(vThumb)
            }

            itemView.setOnClickListener() {
                val i = Intent(Intent.ACTION_VIEW)
                i.data= Uri.parse(item.link)
                vThumb.context.startActivity(i)
            }
        }

    }
}

class Feed(val items: ArrayList<FeedItems>)

class FeedItems(val title: String, val link: String, val thumbnail: String, val description: String)