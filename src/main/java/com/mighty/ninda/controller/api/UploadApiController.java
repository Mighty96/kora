package com.mighty.ninda.controller.api;

import com.mighty.ninda.utils.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class UploadApiController {

    private final S3Uploader s3Uploader;

    @PostMapping("/api/upload")
    public String upload(MultipartHttpServletRequest multipartFile, HttpServletResponse response) throws IOException {

        MultipartFile file = multipartFile.getFile("upload");
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        String fileUrl = s3Uploader.upload(file, fileName, LocalDate.now().toString());
        PrintWriter printWriter = response.getWriter(); // 서버로 파일 전송 후 이미지 정보 확인을 위해 filename, uploaded, fileUrl 정보를 response 해주어야 함
        printWriter.println("{\"filename\" : \"" + fileName + "\", \"uploaded\" : 1, \"url\":\"" + fileUrl + "\"}");
        printWriter.flush();

        return null;
    }

}
