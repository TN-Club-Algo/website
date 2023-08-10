package org.algotn.website.services.tests

import org.algotn.api.Chili
import org.springframework.core.io.FileSystemResource
import org.springframework.stereotype.Repository
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths


@Repository
class TestSystemRepository {

    companion object {
        val TESTS_LOCATION = Chili.SAVE_LOCATION + "/tests/"
    }

    fun save(content: ByteArray, fileName: String): String {
        val newFile: Path = Paths.get(TESTS_LOCATION + fileName)
        Files.createDirectories(newFile.parent)

        Files.write(newFile, content)

        return newFile.toAbsolutePath().toString()
    }

    @Suppress("NAME_SHADOWING")
    fun findInFileSystem(location: String): FileSystemResource {
        var location = location
        if (!location.startsWith(TESTS_LOCATION)) location = TESTS_LOCATION + location
        return FileSystemResource(Paths.get(location))
    }
}