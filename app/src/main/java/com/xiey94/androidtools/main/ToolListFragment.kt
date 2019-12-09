package com.xiey94.androidtools.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.xiey94.androidtools.MainActivity
import com.xiey94.androidtools.R
import kotlinx.android.synthetic.main.fragment_tool_list.*

/**
 * Create by xiey on 2019/12/9.
 * Tool List
 * sort total
 */
class ToolListFragment : Fragment() {

    var adapter: ToolListAdapter? = null


    //region static
    companion object StaticField {
        private const val PARAMS1 = "params1"
        private const val PARAMS2 = "params2"

        fun newInstance(params1: String, params2: String): ToolListFragment {
            val fragment = ToolListFragment()
            val bundle = Bundle()
            bundle.putString(PARAMS1, params1)
            bundle.putString(PARAMS2, params2)
            fragment.arguments = bundle
            return fragment
        }
    }
    //endregion

    //region life cycle
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tool_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initListener()
        initData()

    }
    //endregion

    //region private method
    private fun initView() {
        adapter = ToolListAdapter(initListData(), activity as MainActivity)
        recycler_view.let {
            it.layoutManager = LinearLayoutManager(activity)
            it.adapter = adapter
        }
        adapter?.setListener { view, position ->
            Toast.makeText(activity, "/-/", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initListener() {}

    private fun initData() {}

    private fun initListData(): MutableList<String> {
        val initListData = mutableListOf<String>()
        initListData.add("gui tools")
        return initListData
    }
    //endregion


}