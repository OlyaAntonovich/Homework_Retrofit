package com.example.homework_retrofit

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.homework_retrofit.databinding.ScreenThirdBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ScreenThird : Fragment() {
    private var _binding: ScreenThirdBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var currentRequestResponse: Call<User>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ScreenThirdBinding.inflate(inflater, container, false)
        .also { _binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            val textWatcherEditText: TextWatcher = object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(editable: Editable?) {
                    val condition: Boolean = editTextName.text.toString().isNotEmpty() &&
                            editTextJob.text.toString().isNotEmpty()

                    buttonPost.isEnabled = condition

                }
            }

            editTextName.addTextChangedListener(textWatcherEditText)
            editTextJob.addTextChangedListener(textWatcherEditText)

            if (buttonPost.isEnabled
            ) {

                buttonPost.setOnClickListener {
                    val user = User(
                        editTextName.text.toString(),
                        editTextJob.text.toString()
                    )
                    postData(user)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun postData(user: User) {
        currentRequestResponse?.cancel()
        currentRequestResponse = ReqresService.api
            .postUsers(user)
            .apply {
                enqueue(object : Callback<User> {

                    override fun onResponse(
                        call: Call<User>,
                        response: Response<User>
                    ) {
                        if (response.isSuccessful) {

                            binding.text.text = response.body().toString()

                            Toast.makeText(
                                requireContext(),
                                response.code().toString() + "Everything is ok",
                                Toast.LENGTH_LONG
                            ).show()

                        } else {
                            HttpException(response)
                            binding.text.text = response.code().toString()
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        if (!call.isCanceled) {
                            "Error found is : " + t.message
                        }
                    }
                })
            }
    }
}
