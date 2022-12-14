package com.hfad.shoppinglist.presentation

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.hfad.shoppinglist.R
import com.hfad.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    lateinit var llShopList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llShopList = findViewById(R.id.ll_shop_list)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            showList(it)

        }


    }

    private fun showList(list: List<ShopItem>) {
        llShopList.removeAllViews()
        for (shopItem in list) {
            val layoutId = if (shopItem.enable) {
                R.layout.item_shop_enable
            } else {
                R.layout.item_shop_disenable
            }
            val view = layoutInflater.inflate(layoutId, llShopList, false)
            val tvName = findViewById<TextView>(R.id.tv_name)
            val tvCount = findViewById<TextView>(R.id.tv_count)
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()
            view.setOnLongClickListener {
                viewModel.changeEnableState(shopItem)
                true
            }
            llShopList.addView(view)

        }
    }
}