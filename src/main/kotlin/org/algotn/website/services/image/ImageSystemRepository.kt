package org.algotn.website.services.image

import org.algotn.api.Chili
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*


@Repository
class ImageSystemRepository {

    companion object {
        val IMAGE_LOCATION = Chili.SAVE_LOCATION + "/images/"
    }

    fun save(content: ByteArray, imageName: String): String {
        val newFile: Path = Paths.get((IMAGE_LOCATION + Date().time) + "-" + imageName)
        Files.createDirectories(newFile.parent)

        Files.write(newFile, content)

        return newFile.toAbsolutePath().toString()
    }

    @Suppress("NAME_SHADOWING")
    fun findInFileSystem(location: String): FileSystemResource {
        var location = location
        if (!location.startsWith(IMAGE_LOCATION)) location = IMAGE_LOCATION + location
        return FileSystemResource(Paths.get(location))
    }
}