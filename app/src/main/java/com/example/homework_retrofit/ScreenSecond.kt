package com.example.homework_retrofit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.homework_retrofit.databinding.ScreenSecondBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ScreenSecond : Fragment() {
    private var _binding: ScreenSecondBinding? = null
    private val binding get() = requireNotNull(_binding)
    private val currentItem = mutableListOf<Item>()
    private var currentRequest: Call<List<Item>>? = null
    private val args by navArgs<ScreenSecondArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ScreenSecondBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        returnInformationById()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        currentRequest?.cancel()
        _binding = null
    }

    private fun returnInformationById() {
        currentRequest?.cancel()
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
                            currentItem.addAll(users)
                            for (i in 0 until currentItem.size) {
                                if (currentItem[i].id == args.id.toInt()) {
                                    binding.imageAvatar.load(currentItem[i].avatar)
                                    binding.textView2.text = currentItem[i].author
                                }
                            }
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
}