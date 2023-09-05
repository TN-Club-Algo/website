package org.algotn.api.problem.data

import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import java.io.File

class SampleFiles(problem: Problem) {

    val samples = arrayListOf<Pair<String, String>>()

    init {
        val sampleFolder = "${problem.getDirectory()}data/sample/"
        Chili.logger.debug("Looking for sample files in $sampleFolder")
        val sampleFolderFile = File(sampleFolder)
        if (sampleFolderFile.exists()) {
            sampleFolderFile.listFiles()?.forEach {
                if (it.isFile && it.extension == "in" && File(
                        it.parentFile,
                        it.nameWithoutExtension + ".ans"
                    ).exists()
                ) {
                    Chili.logger.debug("Found sample file: ${it.name}")
                    samples.add(
                        Pair(
                            it.readText(),
                            File(it.parentFile, it.nameWithoutExtension + ".ans").readText()
                        )
                    )
                }
            }
        }
    }
}