package com.wainow.companiono.description

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.wainow.companiono.list.CompanyListFragment
import com.wainow.companiono.list.MyCompanyRecyclerViewAdapter
import com.wainow.companiono.R
import com.wainow.domain.common.Cleanable
import com.wainow.domain.entities.CompanyDescription


class CompanyDescriptionFragment : Fragment(), Cleanable {
    private val viewModel by lazy {ViewModelProviders.of(context as FragmentActivity).get(
        CompanyDescriptionViewModel::class.java
    )}

    lateinit var id: String
    lateinit var name: String
    lateinit var imageView: ImageView
    lateinit var descriptionView: TextView
    lateinit var latLonView: TextView
    lateinit var wwwView: TextView
    lateinit var phoneView: TextView
    lateinit var pd: ProgressBar
    lateinit var cardView: CardView
    var isFirst = false

    companion object {
        fun newInstance() = CompanyDescriptionFragment()
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(CompanyListFragment.TAG, "CompanyDescriptionFragment: onCreateView")
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.setHomeButtonEnabled(true)
        val view = inflater.inflate(R.layout.fragment_item_description, container, false)

        descriptionView = view.findViewById(R.id.description_info)
        latLonView = view.findViewById(R.id.lat_lon_info)
        wwwView = view.findViewById(R.id.www_info)
        phoneView = view.findViewById(R.id.phone_info)
        imageView = view.findViewById(R.id.img_info)
        cardView = view.findViewById(R.id.card_info)
        pd = view.findViewById(R.id.pd_info)

        setVisibility(View.GONE)
        viewModel.getData().observe(viewLifecycleOwner, {
            Log.d(CompanyListFragment.TAG, "CompanyDescriptionFragment: onCreateView: observe")
            setVisibility(View.VISIBLE)
            checkAndSet(it)
        })
        return view
    }

     fun setVisibility(visibility: Int){
        if(!isFirst) {
            if (visibility == View.INVISIBLE) {
                pd.visibility = View.VISIBLE
                Log.d(
                    CompanyListFragment.TAG,
                    "CompanyDescriptionFragment: setVisibility: invisible"
                )
            } else {
                Log.d(CompanyListFragment.TAG, "CompanyDescriptionFragment: setVisibility: visible")
                pd.visibility = View.INVISIBLE
            }
            visibility.apply {
                cardView.visibility = this
                descriptionView.visibility  = this
                latLonView.visibility  = this
                wwwView.visibility  = this
                phoneView.visibility  = this
                imageView.visibility = this
            }
        } else
            Log.d(CompanyListFragment.TAG, "CompanyDescriptionFragment: setVisibility: isFirst")
    }

    fun errorVisibility(errorString: String){
        pd.visibility = View.GONE
        descriptionView.visibility = View.VISIBLE
        descriptionView.text = errorString
        View.GONE.apply {
            cardView.visibility = this
            latLonView.visibility  = this
            wwwView.visibility  = this
            phoneView.visibility  = this
            imageView.visibility = this
        }
    }

    @SuppressLint("SetTextI18n")
    fun checkAndSet(companyDescription: CompanyDescription){
        Log.d(CompanyListFragment.TAG, "CompanyDescriptionFragment: checkAndSet")
        companyDescription.let {
            id = it.id.toString()
            name = it.name
            (activity as AppCompatActivity?)?.supportActionBar?.title = "#${id} $name"
            Log.d(CompanyListFragment.TAG, "CompanyDescriptionFragment: checkAndSet: description: ${it.description}")
            if(it.description != "") {
                descriptionView.text = it.description
            } else {
                descriptionView.visibility = View.GONE
            }
            Log.d(CompanyListFragment.TAG, "CompanyDescriptionFragment: checkAndSet: lat lon: ${it.lat} ${it.lon}")
            if(it.lat != 0.0 && it.lon == 0.0) {
                latLonView.text = "lat: ${it.lat}"
            } else if(it.lat == 0.0 && it.lon != 0.0) {
                latLonView.text = "lon: ${it.lon}"
            } else if(it.lat != 0.0 && it.lon != 0.0) {
                latLonView.text = "lat: ${it.lat} lon: ${it.lon}"
            } else latLonView.visibility = View.GONE
            Log.d(CompanyListFragment.TAG, "CompanyDescriptionFragment: checkAndSet: www: ${it.www}")
            if(it.www != "") wwwView.text = "site: ${it.www}" else wwwView.visibility = View.GONE
            Log.d(CompanyListFragment.TAG, "CompanyDescriptionFragment: checkAndSet: phone: ${it.phone}")
            if(it.phone != "") phoneView.text = "phone: ${it.phone}" else phoneView.visibility = View.GONE
            Glide
                .with(this)
                .load(MyCompanyRecyclerViewAdapter.siteUrl + it.img)
                .error(R.drawable.ic_group)
                .into(imageView)
        }
    }

    override fun onDestroyView() {
        Log.d(CompanyListFragment.TAG, "CompanyDescriptionFragment: onDestroyView")
        super.onDestroyView()
    }
}