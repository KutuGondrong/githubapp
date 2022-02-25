package com.kutugondrong.github.helper

import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.RecordedRequest
import java.io.InputStreamReader

class MockServerDispatcher {

    internal inner class RequestDispatcher : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            val path: String ?= request.path
            return if (path?.contains("search/users?per_page") == true) {
                MockResponse().setResponseCode(200)
                    .setBody(getJsonContent("fake_data_list_user.json"))
            }
            else if (path?.contains("users/") == true && path.contains("/repos")) {
                MockResponse().setResponseCode(200)
                    .setBody(getJsonContent("fake_data_list_repo.json"))
            }
            else if (path?.contains("users/") == true) {
                MockResponse().setResponseCode(200)
                    .setBody(getJsonContent("fake_data_detail_user.json"))
            }
            else {
                MockResponse().setResponseCode(400)
            }
        }
    }

    internal inner class ErrorDispatcher : Dispatcher() {

        override fun dispatch(request: RecordedRequest): MockResponse {
            return MockResponse().setResponseCode(400)
        }
    }

    private fun getJsonContent(fileName: String): String {
        return InputStreamReader(this.javaClass.classLoader!!.getResourceAsStream(fileName)).use { it.readText() }
    }
}