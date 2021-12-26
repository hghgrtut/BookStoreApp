package by.profs.bookstore.view.list

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import by.profs.bookstore.databinding.FragmentListBinding
import by.profs.bookstore.R
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

class ListFragment : Fragment() {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ListViewModel by viewModels()
    private val voiceSearchCallback =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) it.data?.let { i: Intent ->
                val query = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!!.first()
                binding.search.editText?.text?.append(query)
                viewModel.loadData(query)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        MainScope().launch { with(binding) {
            try {
                voiceInput.setOnClickListener {
                    voiceSearchCallback.launch(
                        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                            val languageModel = RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, languageModel)
                            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                            val hint = getString(R.string.voice_search_hint)
                            putExtra(RecognizerIntent.EXTRA_PROMPT, hint)
                        })
                }
            } catch (e: Exception) {
                e.printStackTrace()
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(
                    "https://play.google.com/store/apps/details?id=com.google.android.tts")))
            }
            buttonSearch.setOnClickListener {
                viewModel.loadData(search.editText?.text.toString()) }
            viewModel.data.collectLatest { list.adapter = ListAdapter(it) }
        } }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}