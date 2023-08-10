package org.algotn.website.services.tests

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service

@Service
class TestLocationService {

    @Autowired
    val fileSystemRepository: TestSystemRepository? = null

    fun save(content: ByteArray, fileName: String): String {
        return fileSystemRepository!!.save(content, fileName)
    }

    fun findInFileSystem(location: String): FileSystemResource {
        return fileSystemRepository!!.findInFileSystem(location)
    }
}