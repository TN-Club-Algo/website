package org.algotn.api.problem.reader.award

import org.algotn.api.problem.Problem
import org.algotn.api.problem.awards.StringProblemAward
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration
import java.io.File

class AwardReader {

    companion object {

        fun readAwards(problem: Problem) {
            val awardsFile = File("${problem.getDirectory()}${Problem.PROBLEM_AWARDS_FILE_NAME}")

            if (awardsFile.exists()) {
                val yamlConfig = YamlConfiguration.loadConfiguration(awardsFile)
                yamlConfig.getKeys(false).forEach { awardKey ->
                    val typeString = yamlConfig.getString("$awardKey.type")
                    val name = yamlConfig.getString("$awardKey.name") ?: return@forEach

                    when (typeString) {
                        "string" -> {
                            val award = StringProblemAward(problem.slug, -1)
                            award.award = yamlConfig.getString("$awardKey.award")
                            problem.awards[name] = award
                        }

                        else -> {
                            return@forEach
                        }
                    }
                }
            }
        }
    }
}