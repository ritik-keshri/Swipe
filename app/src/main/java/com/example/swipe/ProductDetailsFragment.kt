package com.example.swipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.swipe.api.ApiInterface
import com.example.swipe.api.RetrofitInstance
import com.example.swipe.model.ProductList
import com.example.swipe.repository.ProductRepository
import com.example.swipe.viewmodel.ViewModel
import com.example.swipe.viewmodel.ViewModelFactory

class ProductDetailsFragment : Fragment() {

    private lateinit var viewModel: ViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_product_details, container, false)

        recyclerView = rootView.findViewById(R.id.productList)
        progressBar = rootView.findViewById(R.id.progressBar)

        val apiInterface =
            RetrofitInstance.getInstance().create(ApiInterface::class.java)
        val productRepository = ProductRepository(apiInterface)

        viewModel =
            ViewModelProvider(this, ViewModelFactory(productRepository))[ViewModel::class.java]

        viewModel.products.observe(viewLifecycleOwner, Observer {
            initRecyclerView(it)
        })

        return rootView
    }

    private fun initRecyclerView(productList: ProductList) {
        progressBar.visibility = View.GONE
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        productAdapter = ProductAdapter(requireContext(), productList)
        recyclerView.adapter = productAdapter
    }
}