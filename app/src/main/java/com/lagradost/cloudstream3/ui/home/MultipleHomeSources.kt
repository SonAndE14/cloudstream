package com.lagradost.cloudstream3.ui.home

import android.content.Context
import androidx.preference.PreferenceManager
import com.lagradost.cloudstream3.MainAPI
import com.lagradost.cloudstream3.utils.AppContextUtils.filterProviderByPreferredMedia

internal object MultipleHomeSources {
    fun active(context: Context): List<MainAPI> {
        val current = com.lagradost.cloudstream3.utils.DataStoreHelper.currentHomePage
        return if (current == CombinedHomeApi.NAME) selected(context)
        else listOfNotNull(com.lagradost.cloudstream3.APIHolder.getApiFromNameNull(current)).filter { it.hasMainPage }
    }
    fun selected(context: Context): List<MainAPI> {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val names = prefs.getStringSet("home_selected_sources", emptySet()).orEmpty()
        return context.filterProviderByPreferredMedia().filter {
            it.hasMainPage && (prefs.getBoolean("home_all_sources", false) || it.name in names)
        }.distinctBy { it.name }
    }

}
