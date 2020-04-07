package lab.march.diary.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

fun main() {
    val remoteConfig = Firebase.remoteConfig
    val settings = remoteConfigSettings {
        minimumFetchIntervalInSeconds = 3600
    }
    remoteConfig.setConfigSettingsAsync(settings)

    /*remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)*/
}