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
        binding.body.text = ("Материал - ${mine.body}" )
        binding.mass.text = ("Масса мины - ${mine.mass}" )
        binding.typeBB.text = ("Тип взрывчатого вещества - ${mine.type_ВВ}" )
        binding.massaBB.text = ("Масса взрывчатого вещества - ${mine.massa_BB}" )
        binding.segmentRadius.text = ("Радиус сегмента - ${mine.segment_radius}" )
        binding.segmentAngle.text = ("Угол сегмента - ${mine.segment_angle}" )
        binding.mineHeight.text = ("Высота мины - ${mine.mine_height}" )
        binding.typeOfTargetSensors.text = ("Тип датчиков цели - ${mine.Type_of_target_sensors}")
        binding.lengthOfTheTargetTensionSensor.text = ("Усилие срабатывания натяжного датчика цели - ${mine.Length_of_the_target_tension_sensor}")
        binding.theActuationForceOfTheTargetTensionSensor.text = ("Усилие срабатывания натяжного датчика цели - ${mine.The_actuation_force_of_the_target_tension_sensor}")
        binding.theTimeOfBringingTheCombatPosition.text = ("Тип датчиков цели - ${mine.The_time_of_bringing_the_combat_position}")
        binding.theHeightOfTheSplinterSpread.text = ("Тип датчиков цели - ${mine.The_height_of_the_splinter_spread}")
        binding.theRadiusOfDefeat.text = ("Тип датчиков цели - ${mine.The_radius_of_defeat}")
        binding.theRadiusOfSeparationOfIndividualFragments.text = ("Тип датчиков цели - ${mine.The_radius_of_separation_of_individual_fragments}")
        binding.recoverability.text = ("Тип датчиков цели - ${mine.Recoverability}")
        binding.decontamination.text = ("Тип датчиков цели - ${mine.Decontamination}")
        binding.selfdestruction.text = ("Тип датчиков цели - ${mine.Selfdestruction}")
        binding.diameterMine.text = ("Тип датчиков цели - ${mine.Diameter_mine}")
        binding.diameterOfThePressureSensorOfTheTarget.text = ("Тип датчиков цели - ${mine.Diameter_of_the_pressure_sensor_of_the_target}")
        binding.combatOperationTime.text = ("Тип датчиков цели - ${mine.Combat_operation_time}")
        binding.temperatura.text = ("Тип датчиков цели - ${mine.Temperatura}")
        binding.vzrivatel.text = ("Тип датчиков цели - ${mine.Vzrivatel}")
        binding.dlinaMine.text = ("Тип датчиков цели - ${mine.Dlina_mine}")
        binding.tolshinaMine.text = ("Тип датчиков цели - ${mine.tolshina_mine}")
        binding.broneboynost.text = ("Тип датчиков цели - ${mine.broneboynost}")
        binding.theRadiusOfTheTargetDetectionRange.text = ("Тип датчиков цели - ${mine.The_radius_of_the_target_detection_range}")
        binding.nonInvolvement.text = ("Тип датчиков цели - ${mine.non_involvement}")
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