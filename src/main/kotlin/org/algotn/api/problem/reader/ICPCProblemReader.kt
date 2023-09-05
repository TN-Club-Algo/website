package org.algotn.api.problem.reader

import org.algotn.api.Chili
import org.algotn.api.problem.Problem
import org.algotn.api.problem.ProblemType
import org.algotn.api.problem.validator.ProblemValidator
import org.bspfsystems.yamlconfiguration.file.YamlConfiguration
import java.io.File

class ICPCProblemReader : ProblemReader() {

    override fun readProblem(any: Any): Problem {
        if (any is File) { // config file yml
            val folder = any.parentFile
            val yamlConfig = YamlConfiguration.loadConfiguration(any)

            val source = yamlConfig.getString("source") ?: throw IllegalArgumentException("Source must be specified")
            val author = yamlConfig.getString("author") ?: throw IllegalArgumentException("Author must be specified")
            val rightsOwner =
                yamlConfig.getString("rights_owner") ?: throw IllegalArgumentException("Right owners must be specified")

            val license = yamlConfig.getString("license") ?: "None"
            val validator = yamlConfig.getString("validator")
            val allowMultipleFiles = yamlConfig.getBoolean("allow_multiple_files", false)
            val difficulty = yamlConfig.getString("difficulty") ?: "Inconnue"

            val problem = Problem(
                folder.nameWithoutExtension,
                name = yamlConfig.getString("name") ?: Problem.DEFAULT_NAME,
                type = ProblemType.fromString(yamlConfig.getString("type") ?: Problem.DEFAULT_TYPE.type),
                source,
                author,
                rightsOwner,
                license,
                yamlConfig.getStringList("keywords").toTypedArray(),
                allowMultipleFiles = allowMultipleFiles,
                difficulty = difficulty
            )
            if (yamlConfig.contains("limits")) {
                val limits = HashMap<String, Any>()
                yamlConfig.getConfigurationSection("limits")?.getKeys(false)?.forEach {
                    limits[it] = yamlConfig.get("limits.$it") as Any
                }

                problem.limits.putAll(limits)
            }
            if (yamlConfig.contains("validator")) {
                readValidators(problem, validator!!)
            }
            return problem
        }
        throw IllegalArgumentException("Argument must be a File")
    }

    private fun readValidators(problem: Problem, validators: String) {
        val split = validators.split(" ")

        var previousProblemValidator: ProblemValidator? = null
        for (i in split.indices) {
            val problemValidator = Chili.get().validators.getValidator(split[i])
            if (problemValidator == null) {
                if (previousProblemValidator == null) throw IllegalArgumentException("Validator not found")
                else {
                    problem.validators[previousProblemValidator] =
                        problem.validators[previousProblemValidator]!! + split[i]
                }
            } else {
                previousProblemValidator = problemValidator
                problem.validators[problemValidator] = arrayOf()
            }
        }
    }
}