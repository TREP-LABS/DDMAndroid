package com.treplabs.ddm

/**
 * @author Rasheed Sulayman.
 */

object Constants {

    object FireStorePaths {
        const val SYMPTOMS = "symptoms"
        const val CONDITIONS = "conditions"
        const val USERS = "users"
    }

    object CloudStoragePaths {
        const val IMAGES = "images"
        const val PROFILE_PICTURE = "profile_picture"
    }

    object APIDataKeys{
        const val NAME = "name"
        const val KEY = "key"
    }

    object Misc {
        const val AGENT_LINK = "Agent Link"
        const val DEFAULT_FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        const val DEFAULT_PHOTO_EXTENSION = ".jpg"
        const val FILE_PROVIDER_AUTHORITY = "${BuildConfig.APPLICATION_ID}.fileprovider"
    }

}
