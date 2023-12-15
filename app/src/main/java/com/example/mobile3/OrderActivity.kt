package com.example.mobile3

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.SeekBar
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class OrderActivity : AppCompatActivity() {
    private lateinit var orderList:TableLayout
    private lateinit var seek: SeekBar
    private lateinit var total:TextView
    private lateinit var ok:Button
    @SuppressLint("SetTextI18n", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        orderList=findViewById(R.id.orders_list)
        seek=findViewById(R.id.seek)
        total=findViewById(R.id.total)
        ok=findViewById(R.id.returnRes)
        val arguments:Bundle = intent.extras!!
        if(arguments!=null)
        {
            val listOrders=arguments.getString("orders")!!
            val typeToken = object : TypeToken<List<Detail>>() {}.type
            val authors = Gson().fromJson<List<Detail>>(listOrders, typeToken)
            var sum:Double=0.0
            for (i in authors){
                var tableRow:TableRow=TableRow(this)

                val textName:TextView= TextView(this)
                textName.textSize = 30.0f
                textName.gravity= Gravity.CENTER
                textName.text=i.name
                tableRow.addView(textName,TableRow.
                LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,0.5f))

                val textCount=TextView(this)
                textCount.textSize = 30.0f
                textCount.gravity=Gravity.CENTER
                textCount.text=i.count.toString()
                tableRow.addView(textCount,TableRow.
                LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,0.5f))

                val textPrice=TextView(this)
                textPrice.textSize = 30.0f
                textPrice.gravity=Gravity.CENTER
                textPrice.text=i.price.toString()
                tableRow.addView(textPrice,TableRow.
                LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT,0.5f))

                orderList.addView(tableRow)
                sum+=i.count*i.price
            }
            seek.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
                @SuppressLint("SetTextI18n")
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {

                    total.text="Итоговая цена пк:"+(sum+sum*p1/100).toString()
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {

                }

                override fun onStopTrackingTouch(p0: SeekBar?) {

                }

            })
            ok.setOnClickListener {
                val data = Intent();
                data.putExtra("total",total.text.toString())
                setResult(RESULT_OK,data)
                finish()
            }
            total.text= "Цена компьютера:$sum"
        }

    }
}