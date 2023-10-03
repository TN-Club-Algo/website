package org.algotn.api.problem

import org.algotn.api.Chili
import org.algotn.api.problem.awards.ProblemAward
import org.algotn.api.problem.data.SampleFiles
import org.algotn.api.problem.data.SecretFiles
import org.algotn.api.problem.reader.award.AwardReader
import org.algotn.api.problem.validator.ProblemValidator
import org.algotn.api.utils.slugify
import java.io.File

class Problem(
    val folderName: String,
    val name: String = DEFAULT_NAME,
    val type: ProblemType = DEFAULT_TYPE,
    val source: String,
    val author: String,
    val rightOwners: String,
    val license: String,
    val keywords: Array<String> = emptyArray(),
    val difficulty: String,
    val allowMultipleFiles: Boolean
) {

    companion object {
        val DEFAULT_NAME = "No Name"
        val DEFAULT_TYPE = ProblemType.PASS_FAIL

        val PROBLEM_STATEMENT_FILE_NAME = "problem.md"
        val PROBLEM_AWARDS_FILE_NAME = "awards.yml"
    }

    val limits = HashMap<String, Any>()
    val validators = HashMap<ProblemValidator, Array<String>>()
    val slug: String = name.slugify()

    val usedImages = ArrayList<String>()

    val sampleFiles = SampleFiles(this)
    val secretFiles = SecretFiles(this)

    val fullStatement: String

    // List of users emails
    val usersWhoSolved = HashSet<String>()

    // Awards to be given when solving the problem
    val awards: HashMap<String, ProblemAward> = HashMap()

    init {
        val statementFile = File("${getDirectory()}$PROBLEM_STATEMENT_FILE_NAME")
        fullStatement = if (statementFile.exists()) {
            statementFile.readText()
        } else {
            ""
        }

        AwardReader.readAwards(this)

        Chili.logger.debug("problem={} Found awards: {}", slug, awards)

        usersWhoSolved.addAll(Chili.getRedisInterface().client.getSet("problem-$slug-solved"))
    }

    fun getDirectory(): String {
        return "${Chili.SAVE_LOCATION}/problems/$folderName/"
    }

    fun getUsersWhoSolvedCount(): Int {
        return usersWhoSolved.size
    }

    fun getLimit(key: String): Any? {
        return limits[key]
    }

    fun getMemoryLimit(): Int {
        return getLimit("memory") as Int? ?: 0
    }

    fun getCompilationTimeLimit(): Int {
        return getLimit("compilation_time") as Int? ?: 0
    }

    fun getValidationTimeLimit(): Int {
        return getLimit("validation_time") as Int? ?: 0
    }

    fun getValidationMemoryLimit(): Int {
        return getLimit("validation_memory") as Int? ?: 0
    }

    fun getLimitsFormatted(): String {
        val builder = StringBuilder()
        if (getValidationTimeLimit() > 1000) {
            builder.append("Temps d'exécution : ${getValidationTimeLimit() / 1000} s\n")
        } else {
            builder.append("Temps d'exécution : ${getValidationTimeLimit()} ms\n")
        }
        builder.append("Mémoire d'exécution: ${getValidationMemoryLimit()} Mo\n")
        return builder.toString()
    }

    fun getImagePath(image: String): String {
        return "${getDirectory()}$image"
    }
}