package org.algotn.website.utils

import jakarta.servlet.http.HttpServletRequest

class IPUtils {

    companion object {
        fun getClientIP(request: HttpServletRequest): String {
            val xForwardedFor = request.getHeader("X-Forwarded-For")
            return if (xForwardedFor == null) {
                request.remoteAddr
            } else {
                val commaIndex = xForwardedFor.indexOf(',')
                if (commaIndex == -1) {
                    xForwardedFor
                } else {
                    xForwardedFor.substring(0, commaIndex)
                }
            }
        }
    }
}