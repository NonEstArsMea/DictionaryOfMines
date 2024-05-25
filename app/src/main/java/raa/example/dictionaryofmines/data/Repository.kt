package raa.example.dictionaryofmines.data

import android.content.Context
import android.util.Log
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser

object Repository {


    fun getInfo(id: String, context: Context) {

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
        Log.e("tab", fileContent.toString())
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

            listOfNames.add(name)
        }

        return listOfNames.toList()
    }

    private fun getFile(context: Context): String {
        val filename = "db.txt"
        val assetManager = context.assets
        return assetManager.open(filename).bufferedReader().readText()
    }

}