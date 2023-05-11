package com.example.plantidentification.feature_plant_identification.data.repository

import android.content.Context
import android.util.Base64
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.plantidentification.feature_plant_identification.core.extensions.editData
import com.example.plantidentification.feature_plant_identification.core.extensions.getData
import com.example.plantidentification.feature_plant_identification.data.dto.PlantSpeciesDto
import com.example.plantidentification.feature_plant_identification.domain.repository.MainRepository
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.util.prefs.Preferences

val Context.dataStore by preferencesDataStore(name = "preferences")
private val DATA_STORE_INFORMATION_KEY = booleanPreferencesKey("information_key")

class MainRepositoryImpl(
    context: Context
) : MainRepository {

    private var dataStore = context.dataStore

    override suspend fun userReadAppInformation(): Boolean {
        return dataStore.getData(key = DATA_STORE_INFORMATION_KEY, defaultValue = false).first()
    }

    override suspend fun setUserReadAppInformation() {
        return dataStore.editData(DATA_STORE_INFORMATION_KEY, true)
    }

    override suspend fun recognizeFood(imageUri: String): PlantSpeciesDto {
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


    }


    private fun getDataJsonObject(imageUri: String): JSONObject {
        val data = JSONObject()
        data.put("api_key", "27rVFvUXRmdcKtJTTNNjUmWIgss8c8CtNTvJVE4YWLat04RFOs")

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