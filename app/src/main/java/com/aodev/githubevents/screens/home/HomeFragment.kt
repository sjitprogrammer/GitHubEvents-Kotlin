package com.aodev.githubevents.screens.home

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aodev.githubevents.R
import com.aodev.githubevents.data.EventsItemAdapter
import com.aodev.githubevents.data.EventsLandAdapter
import com.aodev.githubevents.listener.OnItemClickListener
import com.aodev.githubevents.network.data.EventsItem
import kotlinx.android.synthetic.main.event_item.view.*
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
        val rootView: View = inflater.inflate(R.layout.fragment_home, container, false)

        val myToolbar =
            rootView.findViewById(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity?)!!.setSupportActionBar(myToolbar)
        setHasOptionsMenu(true)
        return rootView
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

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        val search = menu.findItem(R.id.action_search)
        val searchView = search.actionView as SearchView
        searchView.setBackgroundResource(R.drawable.background_search)
        searchView.setMaxWidth(Integer.MAX_VALUE)
        searchView.queryHint = "Search"

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
        super.onCreateOptionsMenu(menu, inflater)
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
            textView_Events.text = "Events ${it.size.toString()?:"0"}"

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