package raa.example.dictionaryofmines.ui.mainFragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import raa.example.dictionaryofmines.R
import raa.example.dictionaryofmines.databinding.FragmentMainBinding
import java.util.Random

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val mainParamAdapter = RecycleViewAdapter()

    private val mineList = buildList {
        (1..10).forEach {
            this.add(MineDataClass(it, "Мина ${it}${Random().nextInt(100000)}"))
        }
    }

    private val viewModel: MainFragmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rvAddPerson = binding.recycleView
        rvAddPerson.adapter = mainParamAdapter
        rvAddPerson.layoutManager = LinearLayoutManager(context)

        mainParamAdapter.submitList(mineList)

        binding.searchVeiw.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false

            override fun onQueryTextChange(text: String): Boolean {
                val aCopy = mineList.toMutableList()
                aCopy.retainAll { newText ->
                    text in newText.name.lowercase()
                }
                mainParamAdapter.submitList(aCopy)
                return false
            }
        })


        mainParamAdapter.onClickListener = {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.main_fragment_container, MineDetail.newInstance(it.name))
                .commit()
        }

    }
}