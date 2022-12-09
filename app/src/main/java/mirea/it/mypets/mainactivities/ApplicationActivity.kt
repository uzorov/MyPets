package mirea.it.mypets.mainactivities


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import mirea.it.mypets.MYSQL.BackgroundWorkerAssync
import mirea.it.mypets.MYSQL.BackgroundWorkerAssync.*
import mirea.it.mypets.MYSQL.MYSQL_ENTITYS.Application
import mirea.it.mypets.R
import mirea.it.mypets.fragments.CreateApplicationFormFragment


class ApplicationActivity : AppCompatActivity(), CreateApplicationFormFragment.HideFragment,
    BackgroundWorkerAssync.NotifyThatNewItemWasAdded {


    lateinit var vRecycleView: RecyclerView

    override fun onCreate(_savedInstanceState: Bundle?) {
        super.onCreate(_savedInstanceState)
        setContentView(R.layout.activity_application)

        if (_savedInstanceState == null
            && supportFragmentManager.findFragmentByTag("appCreating") == null
        ) {

            val appFormInstance = CreateApplicationFormFragment.newInstance()
            supportFragmentManager
                .beginTransaction()
                .add(
                    R.id.root_layout,
                    appFormInstance,
                    "appCreating"
                )
                .commit()

            supportFragmentManager.beginTransaction()
                .hide(appFormInstance)
                .commit()

        }


        // assigning ID of the toolbar to a variable
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        toolbar.title = ""

        val createNewAppButton: ImageButton = findViewById(R.id.createNewAppButton)

        createNewAppButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                showApplicationForm()
            }
        })

        // using toolbar as ActionBar
        setSupportActionBar(toolbar)

        vRecycleView = findViewById<RecyclerView>(R.id.RecyclerViewApplication)

        showRecView(clientsApplications);
    }

    fun showRecView(appsList: ArrayList<Application>) {
        vRecycleView.adapter = RecAdapter(appsList)
        vRecycleView.layoutManager = LinearLayoutManager(this)
    }

    private class RecAdapter(val items: ArrayList<Application>) :
        RecyclerView.Adapter<ApplicationActivity.RecHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ApplicationActivity.RecHolder {

            val inflater = LayoutInflater.from(parent!!.context)
            val view = inflater.inflate(R.layout.list_item_for_applications, parent, false)
            return ApplicationActivity.RecHolder(view)
        }

        override fun onBindViewHolder(holder: ApplicationActivity.RecHolder, position: Int) {
            val item = items[position]

            holder?.bind(item)
        }

        override fun getItemCount(): Int {
            return items.size
        }


    }


    class RecHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        fun bind(item: Application) {
            val vTitle = itemView.findViewById<TextView>(R.id.item_title)
            val vDesc = itemView.findViewById<TextView>(R.id.item_desc)
            val vThumb = itemView.findViewById<ImageView>(R.id.item_thumb)
            val vDateOfReceiving = itemView.findViewById<TextView>(R.id.date_of_receiving)
            val vDateOfPerfoming = itemView.findViewById<TextView>(R.id.date_of_perfoming)

            val vEditButton = itemView.findViewById<ImageButton>(R.id.EditApplicationButton)
            val vDeleteButton = itemView.findViewById<ImageButton>(R.id.DeleteApplicationButton)
            vEditButton.setOnClickListener(this)
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

        override fun onClick(view: View?) {
            if (view?.id as Int == R.id.DeleteApplicationButton) {
                val id = clientsApplications[absoluteAdapterPosition].id
                val backgroundWorker = BackgroundWorkerAssync(view.context)

                    clientsApplications.removeAt(absoluteAdapterPosition)


                (view.context as ApplicationActivity).notifyThatItemAdded()
                backgroundWorker.execute(
                    DELETE_DATA_CODE,
                    id
                )

                } else if (view.id == R.id.EditApplicationButton) {
                    val activity = view.context as ApplicationActivity
                    activity.showApplicationForm()
                    val pet = pets.get(adapterPosition)
                    activity.changePetName(pet.name)
                    activity.changePetType(pet.type)
                    activity.changeTitleOfTheForm("Изменить заявку")
                    activity.changeFormSendingLogic(UPDATE_DATA_CODE)
                    activity.setApplicationId(clientsApplications[absoluteAdapterPosition].id)
                }
            }




    }   override fun hideApplicationForm() {
            supportFragmentManager.findFragmentByTag("appCreating")?.let {
                supportFragmentManager
                    .beginTransaction()
                    .hide(
                        it
                    )
                    .commit()
            }
        }

        override fun showApplicationForm() {
            supportFragmentManager.findFragmentByTag("appCreating")?.let {
                supportFragmentManager
                    .beginTransaction()
                    .show(
                        it
                    )
                    .commit()
            }
        }

     override fun changeTitleOfTheForm(title: String) {
            val fragment: CreateApplicationFormFragment =
                supportFragmentManager.findFragmentByTag("appCreating") as CreateApplicationFormFragment
            fragment.setFormTitle(title)
        }

        override fun changePetName(name: String) {
            val fragment: CreateApplicationFormFragment =
                supportFragmentManager.findFragmentByTag("appCreating") as CreateApplicationFormFragment
            fragment.setPetName(name)
        }

    override fun changePetAge(age: String) {

    }

    override fun changePetType(type: String) {
            val fragment: CreateApplicationFormFragment =
                supportFragmentManager.findFragmentByTag("appCreating") as CreateApplicationFormFragment
            fragment.setPetType(type)
        }

        override fun changeFormSendingLogic(codeOfOperation: String) {
            val fragment: CreateApplicationFormFragment =
                supportFragmentManager.findFragmentByTag("appCreating") as CreateApplicationFormFragment
            fragment.setFormCode(codeOfOperation)

        }

        override fun setApplicationId(idApplication: String) {
            val fragment: CreateApplicationFormFragment =
                supportFragmentManager.findFragmentByTag("appCreating") as CreateApplicationFormFragment
            fragment.setApplicationId(idApplication)
        }

        override fun notifyThatItemAdded() {
            vRecycleView.adapter?.notifyDataSetChanged()
            vRecycleView.smoothScrollToPosition(clientsApplications.size)

        }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        vRecycleView.adapter!!.notifyDataSetChanged()
        showRecView(clientsApplications);
    }


    }

