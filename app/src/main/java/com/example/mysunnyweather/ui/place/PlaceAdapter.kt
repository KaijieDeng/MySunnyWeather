package com.example.mysunnyweather.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.mysunnyweather.R
import com.example.mysunnyweather.databinding.PlaceItemBinding
import com.example.mysunnyweather.logic.model.Place
import com.example.mysunnyweather.ui.weather.WeatherActivity

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: List<Place>): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    inner class ViewHolder(dataBinding: PlaceItemBinding) : RecyclerView.ViewHolder(dataBinding.root) {
        val _dataBinding: PlaceItemBinding = dataBinding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val dataBinding: PlaceItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.place_item, parent, false)

        val holder = ViewHolder(dataBinding)
        holder.itemView.setOnClickListener {
            val position = holder.adapterPosition
            val place = placeList[position]

            val activty  = fragment.activity
            if (activty is WeatherActivity){
                activty.binding.drawerLayout.closeDrawers()
                activty.viewModel.locationLng = place.location.lng
                activty.viewModel.locationLat = place.location.lat
                activty.viewModel.placeName = place.name
                activty.refreshWeather()
            }else{
                val intent = Intent(parent.context,WeatherActivity::class.java).apply {
                    putExtra("location_lng",place.location.lng)
                    putExtra("location_lat",place.location.lat)
                    putExtra("place_name",place.name)
                }
                fragment.viewModel.savePlace(place)
                fragment.startActivity(intent)
                fragment.activity?.finish()
            }
            fragment.viewModel.savePlace(place)

        }

        return holder
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder._dataBinding.placeName.text = place.name
        holder._dataBinding.placeAddress.text = place.address
    }


}