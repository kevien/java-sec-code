package org.joychou.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author: JoyChou (joychou@joychou.org)
 * @date:   2018.08.15
 * @desc:   Java file upload
 */

@Controller
@RequestMapping("/file")
public class FileUpload {

    // Save the uploaded file to this folder
    private static String UPLOADED_FOLDER = "/tmp/";

    @GetMapping("/")
    public String index() {
        return "upload";
        //return upload.html;
    }

    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            // 赋值给uploadStatus.html里的动态参数message
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:/file/status";
        }
        // fix code 判断上传文件的后缀
//        if (!file.getOriginalFilename().endsWith(".jpg") && !file.getOriginalFilename().endsWith(".png") && !file.getOriginalFilename().endsWith(".gif") &&  !file.getOriginalFilename().endsWith(".jpeg")) {
//            redirectAttributes.addFlashAttribute("message", "Please up load jpg,gif,png or jpeg");
//            return "redirect:/file/status";
//        }

        try {
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            //fix code 随机文件名
            //Path path = Paths.get(UPLOADED_FOLDER + UUID.randomUUID().toString() + file.getOriginalFilename());


            Files.write(path, bytes);

            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + UPLOADED_FOLDER + file.getOriginalFilename() + "'");

        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("message", "upload failed");
            e.printStackTrace();
            return "uploadStatus";
        }

        return "redirect:/file/status";
    }

    @GetMapping("/status")
    public String uploadStatus() {
        return "uploadStatus";
    }

}
