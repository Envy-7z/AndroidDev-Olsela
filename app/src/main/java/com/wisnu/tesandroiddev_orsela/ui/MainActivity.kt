package com.wisnu.tesandroiddev_orsela.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TableLayout
import androidx.appcompat.widget.AppCompatTextView
import com.wisnu.tesandroiddev_orsela.R
import com.wisnu.tesandroiddev_orsela.ui.fragment.ActiveFragment
import com.wisnu.tesandroiddev_orsela.ui.fragment.AllFragment
import com.wisnu.tesandroiddev_orsela.ui.fragment.InactiveFragment
import com.wisnu.tesandroiddev_orsela.utils.UtilExtensions.openActivity
import com.wisnu.tesandroiddev_orsela.utils.VPAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var vpAdapter: VPAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initView()
    }

    private fun setupViewPager() {
        vpAdapter = VPAdapter(supportFragmentManager)
        vpAdapter!!.addFragment(AllFragment(), "")
        vpAdapter!!.addFragment(ActiveFragment(), "")
        vpAdapter!!.addFragment(InactiveFragment    (), "")

        cVp.offscreenPageLimit = 3
        cVp.adapter = vpAdapter
        tl.setupWithViewPager(cVp)
        cVp.swipeLocked = true


        setTabLayout()
    }
    private fun setTabLayout() {
        var icon :Array<Int>? = null
        var text:Array<String>? = null

        icon =
            arrayOf(
                R.drawable.ic_baseline_add_24,
                R.drawable.ic_baseline_add_24,
                R.drawable.ic_baseline_add_24
            )
        text = arrayOf("All Status", "Active" , "Inactive")



        for (i in 0 until icon!!.size) {
            val custom = LayoutInflater.from(this).inflate(R.layout.custom_tab, null)
            val tvNama = custom.findViewById<AppCompatTextView>(R.id.tv_nama)
            custom.layoutParams = TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            custom.setPadding(0, 0, 0, 0)
            tvNama.text = text!![i]
            tvNama.setTextColor(Color.parseColor("#bdbdbd"));
            tl.getTabAt(i)?.customView = custom

        }
    }
    private fun initView() {
        setupViewPager()

        addNoteFAB.setOnClickListener {
            openActivity(AddLocation::class.java)
        }
    }
}