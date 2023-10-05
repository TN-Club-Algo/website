package org.algotn.api.problem.data

import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import java.io.File

class SampleFiles(problem: Problem) {

    val samples = arrayListOf<Triple<String, String, String>>()

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
                    if (File(
                            it.parentFile,
                            it.nameWithoutExtension + ".exp"
                        ).exists()
                    ) {
                        Chili.logger.debug("Found explanation file: ${it.name}")
                        samples.add(
                            Triple(
                                it.readText(),
                                File(it.parentFile, it.nameWithoutExtension + ".ans").readText(),
                                File(it.parentFile, it.nameWithoutExtension + ".exp").readText()
                            )
                        )
                    }else{
                        Chili.logger.debug("Explanation file ${it.name} not found")
                        samples.add(
                            Triple(
                                it.readText(),
                                File(it.parentFile, it.nameWithoutExtension + ".ans").readText(),
                                ""
                            )
                        )
                    }
                }
            }
        }
    }
}