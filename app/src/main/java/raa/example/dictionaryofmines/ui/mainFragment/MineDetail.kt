package raa.example.dictionaryofmines.ui.mainFragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import raa.example.dictionaryofmines.R
import raa.example.dictionaryofmines.data.Repository
import raa.example.dictionaryofmines.databinding.FragmentMineDetailBinding


private const val ARG_PARAM1 = "param1"

@SuppressLint("StaticFieldLeak")
private lateinit var imageSwitcher: ImageSwitcher
private lateinit var gestureDetector: GestureDetector

private var currentIndex = 0

private val images = intArrayOf(
    R.drawable.img1,
    R.drawable.img2,
    R.drawable.img3,
    R.drawable.img4,
    R.drawable.img5,
)


class MineDetail : Fragment() {
    private var param1: String? = null

    private var _binding: FragmentMineDetailBinding? = null
    private val binding get() = _binding!!

    // Set animations
    private lateinit var inAnimLeft: Animation
    private lateinit var outAnimLeft: Animation
    private lateinit var inAnimRight: Animation
    private lateinit var outAnimRight: Animation

    override fun onAttach(context: Context) {
        super.onAttach(context)
        inAnimLeft = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        outAnimRight = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right)
        outAnimLeft = AnimationUtils.loadAnimation(context, R.anim.slide_out_left)
        inAnimRight = AnimationUtils.loadAnimation(context, R.anim.slide_in_right)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }


    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMineDetailBinding.inflate(inflater, container, false)

        imageSwitcher = binding.switcher
        imageSwitcher.setFactory {
            val imageView = ImageView(requireContext())
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            )
            imageView
        }


        gestureDetector =
            GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {

                override fun onDoubleTap(e: MotionEvent) = showNextImage()

                override fun onFling(
                    e1: MotionEvent?,
                    e2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    Log.e("motion", e1.toString())
                    val sensitivity = 40

                    if (e1 != null) {
                        if (e1.x - e2.x > sensitivity) {
                            return showNextImage()
                        } else if (e2.x - e1.x > sensitivity) {
                            return showPreviousImage()
                        }
                    }
                    return false
                }
            })

        imageSwitcher.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
        }



        imageSwitcher.setImageResource(images[currentIndex])


        bindingInfo()

        return binding.root
    }

    private fun showNextImage(): Boolean {
        currentIndex = (currentIndex + 1) % images.size
        imageSwitcher.inAnimation = inAnimRight
        imageSwitcher.outAnimation = outAnimLeft
        imageSwitcher.setImageResource(images[currentIndex])
        return true
    }

    private fun showPreviousImage(): Boolean {
        currentIndex = if (currentIndex > 0) currentIndex - 1 else images.size - 1
        imageSwitcher.inAnimation = inAnimLeft
        imageSwitcher.outAnimation = outAnimRight
        imageSwitcher.setImageResource(images[currentIndex])
        return true
    }

    private fun bindingInfo(){
        val mine = Repository.getInfo(param1!!, requireContext())

        binding.name.text = mine.name
        binding.information.text = mine.information
    }

    override fun onStart() {
        super.onStart()


        binding.backButton.setOnClickListener {
            backToMain()
        }

    }

    private fun backToMain() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, MainFragment.newInstance())
            .commit()
    }


    companion object {

        fun newInstance(param1: String) =
            MineDetail().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

}