package com.mighty.ninda.controller.api;

import com.mighty.ninda.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UploadApiController {

    private final S3Uploader s3Uploader;

    @PostMapping("/api/upload")
    public String upload(@RequestParam("data")MultipartFile multipartFile) throws IOException {

        return s3Uploader.upload(multipartFile, "static");
    }

}
