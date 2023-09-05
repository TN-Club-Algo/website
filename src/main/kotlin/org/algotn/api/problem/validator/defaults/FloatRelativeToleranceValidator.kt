package org.algotn.api.problem.validator.defaults

import org.algotn.api.problem.validator.ProblemValidator

class FloatRelativeToleranceValidator(val tolerance: Float = 1e-6f) : ProblemValidator()