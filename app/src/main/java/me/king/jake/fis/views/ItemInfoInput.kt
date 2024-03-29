package me.king.jake.fis.views

import android.content.Context
import android.text.InputType
import android.util.AttributeSet
import android.widget.LinearLayout
import com.google.android.material.textfield.TextInputEditText
import me.king.jake.fis.R
import me.king.jake.fis.Utils.disableTextView
import me.king.jake.fis.Utils.enableTextView
import me.king.jake.fis.models.InventoryDTO
import me.king.jake.fis.models.ItemDTO

class ItemInfoInput(context: Context, attrs: AttributeSet?) : LinearLayout(context, attrs) {
    private lateinit var barcodeInput: TextInputEditText
    private lateinit var titleInput: TextInputEditText
    private lateinit var genericTitleInput: TextInputEditText

    var editMode: Boolean = true
        set(value) {
            field = value
            update()
        }

    init {
        inflate(context, R.layout.input_item_info, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        barcodeInput = findViewById(R.id.input_barcode)
        titleInput = findViewById(R.id.input_title)
        genericTitleInput = findViewById(R.id.input_generic_title)
    }

    fun populateFields(inventoryItem: InventoryDTO) {
        barcodeInput.apply {
            setText(inventoryItem.barcode)
            inputType = InputType.TYPE_NULL
        }

        titleInput.apply {
            val title = inventoryItem.item?.title ?: return

            setText(title)
        }

        genericTitleInput.apply {
            val genericTitle = inventoryItem.item?.genericTitle ?: return
            setText(genericTitle)
        }
    }

    fun validate() : Boolean {
        if (titleInput.text.isNullOrBlank()) {
            titleInput.error = "Title is required"
            return false
        }

        return true
    }

    fun getItemOutput(): ItemDTO {
        return ItemDTO().apply {
            title = titleInput.text.toString()
            genericTitle = genericTitleInput.text.toString()
        }
    }

    private fun update() {
        if (editMode) {
            enableTextView(titleInput)
            enableTextView(genericTitleInput)
        } else {
            disableTextView(titleInput)
            disableTextView(genericTitleInput)
        }
    }
}