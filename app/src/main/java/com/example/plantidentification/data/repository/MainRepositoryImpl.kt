package com.example.plantidentification.data.repository

import android.util.Base64
import com.example.plantidentification.data.dto.PlantSpeciesDto
import com.example.plantidentification.data.dto.Suggestion
import com.example.plantidentification.domain.repository.MainRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class MainRepositoryImpl(
) : MainRepository {

    override suspend fun recognizeFood(imageUri: String): Suggestion {
        val data = getDataJsonObject(imageUri)

        val url = URL("https://api.plant.id/v2/identify")
        val con = withContext(Dispatchers.IO) {
            url.openConnection()
        } as HttpURLConnection

        con.doOutput = true
        con.doInput = true
        con.requestMethod = "POST"
        con.setRequestProperty("Content-Type", "application/json")

        val os: OutputStream = con.outputStream
        withContext(Dispatchers.IO) {
            os.write(data.toString().toByteArray())
        }
        withContext(Dispatchers.IO) {
            os.close()
        }

        val `is`: InputStream = con.inputStream
        val br = BufferedReader(InputStreamReader(`is`))
        val response = StringBuilder()
        var line: String? = withContext(Dispatchers.IO) {
            br.readLine()
        }
        while (line != null) {
            response.append(line)
            line = withContext(Dispatchers.IO) {
                br.readLine()
            }
        }
        val responseString = response.toString()

        con.disconnect()

        val gson = Gson()
        return gson.fromJson(responseString, PlantSpeciesDto::class.java)
                   .getPlantWithHighestProbability() ?: throw RuntimeException("No plant found")

    }


    private fun PlantSpeciesDto.getPlantWithHighestProbability(): Suggestion? {
        var maxProbability = -1.0
        var maxPlant: Suggestion? = null
        for (i in suggestions.indices) {
            val plant = suggestions[i]
            val probability = plant.probability
            if (probability > maxProbability) {
                maxProbability = probability
                maxPlant = plant
            }
        }
        return maxPlant
    }

    private fun getDataJsonObject(imageUri: String): JSONObject {
        val data = JSONObject()
        data.put("api_key", "eMalbvhUDX4wFuKa8AgFzWt4Jvgh2l0r1eqyoIWx3SDbqzPdUm")

        val images = JSONArray()
        val fileData = base64EncodeFromFile(imageUri)
        images.put(fileData)
        data.put("images", images)

        val modifiers = JSONArray()
            .put("crops_fast")
            .put("similar_images")
        data.put("modifiers", modifiers)

        data.put("plant_language", "en")

        val plantDetails = JSONArray()
            .put("common_names")
            .put("url")
            .put("name_authority")
            .put("wiki_description")
            .put("taxonomy")
            .put("synonyms")
        data.put("plant_details", plantDetails)
        return data
    }


    private fun base64EncodeFromFile(fileString: String): String {
        val file = File(fileString)
        val fis = FileInputStream(file)
        val buffer = ByteArray(file.length().toInt())
        fis.read(buffer)
        fis.close()
        return Base64.encodeToString(buffer, Base64.DEFAULT)
    }


}