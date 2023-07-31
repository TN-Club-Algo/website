package org.algotn.website.auth

import java.util.*

class TestUserData(var email: String) : org.algotn.api.database.data.Data {

    constructor() : this("")

    val exampleData = Random().nextInt()
}