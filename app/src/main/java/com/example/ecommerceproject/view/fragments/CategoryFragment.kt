package com.example.ecommerceproject.view.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ecommerceproject.R
import com.example.ecommerceproject.adapters.CategoryAdapter
import com.example.ecommerceproject.databinding.FragmentCategoryBinding
import com.example.ecommerceproject.viewmodel.CategoryViewModel
import com.google.android.material.navigation.NavigationView

class CategoryFragment : Fragment() {
    lateinit var binding: FragmentCategoryBinding
    lateinit var categoryAdapter: CategoryAdapter
    private val viewModel: CategoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View{
        binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        categoryAdapter = CategoryAdapter(emptyList()) { category ->
            val bundle = Bundle().apply {
                putString("category_id", category.category_id)
                putString("category_name", category.category_name)
            }
            val subcategoryFragment = SubcategoryFragment()
            subcategoryFragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, subcategoryFragment)
                .addToBackStack(null)
                .commit()
        }

        binding.rvCategories.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvCategories.adapter = categoryAdapter


        observeViewModel()
        viewModel.fetchCategories()

    }

    override fun onResume() {
        super.onResume()
        setupDrawer()
    }

    private fun setupDrawer() {
        val drawerLayout = requireActivity().findViewById<DrawerLayout>(R.id.drawer_layout)
        val toolbar = requireActivity().findViewById<Toolbar>(R.id.toolbar)
        val navView = requireActivity().findViewById<NavigationView>(R.id.navView)

        val toggle = ActionBarDrawerToggle(
            requireActivity(),
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()


        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    Toast.makeText(requireContext(), "Home Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.cart -> {
                    Toast.makeText(requireContext(), "Cart Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.orders -> {
                    Toast.makeText(requireContext(), "Orders Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.profile -> {
                    Toast.makeText(requireContext(), "Profile Clicked", Toast.LENGTH_SHORT).show()
                }
                R.id.logout -> {
                    logOutUser()
                }
            }

            drawerLayout.closeDrawers()
            true
        }
    }

    private fun observeViewModel() {
        viewModel.categories.observe(viewLifecycleOwner) { response ->
            response?.categories?.let {
                categoryAdapter.updateCategories(it)
                binding.rvCategories.visibility = View.VISIBLE
            }
        }

        viewModel.error.observe(viewLifecycleOwner){errorMessage ->
            Toast.makeText(requireContext(), "Network error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun logOutUser() {
        val sharedPreferences = requireContext().getSharedPreferences("UserSession", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        navigateToLogin()
    }

    private fun navigateToLogin() {
        val loginFragment = LoginFragment()
        parentFragmentManager.popBackStack(null, androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, loginFragment)
            .commit()

    }

}