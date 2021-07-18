package com.miqbalkalevi.todolist.data

import android.content.Context
import android.graphics.Region
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferenceManager"

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

data class FilterPreferences(val sortOrder: SortOrder, val hideCompleted: Boolean)

@Singleton
class PreferenceManager @Inject constructor(@ApplicationContext context: Context) {

    val dataStore = context.dataStore

    val preferencesFlow = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { prefs ->
            //region initializing default preferences values
            val sortOrder = SortOrder.valueOf(
                prefs[preferencesKeys.SORT_ORDER] ?: SortOrder.BY_DATE.name
            )

            val hideCompleted = prefs[preferencesKeys.HIDE_COMPLETED] ?: false
            //endregion

            FilterPreferences(sortOrder, hideCompleted)
        }

    suspend fun updateSortOrder(sortOrder: SortOrder) {
        dataStore.edit { prefs ->
            prefs[preferencesKeys.SORT_ORDER] = sortOrder.name
        }
    }

    suspend fun updateHideCompleted(hideCompleted: Boolean) {
        dataStore.edit { prefs ->
            prefs[preferencesKeys.HIDE_COMPLETED] = hideCompleted
        }
    }

    private object preferencesKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val HIDE_COMPLETED = booleanPreferencesKey("hide_completed")

    }

}

enum class SortOrder {
    BY_NAME,
    BY_DATE
}