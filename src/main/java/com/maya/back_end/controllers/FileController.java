package com.maya.back_end.controllers;

import com.maya.back_end.dto.Record;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;


@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping(path = "rest")
public class FileController {

    private RecordInputStreamParser recordInputStreamParser;

    @Autowired
    public FileController(RecordInputStreamParser recordInputStreamParser) {
        this.recordInputStreamParser = recordInputStreamParser;
    }

    @PostMapping(path = "/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity uploadFile(@RequestParam(name = "file") MultipartFile recordFile) {

        if (nonNull(recordFile)) {

            try {

                log.debug("Got parsing request for file name: {}", recordFile.getOriginalFilename());

                Collection<Record> result = recordInputStreamParser.parse(recordFile.getInputStream());

                log.debug("File name: {} processed successfully", recordFile.getOriginalFilename());

                return ResponseEntity.ok(result);

            } catch (RecordParsingException | IOException rpe) {
                log.error("Failed to process file name: {}", recordFile.getOriginalFilename(), rpe);

                return ResponseEntity.internalServerError().body(
                        "Failed to process records file, please make sure to provide a properly formatted record file");
            }

        }

        return ResponseEntity.noContent().build();
    }

}

