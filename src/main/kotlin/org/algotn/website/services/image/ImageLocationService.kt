package org.algotn.website.services.image

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Service

@Service
class ImageLocationService {

    @Autowired
    val fileSystemRepository: ImageSystemRepository? = null

    fun save(content: ByteArray, imageName: String): String {
        return fileSystemRepository!!.save(content, imageName)
    }

    fun findInFileSystem(location: String): FileSystemResource {
        return fileSystemRepository!!.findInFileSystem(location)
    }
}