package raa.example.dictionaryofmines.ui.mainFragment

import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ViewSwitcher
import androidx.fragment.app.Fragment
import raa.example.dictionaryofmines.R
import raa.example.dictionaryofmines.databinding.FragmentMineDetailBinding


private const val ARG_PARAM1 = "param1"

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


class MineDetail : Fragment()  {
    private var param1: String? = null

    private var _binding: FragmentMineDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMineDetailBinding.inflate(inflater, container, false)

        imageSwitcher = binding.switcher
        imageSwitcher.setFactory(ViewSwitcher.ViewFactory {
            val imageView = ImageView(requireContext())
            imageView.scaleType = ImageView.ScaleType.FIT_CENTER
            imageView.layoutParams = FrameLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
            )
            imageView
        })

        gestureDetector = GestureDetector(requireContext(), object : GestureDetector.OnGestureListener, View.OnTouchListener{
            override fun onDown(e: MotionEvent) = false

            override fun onShowPress(e: MotionEvent){}

            override fun onSingleTapUp(e: MotionEvent) = false

            override fun onScroll(
                e1: MotionEvent?,
                e2: MotionEvent,
                distanceX: Float,
                distanceY: Float
            ) = false

            override fun onLongPress(e: MotionEvent) {}

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val sensitivity = 50

                if (e1 != null && e2 != null) {
                    if (e1.x - e2.x > sensitivity) {
                        showNextImage()
                    } else if (e2.x - e1.x > sensitivity) {
                        showPreviousImage()
                    }
                }
                return true
            }

            override fun onTouch(v: View?, event: MotionEvent): Boolean {
                return gestureDetector.onTouchEvent(event)
            }

        })

        // Set animations
        val inAnim = AnimationUtils.loadAnimation(requireContext(), android.R.anim.slide_in_left)
        val outAnim = AnimationUtils.loadAnimation(requireContext(), android.R.anim.slide_out_right)
        imageSwitcher.inAnimation = inAnim
        imageSwitcher.outAnimation = outAnim

        imageSwitcher.setImageResource(images[currentIndex])


        return binding.root
    }

    private fun showNextImage() {
        currentIndex = (currentIndex + 1) % images.size
        imageSwitcher.setImageResource(images[currentIndex])
    }

    private fun showPreviousImage() {
        currentIndex = if (currentIndex > 0) currentIndex - 1 else images.size - 1
        imageSwitcher.setImageResource(images[currentIndex])
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