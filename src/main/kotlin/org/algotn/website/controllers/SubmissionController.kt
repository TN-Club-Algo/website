package org.algotn.website.controllers

import org.algotn.api.Chili
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.nio.file.Files
import java.util.*
import kotlin.collections.ArrayList

@Controller
class SubmissionController {

    @GetMapping("/submit")
    fun seeform(): String{
        return "submit"
    }
    @PostMapping("/submit")
    fun sendform(
        @RequestParam("lang") lang: String,
        @RequestParam("prog") prog: String,
        @RequestParam("files") files: ArrayList<MultipartFile>): String {
        println("yeah");
        println(lang);
        if (files.size==0){
            println(prog)
        }else{
            files.stream().forEach { file ->
                println(file.originalFilename);
                val file_copy = File(file.originalFilename);
                //               @todo save right place
                file_copy.createNewFile()
                file_copy.writeText(file.inputStream.readBytes().decodeToString())

                print(file.inputStream.readBytes().decodeToString())
            }
//            println(files.originalFilename)
//            print(files.inputStream.readBytes().decodeToString())
        }
        Chili.getRedisInterface().client
        return "/submit";
    }
}