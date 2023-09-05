package org.algotn.api.problem.data

import org.algotn.api.problem.Problem
import org.algotn.api.tests.TestType
import java.io.File

class SecretFiles(problem: Problem) {

    // names of secret test files (in, ans)
    val secretFiles = arrayListOf<String>()

    val secretTestsTypes = mutableMapOf<String, TestType>()

    init {
        val secretFolder = "${problem.getDirectory()}data/secret/"
        val secretFolderFile = File(secretFolder)
        if (secretFolderFile.exists()) {
            secretFolderFile.listFiles()?.forEach {
                // Input/Output
                if (it.isFile && it.extension == "in" && File(
                        it.parentFile,
                        it.nameWithoutExtension + ".ans"
                    ).exists()
                ) {
                    secretFiles.add(it.nameWithoutExtension)
                    secretTestsTypes[it.nameWithoutExtension] = TestType.INPUT_OUTPUT
                }

                // Scoring .scoring.in

                // Custom
            }
        }
    }
}