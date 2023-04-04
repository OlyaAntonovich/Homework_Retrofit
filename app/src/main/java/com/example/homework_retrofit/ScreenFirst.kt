package com.example.homework_retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.homework_retrofit.databinding.ScreenFirstBinding
import com.google.android.material.divider.MaterialDividerItemDecoration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ScreenFirst : Fragment() {
    private var _binding: ScreenFirstBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var items = mutableListOf<Item>()
    private var currentRequest: Call<List<Item>>? = null

    private val adapter by lazy {
        Adapter(
            items, requireContext()
        ) {
            findNavController().navigate(ScreenFirstDirections.toScreenSecond(it.id.toString()))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ScreenFirstBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        returnRecyclerView()

        binding.buttonClick.setOnClickListener{
            findNavController().navigate(ScreenFirstDirections.toScreenThird(KEY))}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentRequest?.cancel()
        _binding = null
    }

    private fun returnRecyclerView() {
        currentRequest = PicsumService.api
            .getItems()
            .apply {
                enqueue(object : Callback<List<Item>> {
                    override fun onResponse(
                        call: Call<List<Item>>,
                        response: Response<List<Item>>
                    ) {
                        if (response.isSuccessful) {
                            val users = response.body() ?: return
                            items.addAll(users)
                            adapter.notifyDataSetChanged()
                            binding.recyclerView.layoutManager =
                                LinearLayoutManager(requireContext())
                            binding.recyclerView.adapter = adapter
                            binding.recyclerView.addItemDecoration(
                                MaterialDividerItemDecoration(
                                    requireContext(),
                                    MaterialDividerItemDecoration.VERTICAL
                                )
                            )
                        } else {
                            HttpException(response)
                        }
                        currentRequest = null
                    }

                    override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                        if (!call.isCanceled) {
                        }
                        currentRequest = null
                    }
                })
            }
    }

    companion object{
        private val KEY = "go"
    }
}