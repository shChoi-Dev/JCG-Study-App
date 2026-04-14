package com.example.certstudy.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "study_settings")

class StudyPreferencesRepository(private val context: Context) {
    private object Keys {
        val EXAM_DATE_EPOCH = longPreferencesKey("exam_date_epoch_day")
    }

    val examDateEpochDayFlow: Flow<Long?> = context.dataStore.data.map { preferences: Preferences ->
        preferences[Keys.EXAM_DATE_EPOCH]
    }

    suspend fun saveExamDateEpochDay(epochDay: Long) {
        context.dataStore.edit { preferences ->
            preferences[Keys.EXAM_DATE_EPOCH] = epochDay
        }
    }
}
