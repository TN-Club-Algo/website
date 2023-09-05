package org.algotn.api.problem.validator

import org.algotn.api.problem.validator.defaults.*
import java.util.*

class Validators {

    private val validators = TreeMap<String, ProblemValidator>(String.CASE_INSENSITIVE_ORDER)

    init {
        // Not used for now
        validators["case_sensitive"] = CaseSensitiveValidator()
        validators["space_change_sensitive"] = SpaceChangeSensitiveValidator()
        validators["float_tolerance"] = FloatToleranceValidator()
        validators["float_relative_tolerance"] = FloatRelativeToleranceValidator()
        validators["float_absolute_tolerance"] = FloatAbsoluteToleranceValidator()
    }

    fun getValidator(name: String): ProblemValidator? = validators[name]
}