package mirea.it.mypets.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import mirea.it.mypets.MYSQL.BackgroundWorkerAssync
import mirea.it.mypets.MYSQL.BackgroundWorkerAssync.SEND_NEW_DATA_CODE
import mirea.it.mypets.MYSQL.BackgroundWorkerAssync.UPDATE_DATA_CODE
import mirea.it.mypets.R
import mirea.it.mypets.mainactivities.ApplicationActivity
import org.w3c.dom.Text
import java.lang.IllegalStateException

class CreateApplicationFormFragment : Fragment(), View.OnClickListener {

    companion object {

        fun newInstance():
                CreateApplicationFormFragment {
            return CreateApplicationFormFragment()
        }
    }

    private var formCode = SEND_NEW_DATA_CODE

    var idApplication : String = "null"

    lateinit var vName: EditText
    lateinit var vAge: EditText
    lateinit var vType: EditText
    lateinit var vTitle: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        saveInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.create_or_delete_application_form, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sendButton = view.findViewById<View>(R.id.buttonSend) as Button
        val cancelButton = view.findViewById<View>(R.id.buttonCancel) as Button
        sendButton.setOnClickListener(this)
        cancelButton.setOnClickListener(this)

        vName = activity?.findViewById<View>(R.id.pets_name) as EditText
        vType = activity?.findViewById<View>(R.id.pets_type) as EditText
        vTitle = activity?.findViewById<View>(R.id.titleOfTheApplicationForm) as TextView
        vAge = activity?.findViewById<View>(R.id.pets_age) as EditText
    }

    fun setApplicationId(idApplication: String){
        this.idApplication = idApplication
    }
    fun setPetAge(Age: String){
        vAge.setText(Age)
    }

    fun setPetName(name: String){
        vName.setText(name)
    }

    fun setFormCode(codeOfOperation: String){
        formCode = codeOfOperation
    }

    fun setPetType(type: String){
        vType.setText(type)
    }
    fun setFormTitle(title: String){
        vTitle.text = title
    }
    fun getPetName(): String {
        return vName.text.toString()
    }
    fun getPetType(): String {
        return vType.text.toString()
    }
    fun getFormTitle(): String {
        return vTitle.text.toString()
    }
    fun getFormCode(): String {
        return formCode
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonCancel -> {
                val parentActivity = activity as ApplicationActivity
                parentActivity.hideApplicationForm()

            }
            R.id.buttonSend -> run {
                //val vTitle = view.findViewById<TextView>(R.id.titleOfTheApplicationForm)
                val backgroundWorker = BackgroundWorkerAssync(activity as ApplicationActivity)

                val parentActivity = activity as ApplicationActivity
                parentActivity.hideApplicationForm()

                if (formCode.equals(SEND_NEW_DATA_CODE))
                {
                backgroundWorker.execute(
                    formCode,
                    vName.text.toString(),
                    vType.text.toString(),
                    vAge.text.toString()
                )}
                else
                {
                    backgroundWorker.execute(
                        formCode,
                        vName.text.toString(),
                        vType.text.toString(),
                        idApplication.toString()
                    )
                }
                idApplication = "null"
                vName.setText("")
                vType.setText("")
                vTitle.text = "Создать новую заявку"
                formCode = SEND_NEW_DATA_CODE
            }
            else -> throw IllegalStateException("Unexpected value: " + view.id)
        }
    }

    interface HideFragment {
        fun hideApplicationForm()
        fun showApplicationForm()

        fun changeTitleOfTheForm(title : String)
        fun changePetName(name : String)
        fun changePetAge(age : String)
        fun changePetType(type : String)
        fun changeFormSendingLogic(codeOfOperation : String)
        fun setApplicationId(idApplication : String)
    }

}

