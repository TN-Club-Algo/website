package org.algotn.website.controllers

import org.algotn.api.Chili
import org.algotn.api.submission.Submission
import org.algotn.website.auth.User
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
        @RequestParam("files") files: ArrayList<MultipartFile>): String{
        println("yeah");
        println(lang);
        if (files.size==0){
            println(prog)
        }else{
            if (files.size==1){
                val file = files[0]
                val fileCopy = File(file.originalFilename);
                fileCopy.createNewFile()
                fileCopy.writeText(file.inputStream.readBytes().decodeToString())
                val sub = Submission(UUID.randomUUID(),"",lang,fileCopy.path)
            }else{
                println("multiple files not supported")
            }
//            files.stream().forEach { file ->
//                println(file.originalFilename);
//                val fileCopy = File(file.originalFilename);
//                //               @todo save right place
//                fileCopy.createNewFile()
//                fileCopy.writeText(file.inputStream.readBytes().decodeToString())
//
//                print(file.inputStream.readBytes().decodeToString())
//            }
//            println(files.originalFilename)
//            print(files.inputStream.readBytes().decodeToString())
        }

        Chili.getRedisInterface().client
        return "/submit";
    }
}