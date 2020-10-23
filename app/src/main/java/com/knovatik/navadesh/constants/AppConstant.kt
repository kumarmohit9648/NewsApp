package com.knovatik.navadesh.constants

import com.otaliastudios.cameraview.PictureResult
import com.otaliastudios.cameraview.VideoResult
import java.text.SimpleDateFormat
import java.util.*

class AppConstant {

    companion object {
        const val IS_LOGIN = "is_login"
        const val AUTH_TOKEN = "auth_token"
        const val IS_FIRST_TIME_OPEN = "is_first_time_open"
        const val TITLE_KEY = "title_key"
        const val CAMERA_TYPE = "camera_type"
        const val PHOTO = "photo"
        const val VIDEO = "video"
        const val CATEGORY_ID = "category_id"
        const val SUB_CATEGORY_ID = "sub_category_id"
        const val VIDEO_ID = "video_id"

        const val FRAGMENT_ID = "fragment_id"
        const val FRAGMENT_TITLE = "FRAGMENT_TITLE"

        const val FRAGMENT_SUB_CATEGORY = "fragment_sub_category"
        const val FRAGMENT_TIME_PASS = "fragment_time_pass"
        const val FRAGMENT_CITIZEN_REPORTER = "fragment_citizen_reporter"
        const val FRAGMENT_OTHER = "fragment_other"

        const val SECTION_NAME = "section_name"
        const val SECTION_ID = "section_id"

        const val IS_SOCIAL = "is_social"
        const val USERNAME = "username"
        const val EMAIL = "email"
        const val MOBILE_NUMBER = "mobile_number"

        var pictureResult: PictureResult? = null
        var videoResult: VideoResult? = null

        const val PROFILE_DETAIL = "profile_detail"

        val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy_hh:mm", Locale.ENGLISH)

    }
}