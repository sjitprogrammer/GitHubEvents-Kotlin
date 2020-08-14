package com.aodev.githubevents.screens.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aodev.githubevents.R
import com.aodev.githubevents.data.EventsItemAdapter
import com.aodev.githubevents.data.EventsLandAdapter
import com.aodev.githubevents.listener.OnItemClickListener
import com.aodev.githubevents.network.data.EventsItem
import kotlinx.android.synthetic.main.event_item.*
import kotlinx.android.synthetic.main.event_item.view.*
import kotlinx.android.synthetic.main.event_item.view.card_item
import kotlinx.android.synthetic.main.events_item_land.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(), OnItemClickListener {

    val listItem:ArrayList<EventsItem> = ArrayList()
    val tempItem:ArrayList<EventsItem> = ArrayList()
    val tempItemLand:ArrayList<EventsItem> = ArrayList()

    lateinit var viewModel:HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        showData()
        showDataLand()
        OnObserveViewModel()

        refreshLayout.setOnRefreshListener {
            viewModel.fetchAllEvents()
            recyclerview.adapter?.notifyDataSetChanged()
            recyclerview_land.adapter?.notifyDataSetChanged()
        }
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText : String?): Boolean {

                if(newText!!.isNotEmpty()){
                    tempItem.clear()
                    val search = newText.toLowerCase(Locale.getDefault())

                    listItem.forEach{
                        if(it.actor.login?.toLowerCase(Locale.getDefault())?.contains(search)!!){
                            tempItem.add(it)
                        }
                    }
                    recyclerview.adapter?.notifyDataSetChanged()
                }else{
                    tempItem.clear()
                    tempItem.addAll(listItem)
                    recyclerview.adapter?.notifyDataSetChanged()
                }
                return true
            }

        })
    }

    private fun OnObserveViewModel(){
        viewModel._eventsFetchAll.observe(viewLifecycleOwner, androidx.lifecycle.Observer {

            listItem.clear()
            tempItem.clear()
            listItem.addAll(it)
            tempItem.addAll(it)
            tempItemLand.addAll(it)
            tempItemLand.reverse()
            recyclerview.adapter?.notifyDataSetChanged()
            recyclerview_land.adapter?.notifyDataSetChanged()

        })

        viewModel.isLoading.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it){
                refreshLayout.isRefreshing = false
            }else{
                refreshLayout.isRefreshing = true
            }
        })

        viewModel.isConnectFalse.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if(it){
                refreshLayout.isRefreshing = false
                Toast.makeText(requireContext(),"Can't fetch data from server please check your internet!",Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun showData(){
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = EventsItemAdapter(tempItem,requireContext(),this)
        recyclerview.apply {
            postponeEnterTransition()
            viewTreeObserver.addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }
        }

    }

    private fun showDataLand(){
        recyclerview_land.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        recyclerview_land.adapter = EventsLandAdapter(tempItemLand,requireContext(),this)

    }

    override fun onClickedItem(view: View, id: String, position: Int) {
        var item_args = tempItem[position]
        val number = tempItem[position].id
        val bundle = bundleOf("item_args" to item_args)
        val extras = FragmentNavigatorExtras(
            view.icon_image to "image_$number"
        )
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle,null,extras)
    }


}