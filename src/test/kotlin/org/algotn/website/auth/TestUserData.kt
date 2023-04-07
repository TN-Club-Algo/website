package org.algotn.website.auth

import org.algotn.api.database.data.UserData
import java.util.*

class TestUserData(override var email: String) : UserData() {

    constructor() : this("")

    val exampleData = Random().nextInt()
}