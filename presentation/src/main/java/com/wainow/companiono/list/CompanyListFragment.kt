package com.wainow.companiono.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wainow.companiono.R
import com.wainow.data.repository.MyCompanyData
import com.wainow.domain.entities.CompanyItem

@Suppress("UNCHECKED_CAST")
class CompanyListFragment : Fragment() {

    private var columnCount = 1
    lateinit var pd: ProgressBar
    lateinit var recyclerView: RecyclerView
    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity?)?.supportActionBar?.title = "Companiono"
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        pd = view.findViewById(R.id.pd_list)
        recyclerView = view.findViewById(R.id.list)
        textView = view.findViewById(R.id.text_list)

        MyCompanyData.getItemList({
            Log.d(TAG, "CompanyFragment: onCreateView: list: firstItem: ${it?.get(0)}")
            setRecycler(recyclerView, it)
            pd.visibility = View.GONE
            textView.visibility = View.GONE
        }, {
            pd.visibility = View.GONE
            textView.visibility = View.VISIBLE
            textView.text = it

        })
        return view
    }

    @Suppress("UNCHECKED_CAST")
    fun setRecycler(view : View, listCompany: List<CompanyItem?>?){
        if (view is RecyclerView) {
            Log.d(TAG, "CompanyFragment: onCreateView: view is RecyclerView")
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = MyCompanyRecyclerViewAdapter((listCompany as List<CompanyItem>?))
            }
        }
    }

    companion object {
        const val ARG_COLUMN_COUNT = "column-count"

        @JvmStatic
        fun newInstance(columnCount: Int) =
            CompanyListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }

        const val TAG: String = "debugLogs"
    }
}