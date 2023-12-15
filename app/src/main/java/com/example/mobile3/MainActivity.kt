package com.example.mobile3

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    private lateinit var TableName:EditText
    private lateinit var Orders:LinearLayout
    private lateinit var fab:FloatingActionButton
    private lateinit var button: Button
    private lateinit var views:MutableList<View>
    private lateinit var totalPrice:TextView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TableName=findViewById(R.id.tableNumber)
        Orders=findViewById(R.id.orders)
        fab=findViewById(R.id.fabOk)
        button=findViewById(R.id.OKorder)
        totalPrice=findViewById(R.id.totalprice)
        views= mutableListOf()
        fab.setOnClickListener{
            var view:View = layoutInflater.inflate(R.layout.order_item,null)
            var del:Button=view.findViewById(R.id.del)
            del.setOnClickListener{
                (view.parent as LinearLayout).removeView(view)
                views.remove(view)
            }
            views.add(view)
            Orders.addView(view)
        }
        button.setOnClickListener{
            var details:MutableList<Detail> = mutableListOf()
            for(i in views)
            {
                val name = (i.findViewById(R.id.name) as EditText).text.toString()
                val count = (i.findViewById(R.id.count) as EditText).text.toString().toInt()
                val price = (i.findViewById(R.id.price) as EditText).text.toString().toDouble()
                var detail = Detail(name, count, price)
                details.add(detail)
            }
            var gson = Gson()
            val ordersGson=gson.toJson(details)
            val intent:Intent=Intent(applicationContext,OrderActivity::class.java)
            intent.putExtra("table_number",TableName.text.toString())
            intent.putExtra("orders",ordersGson)
            startForResult.launch(intent)
        }
    }
    val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            val totalRes= intent?.getStringExtra("total")
            totalPrice.text=totalRes
        }
    }
}