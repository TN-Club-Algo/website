package org.algotn.api.problem.reader

import org.algotn.api.problem.Problem

abstract class ProblemReader {

    abstract fun readProblem(any: Any): Problem
}