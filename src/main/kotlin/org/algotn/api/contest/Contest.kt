package org.algotn.api.contest;

import org.algotn.api.Chili
import org.algotn.api.database.data.Data
import org.algotn.api.problem.Problem
import org.algotn.api.utils.DateUtils
import org.algotn.api.utils.slugify
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

class Contest : Data {

    // problem and it max score
    val problems = HashMap<String, Int>()
    val problemSuccessCount = HashMap<String, Int>()
    val registeredUser = HashSet<String>()

    var uuid: String = UUID.randomUUID().toString()
    var name: String = "No Name"
    var description: String? = null
    var organisator: String = "AlgoTN"
    var beginning: String = ""
    var end: String = ""

    var leaderboardKey = ""
    var successCountKey = ""

    override fun postInit() {
        leaderboardKey = "leaderboard:contest:${name.slugify()}:set"
        successCountKey = "leaderboard:contest:${name.slugify()}:successCount"
    }

    override fun isUUIDData(): Boolean {
        return true
    }

    override fun isUserData(): Boolean {
        return false
    }

    fun addUserToLeaderboard(email: String) {
        val set = Chili.getRedisInterface().client.getScoredSortedSet<String>(leaderboardKey)
        for (problem in problems) {
            set.add(0.0, problem.key + ":" + email)
        }
    }

    fun updateUserScore(problemSlug: String, score: Double, email: String) {
        val set = Chili.getRedisInterface().client.getScoredSortedSet<String>(leaderboardKey)
        set.add(score, "$problemSlug:$email")
    }

    fun getUserScore(email: String): Double {
        val set = Chili.getRedisInterface().client.getScoredSortedSet<String>(leaderboardKey)
        var score = 0.0
        for (problem in problems) {
            score += set.getScore(problem.key + ":" + email)
        }
        return score
    }

    fun computeProblemScore(problemSlug: String): Double {
        return max(
            problems[problemSlug]!!.toDouble() / 2,
            problems[problemSlug]!!.toDouble() - Chili.getRedisInterface().client.getMap<String, Int>(successCountKey)
                .getOrDefault(problemSlug, 0) * (problems[problemSlug]!!.toDouble() / 100)
        )
    }

    fun addProblem(problem: ContestProblem) {
        problems[problem.slug] = problem.score
        problemSuccessCount[problem.slug] = 0
    }

    fun removeProblem(problem: Problem) {
        problems.remove(problem.slug)
        problemSuccessCount.remove(problem.slug)
    }

    fun getBeginningFormatted(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val parisTimeZone = TimeZone.getTimeZone("Europe/Paris")
        sdf.timeZone = parisTimeZone

        val date = sdf.parse(beginning)

        val sdf2 = SimpleDateFormat("dd MMMM à HH:mm", Locale("fr", "FR"))
        sdf2.timeZone = parisTimeZone
        return sdf2.format(date)
    }

    fun getDurationFormatted(): String {
        return getDurationFormatted(DateUtils.dateToLong(end) - DateUtils.dateToLong(beginning))
    }

    fun getDurationFormatted(duration: Long): String {
        val days = duration / (1000 * 60 * 60 * 24)
        val hours = duration / (1000 * 60 * 60) % 24
        val minutes = duration / (1000 * 60) % 60
        val seconds = duration / 1000 % 60

        var result = ""
        if (days != 0L) {
            result += "$days jours "
        }
        if (hours != 0L) {
            result += "${hours}h "
        }
        if (minutes != 0L) {
            result += "${minutes}m "
        }
        if (seconds != 0L) {
            result += "${seconds}s "
        }
        return result
    }

    fun getRemainingDurationFormatted(): String {
        return getDurationFormatted(DateUtils.dateToLong(end) - System.currentTimeMillis())
    }
}
