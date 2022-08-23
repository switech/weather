package com.weather.app

object Constants {

    interface RequestPermission {
        companion object {
            const val REQUEST_GALLERY = 1000
            const val REQUEST_CAMERA = 1001
        }
    }

    interface RequestCode {
        companion object {
            const val REQUEST_FROM_EMAIL_ADAPTER = 1002
            const val REQUEST_FROM_PHONE = 1003
            const val REQUEST_FROM_CONTACT_REQUEST_ADAPTER = 1004
            const val GPS_REQUEST = 1005
            const val RC_SIGN_IN = 1006
            const val REQUEST_DRAG_DROP_MAP = 1007
        }
    }

    interface TimeOut {
        companion object {
            const val SPLASH_TIME_OUT = 2000
            const val IMAGE_UPLOAD_CONNECTION_TIMEOUT = 120
            const val IMAGE_UPLOAD_SOCKET_TIMEOUT = 120
            const val SOCKET_TIME_OUT = 60
            const val CONNECTION_TIME_OUT = 60
        }
    }

    interface UrlPath {
        companion object {
            const val BASE_URL = "https://api.openweathermap.org/"

            const val CURRENT_WEATHER = "data/2.5/weather"
            const val FORECAST_WEATHER = "data/2.5/forecast"

            const val COUNTRY_LIST = "user/countrylist"

        }
    }

    interface LocalPath {
        companion object {
            const val IMAGE = ""
        }
    }


    interface ErrorClass {
        companion object {
            const val CODE = "code"
            const val STATUS = "status"
            const val MESSAGE = "message"
            const val DEVELOPER_MESSAGE = "developerMessage"
        }
    }

    interface PARAM {
        companion object {
            const val APP_ID = "appId"
            const val LATITUDE = "lat"
            const val LONGITUDE = "lon"
            const val UNITS = "units"
        }
    }

    interface ResponseCode {
        companion object {
            const val CODE_200 = 200
            const val CODE_204 = 204
            const val CODE_500 = 500
            const val CODE_400 = 400
            const val CODE_403 = 403
            const val CODE_404 = 404
            const val CODE_401 = 401
            const val SESSION_EXPIRED = "-1"
        }
    }
}