package org.algotn.website.controllers.api.image

import org.algotn.website.services.image.ImageLocationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.InputStreamResource
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.nio.file.Files

@RestController
@RequestMapping("/api/image")
class ImageAPIController {

    @Autowired
    val fileLocationService: ImageLocationService? = null

    @GetMapping("/{id}")
    @ResponseBody
    fun getImage(@PathVariable id: String): ResponseEntity<InputStreamResource> {
        val fileSystemResource = fileLocationService!!.findInFileSystem(id)
        if (!fileSystemResource.exists()) {
            return ResponseEntity.notFound().build()
        }

        // MediaType includes jpeg, png, gif
        if (!fileSystemResource.filename.endsWith(".png") && !fileSystemResource.filename.endsWith(".jpg")
            && !fileSystemResource.filename.endsWith(".jpeg") && !fileSystemResource.filename.endsWith(".gif")
        ) {
            return ResponseEntity.ok().body(InputStreamResource(fileSystemResource.inputStream))
        }

        val contentType = MediaType.parseMediaType(Files.probeContentType(fileSystemResource.file.toPath()))

        return ResponseEntity.ok().contentType(contentType).body(InputStreamResource(fileSystemResource.inputStream))
    }
}