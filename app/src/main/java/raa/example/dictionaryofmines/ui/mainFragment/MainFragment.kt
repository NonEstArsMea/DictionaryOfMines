package raa.example.dictionaryofmines.ui.mainFragment

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import raa.example.dictionaryofmines.R
import raa.example.dictionaryofmines.data.Repository
import raa.example.dictionaryofmines.databinding.FragmentMainBinding
import java.util.Random

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val mainParamAdapter = RecycleViewAdapter()

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

        val filterIcon: MaterialButton = view.findViewById(R.id.filter_icon)
        val filterLayout: MaterialCardView = view.findViewById(R.id.filter_layout)

        filterIcon.setOnClickListener {
            if (filterLayout.visibility == View.GONE) {
                expand(filterLayout)
                filterIcon.icon = requireContext().getDrawable(R.drawable.arrow_up) // Меняем иконку на стрелку вверх
            } else {
                collapse(filterLayout)
                filterIcon.icon = requireContext().getDrawable(R.drawable.arrow_down) // Меняем иконку на стрелку вниз
            }
        }

        var names = Repository.getNamesOfCards(requireContext())

        val mineList = buildList {
            repeat(names.size) {
                this.add(MineDataClass(it, names[it]))
            }
        }

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

    private fun expand(view: View) {
        view.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        val targetHeight = view.measuredHeight

        view.layoutParams.height = 0
        view.visibility = View.VISIBLE
        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                view.layoutParams.height =
                    if (interpolatedTime == 1f) LinearLayout.LayoutParams.WRAP_CONTENT
                    else (targetHeight * interpolatedTime).toInt()
                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        animation.duration = (targetHeight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }

    private fun collapse(view: View) {
        val initialHeight = view.measuredHeight

        val animation = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    view.visibility = View.GONE
                } else {
                    view.layoutParams.height =
                        initialHeight - (initialHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        animation.duration = (initialHeight / view.context.resources.displayMetrics.density).toLong()
        view.startAnimation(animation)
    }
}