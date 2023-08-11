package org.algotn.website.services.problem

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service

@Service
class ProblemLocationService {

    @Autowired
    val problemSystemRepository: ProblemSystemRepository? = null

    fun save(content: ByteArray, fileName: String): String {
        return problemSystemRepository!!.save(content, fileName)
    }

    fun findInFileSystem(location: String): FileSystemResource {
        return problemSystemRepository!!.findInFileSystem(location)
    }

    fun findSecretInFileSystem(problemFolderName: String, fileName: String): FileSystemResource {
        return problemSystemRepository!!.findInFileSystem("$problemFolderName/data/secret/$fileName")
    }
}