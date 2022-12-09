package mirea.it.mypets.mainactivities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mirea.it.mypets.MYSQL.BackgroundWorkerAssync
import mirea.it.mypets.MYSQL.BackgroundWorkerAssync.*
import mirea.it.mypets.MYSQL.MYSQL_ENTITYS.Application
import mirea.it.mypets.R
import java.util.concurrent.TimeUnit

class AdminActivity : AppCompatActivity() {

    lateinit var vRecycleView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin2)

        vRecycleView = findViewById(R.id.all_apps_for_admin)
        showRecView(clientsApplications)

    }
    fun showRecView(appsList: ArrayList<Application>) {
        vRecycleView.adapter = RecAdapter(appsList)
        vRecycleView.layoutManager = LinearLayoutManager(this)
    }

    private class RecAdapter(val items: ArrayList<Application>) :
        RecyclerView.Adapter<AdminActivity.RecHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AdminActivity.RecHolder {

            val inflater = LayoutInflater.from(parent!!.context)
            val view = inflater.inflate(R.layout.list_item_for_applications, parent, false)

            val vEditButton = view.findViewById<ImageButton>(R.id.EditApplicationButton)
            val vDeleteButton = view.findViewById<ImageButton>(R.id.DeleteApplicationButton)
            vEditButton.visibility = View.INVISIBLE


            //vDeleteButton.visibility = View.INVISIBLE

            return RecHolder(view)
        }



        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: RecHolder, position: Int) {
            val item = items[position]

            holder?.bind(item)
        }


    }


    class RecHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        fun bind(item: Application) {
            val vTitle = itemView.findViewById<TextView>(R.id.item_title)
            val vDesc = itemView.findViewById<TextView>(R.id.item_desc)
            val vThumb = itemView.findViewById<ImageView>(R.id.item_thumb)
            val vDateOfReceiving = itemView.findViewById<TextView>(R.id.date_of_receiving)
            val vDateOfPerfoming = itemView.findViewById<TextView>(R.id.date_of_perfoming)



            val vDeleteButton = itemView.findViewById<ImageButton>(R.id.DeleteApplicationButton)
            vDeleteButton.setOnClickListener(this)




            ("Номер заявки: " + item.id).also { vTitle.text = it };
            ("Дата подачи: " + item.dateOfReceiving).also { vDateOfReceiving.text = it };
            ("Дата исполнения: " + item.dateOfPerfoming).also { vDateOfPerfoming.text = it };
            vDesc.text = item.aboutCurrentPet



            try {
                Picasso.get()
                    .load(R.drawable.contract)
                    .resize(100, 100)
                    .into(vThumb)
            } catch (e: Exception) {
                Log.e("ImgLoadingError", e.message ?: "null")
                Picasso.get()
                    .load(R.drawable.catanddogicon)
                    .into(vThumb)
            }

            itemView.setOnClickListener() {
                // Обработка нажатия на конкретный элемент
                // val i = Intent(Intent.ACTION_VIEW)
                // i.data= Uri.parse(item.link)
                // vThumb.context.startActivity(i)


            }
        }

        override fun onClick(p0: View?) {


                    val id = clientsApplications[absoluteAdapterPosition].id
                    val backgroundWorker = BackgroundWorkerAssync(p0?.context)

                    clientsApplications.removeAt(absoluteAdapterPosition)


                    (p0?.context as AdminActivity).vRecycleView.adapter?.notifyDataSetChanged()
                    backgroundWorker.execute(
                        DELETE_DATA_CODE,
                        id
                    )
                }

        }


    }


