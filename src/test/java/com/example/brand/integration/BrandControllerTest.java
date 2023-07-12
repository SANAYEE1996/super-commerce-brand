package com.example.brand.integration;

import com.example.brand.dto.BrandRegisterDto;
import com.example.brand.util.TestUtilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TestUtilService testUtilService;

    @RegisterExtension
    final RestDocumentationExtension restDocumentation = new RestDocumentationExtension("build/generated-snippets");

    @Value("${testUtil.fileLocation}")
    private String fileLocation;

    @BeforeEach
    void initial(RestDocumentationContextProvider restDocumentation) throws Exception {
        mockMvc = testUtilService.loginWithJwtToken(mockMvc,objectMapper,restDocumentation);
    }

    @DisplayName("브랜드 정보 저장")
    @Test
    public void productSaveTest() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "brandImage",
                "foxfox",
                "image/jpg",
                new FileInputStream(fileLocation+"/foxfox.jpg")
        );

        BrandRegisterDto dto = new BrandRegisterDto("underArmor@gmail.com","언더아머","3대 700이하는 입지마시오.","강남구 언주로 어딘가");

        MockMultipartFile mockDto = new MockMultipartFile(
                "BrandRegisterInfo",
                "BrandRegisterInfo",
                "application/json",
                objectMapper.writeValueAsString(dto).getBytes(StandardCharsets.UTF_8)
        );

        mockMvc.perform(multipart("/brand/register")
                        .file(file)
                        .file(mockDto))
                .andDo(document("brand/brandRegister",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
