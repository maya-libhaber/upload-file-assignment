package com.maya.back_end.controller;

import com.maya.back_end.controllers.FileController;
import com.maya.back_end.controllers.RecordInputStreamParser;
import com.maya.back_end.service.RecordPlainTextFileParser;
import com.maya.back_end.service.RecordPlainTextLineDeserializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@WebMvcTest(FileController.class)
class FileControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private RecordInputStreamParser recordInputStreamParser;

    @Test
    void uploadFile_positive() throws Exception {

        // GIVEN
        ClassPathResource dataFile = new ClassPathResource("DataFile.txt", this.getClass().getClassLoader());

        MockMultipartFile mockMultipartFile =
                new MockMultipartFile("file",
                        "DataFile.txt",
                        MediaType.TEXT_PLAIN_VALUE,
                        dataFile.getInputStream());

        // WHEN
        /* intentionally empty */

        // INVOKE
        mvc.perform(multipart("/rest/submit")
                .file(mockMultipartFile)
                .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(13)))
                .andExpect(jsonPath("$[0].date", is("20190210")));

    }

    @TestConfiguration
    static class FileControllerTestConfiguration {

        @Bean
        public RecordInputStreamParser recordInputStreamParser() {
            return new RecordPlainTextFileParser(new RecordPlainTextLineDeserializer());
        }

    }

}