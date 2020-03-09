package com.ijrobotics.ijschoolmanageradministrationservice.service.mapper;

import com.ijrobotics.ijschoolmanageradministrationservice.domain.Attachments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AttachmentFileMapper {
    private final Logger log = LoggerFactory.getLogger(AttachmentFileMapper.class);
    public Set<Attachments> multiPartFilesToDocuments(List<MultipartFile> files){
        return files.stream()
            .map((this::multiPartFileToDocument))
            .collect(Collectors.toSet());
    }

    public Attachments multiPartFileToDocument(MultipartFile file) {
        Attachments document = new Attachments();
        document.setTitle(file.getOriginalFilename());
        document.setSize(file.getSize());
        document.setMimeType(file.getContentType());
        try {
            document.addContent(file.getBytes());
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return document;
    }
}
