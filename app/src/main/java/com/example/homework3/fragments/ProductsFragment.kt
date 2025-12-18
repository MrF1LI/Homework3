package com.example.homework3.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homework3.Product
import com.example.homework3.ProductAdapter
import com.example.homework3.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ProductsFragment : Fragment(R.layout.fragment_products) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProductAdapter

    private val productList = mutableListOf<Product>()
    private lateinit var database: DatabaseReference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView(view)
        initFirebase()
        fetchProducts()
    }

    // ------------------------
    // RecyclerView setup
    // ------------------------
    private fun initRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ProductAdapter(productList) { product ->
            Toast.makeText(
                requireContext(),
                "${product.name} - ${product.price} ₾",
                Toast.LENGTH_SHORT
            ).show()
        }

        recyclerView.adapter = adapter
    }

    // ------------------------
    // Firebase init
    // ------------------------
    private fun initFirebase() {
        database = FirebaseDatabase.getInstance()
            .getReference("products")
    }

    // ------------------------
    // Fetch products (Realtime)
    // ------------------------
    private fun fetchProducts() {
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()

                for (child in snapshot.children) {
                    val product = child.getValue(Product::class.java)
                    product?.let { productList.add(it) }
                }

                adapter.notifyDataSetChanged()
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    requireContext(),
                    "Failed: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}

