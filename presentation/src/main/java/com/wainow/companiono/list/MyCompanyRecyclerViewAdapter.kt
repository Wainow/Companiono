package com.wainow.companiono.list

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wainow.companiono.R
import com.wainow.companiono.description.CompanyDescriptionFragment
import com.wainow.companiono.description.CompanyDescriptionViewModel
import com.wainow.data.repository.MyCompanyData
import com.wainow.domain.entities.CompanyItem


class MyCompanyRecyclerViewAdapter(
    private val values: List<CompanyItem>?
) : RecyclerView.Adapter<MyCompanyRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values?.get(position)
        holder.idView.text = "#${item?.id}"
        holder.nameView.text = item?.name
        Glide
            .with(holder.itemView.context)
            .load(siteUrl + item?.img)
            .error(R.drawable.ic_group)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            val fragment = CompanyDescriptionFragment.newInstance()
            (holder.itemView.context as AppCompatActivity).supportFragmentManager
                .beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.main_layout, fragment)
                .addToBackStack(null)
                .commit()
            fragment.isFirst = true
            item?.id?.let { it1 ->
                MyCompanyData.getItemById( it1 ,{
                    Log.d(CompanyListFragment.TAG, "MyCompanyRecyclerViewAdapter: successCallback: ${it[0]}")
                    val model = ViewModelProviders.of(holder.itemView.context as FragmentActivity)
                        .get(CompanyDescriptionViewModel::class.java)
                    model.setData(it[0])
                    fragment.isFirst = false
                    fragment.setVisibility(View.VISIBLE)
                    fragment.checkAndSet(it[0])
            }, {
                    Log.d(CompanyListFragment.TAG, "MyCompanyRecyclerViewAdapter: errorCallback: $it")
                    fragment.errorVisibility(it)
                }) }
        }
    }

    override fun getItemCount(): Int = values?.size ?: 0

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.id_tv)
        val nameView: TextView = view.findViewById(R.id.name_tv)
        val imageView: ImageView = view.findViewById(R.id.img)
    }

    companion object{
        const val siteUrl: String = "https://lifehack.studio/test_task/"
    }
}