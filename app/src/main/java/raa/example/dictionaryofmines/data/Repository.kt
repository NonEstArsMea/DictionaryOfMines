package raa.example.dictionaryofmines.data

import android.content.Context
import android.util.Log
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser

object Repository {


    fun getInfo(id: String, context: Context): MineClass {

        val filename = "db.txt"
        val assetManager = context.assets


        val fileContent = assetManager.open(filename).bufferedReader().readText()

        val csvParser = CSVParser(
            fileContent.reader(), CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
                .withDelimiter(';')
        )

        for (line in csvParser){
            if(id == line.get(1)){
                return MineClass(
                    param = line.get(0),
                    name = line.get(1),
                    information = line.get(2),
                    body = line.get(3),
                    mass = line.get(4),
                    type_ВВ = line.get(5),
                    massa_BB = line.get(6),
                    segment_radius = line.get(7),
                    segment_angle = line.get(8),
                    mine_height = line.get(9),
                    Type_of_target_sensors = line.get(10),
                    Length_of_the_target_tension_sensor = line.get(11),
                    The_actuation_force_of_the_target_tension_sensor = line.get(12),
                    The_time_of_bringing_the_combat_position = line.get(13),
                    The_height_of_the_splinter_spread = line.get(14),
                    The_radius_of_defeat = line.get(15),
                    The_radius_of_separation_of_individual_fragments = line.get(16),
                    Recoverability = line.get(17),
                    Decontamination = line.get(18),
                    Selfdestruction = line.get(19),
                    Diameter_mine = line.get(20),
                    Diameter_of_the_pressure_sensor_of_the_target = line.get(21),
                    Combat_operation_time = line.get(22),
                    Temperatura = line.get(23),
                    Vzrivatel = line.get(24),
                    Dlina_mine = line.get(25),
                    tolshina_mine = line.get(26),
                    broneboynost = line.get(27),
                    The_radius_of_the_target_detection_range = line.get(28),
                    non_involvement = line.get(29)
                )
            }


        }
        Log.e("tab", fileContent.toString())
        return MineClass.returnEmpty()
    }

    fun getNamesOfCards(context: Context): List<String>{
        val csvParser = CSVParser(
            getFile(context).reader(), CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
                .withDelimiter(';')
        )

        val listOfNames = mutableListOf<String>()

        var name: String

        for (line in csvParser){
            name = line.get(1)
            if(name.isNotBlank())
                listOfNames.add(name)
        }

        return listOfNames.toList()
    }

    private fun getFile(context: Context): String {
        val filename = "db.txt"
        val assetManager = context.assets
        return assetManager.open(filename).bufferedReader().readText()
    }

    fun getImage(context: Context, id: String): List<String> {
        val csvParser = CSVParser(
            getFile(context).reader(), CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim()
                .withDelimiter(';')
        )

        for (line in csvParser){
            if(id == line.get(1)){
                return line.get(30).split("\n")
                    .map { it.trim() }
                    .filter { it.isNotEmpty() }
            }


        }

        return arrayListOf()
    }

}