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
import androidx.core.view.isVisible
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

    private fun bindingInfo() {
        val mine = Repository.getInfo(param1!!, requireContext())

        binding.name.text = mine.name
        binding.information.text = mine.information
        mine.body.apply {
            if (this != "") {
                binding.body.text = ("Материал - ${this}")
                binding.body.isVisible = true
                binding.divider1.isVisible = true
            }
        }

        mine.mass.apply {
            if (this != "") {
                binding.mass.text = ("Масса мины - ${mine.mass}")
                binding.mass.isVisible = true
                binding.divider2.isVisible = true
            }
        }

        mine.type_ВВ.apply {
            if (this != "") {
                binding.typeBB.text = ("Тип взрывчатого вещества - ${mine.type_ВВ}")
                binding.typeBB.isVisible = true
                binding.divider3.isVisible = true
            }
        }

        mine.massa_BB.apply {
            if (this != "") {
                binding.massaBB.text = ("Масса взрывчатого вещества - ${mine.massa_BB}")
                binding.massaBB.isVisible = true
                binding.divider4.isVisible = true
            }
        }

        mine.segment_radius.apply {
            if (this != "") {
                binding.segmentRadius.text = ("Радиус сегмента - ${mine.segment_radius}")
                binding.segmentRadius.isVisible = true
                binding.divider5.isVisible = true
            }
        }

        mine.segment_angle.apply {
            if (this != "") {
                binding.segmentAngle.text = ("Угол сегмента - ${mine.segment_angle}")
                binding.segmentAngle.isVisible = true
                binding.divider6.isVisible = true
            }
        }

        mine.mine_height.apply {
            if (this != "") {
                binding.mineHeight.text = ("Высота мины - ${mine.mine_height}")
                binding.mineHeight.isVisible = true
                binding.divider7.isVisible = true
            }
        }

        mine.Type_of_target_sensors.apply {
            if (this != "") {
                binding.typeOfTargetSensors.text =
                    ("Тип датчиков цели - ${mine.Type_of_target_sensors}")
                binding.typeOfTargetSensors.isVisible = true
                binding.divider8.isVisible = true
            }
        }

        mine.Length_of_the_target_tension_sensor.apply {
            if (this != "") {
                binding.lengthOfTheTargetTensionSensor.text =
                    ("Длина натяжного датчика цели - ${mine.Length_of_the_target_tension_sensor}")
                binding.lengthOfTheTargetTensionSensor.isVisible = true
                binding.divider9.isVisible = true
            }
        }

        mine.The_actuation_force_of_the_target_tension_sensor.apply {
            if (this != "") {
                binding.theActuationForceOfTheTargetTensionSensor.text =
                    ("Усилие срабатывания натяжного датчика цели - ${mine.The_actuation_force_of_the_target_tension_sensor}")
                binding.theActuationForceOfTheTargetTensionSensor.isVisible = true
                binding.divider10.isVisible = true
            }
        }

        mine.The_time_of_bringing_the_combat_position.apply {
            if (this != "") {
                binding.theTimeOfBringingTheCombatPosition.text =
                    ("Время приведения боевого положения - ${mine.The_time_of_bringing_the_combat_position}")
                binding.theTimeOfBringingTheCombatPosition.isVisible = true
                binding.divider11.isVisible = true
            }
        }

        mine.The_height_of_the_splinter_spread.apply {
            if (this != "") {
                binding.theHeightOfTheSplinterSpread.text =
                    ("Высота разлета осколков - ${mine.The_height_of_the_splinter_spread}")
                binding.theHeightOfTheSplinterSpread.isVisible = true
                binding.divider12.isVisible = true
            }
        }

        mine.The_radius_of_defeat.apply {
            if (this != "") {
                binding.theRadiusOfDefeat.text = ("Радиус поражения - ${mine.The_radius_of_defeat}")
                binding.theRadiusOfDefeat.isVisible = true
                binding.divider13.isVisible = true
            }
        }

        mine.The_radius_of_separation_of_individual_fragments.apply {
            if (this != "") {
                binding.theRadiusOfSeparationOfIndividualFragments.text =
                    ("Радиус отдельных осколков - ${mine.The_radius_of_separation_of_individual_fragments}")
                binding.theRadiusOfSeparationOfIndividualFragments.isVisible = true
                binding.divider14.isVisible = true
            }
        }

        mine.Recoverability.apply {
            if (this != "") {
                binding.recoverability.text = ("Элемент извлекаемости - ${mine.Recoverability}")
                binding.recoverability.isVisible = true
                binding.divider15.isVisible = true
            }
        }

        mine.Decontamination.apply {
            if (this != "") {
                binding.decontamination.text =
                    ("Элемент обезвреживаемости - ${mine.Decontamination}")
                binding.decontamination.isVisible = true
                binding.divider16.isVisible = true
            }
        }

        mine.Selfdestruction.apply {
            if (this != "") {
                binding.selfdestruction.text = ("Элемент самоликвидации - ${mine.Selfdestruction}")
                binding.selfdestruction.isVisible = true
                binding.divider17.isVisible = true
            }
        }

        mine.Diameter_mine.apply {
            if (this != "") {
                binding.diameterMine.text = ("Диаметр мины - ${mine.Diameter_mine}")
                binding.diameterMine.isVisible = true
                binding.divider18.isVisible = true
            }
        }

        mine.Diameter_of_the_pressure_sensor_of_the_target.apply {
            if (this != "") {
                binding.diameterOfThePressureSensorOfTheTarget.text =
                    ("Диаметр нажимного датчика цели - ${mine.Diameter_of_the_pressure_sensor_of_the_target}")
                binding.diameterOfThePressureSensorOfTheTarget.isVisible = true
                binding.divider19.isVisible = true
            }
        }

        mine.Combat_operation_time.apply {
            if (this != "") {
                binding.combatOperationTime.text =
                    ("Время боевой работы - ${mine.Combat_operation_time}")
                binding.combatOperationTime.isVisible = true
                binding.divider20.isVisible = true
            }
        }

        mine.Temperatura.apply {
            if (this != "") {
                binding.temperatura.text = ("Температурный диапазон - ${mine.Temperatura}")
                binding.temperatura.isVisible = true
                binding.divider21.isVisible = true
            }
        }

        mine.Vzrivatel.apply {
            if (this != "") {
                binding.vzrivatel.text = ("Основной взрыватель - ${mine.Vzrivatel}")
                binding.vzrivatel.isVisible = true
                binding.divider22.isVisible = true
            }
        }

        mine.Dlina_mine.apply {
            if (this != "") {
                binding.dlinaMine.text = ("Длина мины - ${mine.Dlina_mine}")
                binding.dlinaMine.isVisible = true
                binding.divider23.isVisible = true
            }
        }

        mine.tolshina_mine.apply {
            if (this != "") {
                binding.tolshinaMine.text = ("Толщина мины - ${mine.tolshina_mine}")
                binding.tolshinaMine.isVisible = true
                binding.divider24.isVisible = true
            }
        }

        mine.broneboynost.apply {
            if (this != "") {
                binding.broneboynost.text = ("Бронебойность - ${mine.broneboynost}")
                binding.broneboynost.isVisible = true
                binding.divider25.isVisible = true
            }
        }

        mine.The_radius_of_the_target_detection_range.apply {
            if (this != "") {
                binding.theRadiusOfTheTargetDetectionRange.text =
                    ("Радиус дальности обнаружения цели - ${mine.The_radius_of_the_target_detection_range}")
                binding.theRadiusOfTheTargetDetectionRange.isVisible = true
                binding.divider26.isVisible = true
            }
        }

        mine.non_involvement.apply {
            if (this != "") {
                binding.nonInvolvement.text =
                    ("Возможность обезвреживания - ${mine.non_involvement}")
                binding.nonInvolvement.isVisible = true
                binding.divider27.isVisible = true
            }
        }

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