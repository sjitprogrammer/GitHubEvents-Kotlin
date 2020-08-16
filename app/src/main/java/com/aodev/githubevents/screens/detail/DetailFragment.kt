package com.aodev.githubevents.screens.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aodev.githubevents.R
import com.aodev.githubevents.databinding.FragmentDetailBinding
import com.aodev.githubevents.network.data.EventsItem
import com.bumptech.glide.Glide

class DetailFragment : Fragment() {
    val listItems: ArrayList<String> = ArrayList()
    private lateinit var viewModel: DetailViewModel
    private lateinit var viewModelFactory: DetailViewModelFactory
    private lateinit var binding: FragmentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val transition =
            TransitionInflater.from(activity).inflateTransition(R.transition.change_bounds)
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_detail,
            container,
            false
        )
        val item_args: EventsItem = arguments?.getParcelable("item_args")!!
        viewModelFactory = DetailViewModelFactory(item_args)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(DetailViewModel::class.java)
        binding.detailViewModel = viewModel
        binding.setLifecycleOwner(this)

        setImageUrl()
        setButtonBack()
        viewModel.eventGotoGithub.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain) {
                gotoGithubRepoClick()
                viewModel.onGotoGithubRepoComplete()
            }
        })
        return binding.root
    }

    private fun setButtonBack() {
        binding.imageViewBack.setOnClickListener {
            getActivity()?.onBackPressed();
        }
    }

    private fun setImageUrl() {

        binding.iconImageProfile.transitionName = "image_${viewModel.GetId()}"
        val imageUrl = viewModel.GetImageUrl()
        Glide.with(binding.root)
            .load(imageUrl)
            .placeholder(R.drawable.placeholder_github)
            .into(binding.iconImageProfile);

    }

    fun gotoGithubRepoClick(){
        val uris = Uri.parse(viewModel.getGithubRepoUrl())
        val intents = Intent(Intent.ACTION_VIEW, uris)
        val b = Bundle()
        b.putBoolean("new_window", true)
        intents.putExtras(b)
        requireContext().startActivity(intents)
    }
}