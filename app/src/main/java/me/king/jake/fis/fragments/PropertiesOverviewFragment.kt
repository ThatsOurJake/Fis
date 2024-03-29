package me.king.jake.fis.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.button.MaterialButton
import me.king.jake.fis.Api
import me.king.jake.fis.InventoryOverviewStore
import me.king.jake.fis.R
import me.king.jake.fis.views.PropertiesInput

class PropertiesOverviewFragment: BaseOverviewFragment() {
    private lateinit var propertiesInput: PropertiesInput

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_properties_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        propertiesInput = view.findViewById(R.id.item_properties_wrapper)
        propertiesInput.populateFields(inventoryItem!!)

        view.findViewById<MaterialButton>(R.id.btn_overview_submit).setOnClickListener {
            submit()
        }
    }

    private fun submit() {
        if (!propertiesInput.validate()) {
            return
        }

        closeKeyboard()

        Api.postInventoryItem(inventoryItem!!.barcode, propertiesInput.getItemOutput()) {
            inventoryItemError -> run {
                if (inventoryItemError != null) {
                    Log.e(this.javaClass.canonicalName, inventoryItemError)
                    return@run
                }

                Handler(context!!.mainLooper).post {
                    InventoryOverviewStore.setCurrentState(InventoryOverviewStore.States.FINISHED)
                }
            }
        }
    }
}