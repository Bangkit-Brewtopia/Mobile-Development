package academy.bangkit.brewtopia.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    private val tokens = stringPreferencesKey("session_token")
    private val names = stringPreferencesKey("name")

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[tokens] ?: ""
        }
    }

    fun getName(): Flow<String> {
        return dataStore.data.map {
            it[names] ?: ""
        }
    }

    suspend fun saveToken(token: String) {
        dataStore.edit {
            it[tokens] = token
        }
    }

    suspend fun saveName(name: String) {
        dataStore.edit {
            it[names] = name
        }
    }

    suspend fun clearSession() {
        dataStore.edit {
            it.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}