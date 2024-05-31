package raa.example.dictionaryofmines.data

data class MineClass(
    val param: String,
    val name: String,
    val information: String = "",
    val body: String = "",
    val mass: String = "",
    val type_ВВ: String = "",
    val massa_BB: String = "",
    val segment_radius: String = "",
    val segment_angle: String = "",
    val mine_height: String = "",
    val Type_of_target_sensors: String = "",
    val Length_of_the_target_tension_sensor: String = "",
    val The_actuation_force_of_the_target_tension_sensor: String = "",
    val The_time_of_bringing_the_combat_position: String = "",
    val The_height_of_the_splinter_spread: String = "",
    val The_radius_of_defeat: String = "",
    val The_radius_of_separation_of_individual_fragments: String = "",
    val Recoverability: String = "",
    val Decontamination: String = "",
    val Selfdestruction: String = "",
    val Diameter_mine: String = "",
    val Diameter_of_the_pressure_sensor_of_the_target: String = "",
    val Combat_operation_time: String = "",
    val Temperatura: String = "",
    val Vzrivatel: String = "",
    val Dlina_mine: String = "",
    val tolshina_mine: String = "",
    val broneboynost: String = "",
    val The_radius_of_the_target_detection_range: String = "",
    val non_involvement: String = "",
) {

    companion object {
        fun returnEmpty(): MineClass {
            return MineClass("error", "empty")
        }

    }
}
