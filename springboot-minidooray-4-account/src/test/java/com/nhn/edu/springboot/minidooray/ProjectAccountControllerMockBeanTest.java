package com.nhn.edu.springboot.minidooray;

import com.nhn.edu.springboot.minidooray.entity.ProjectAccount;
import com.nhn.edu.springboot.minidooray.repository.ProjectAccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//ProjectAccountResitory가 특정 ProjectAccount을 반환하도록 모의된 경우,
//getporjectAccountId 엔드 포인트가 예상된 JSON응답을 반환하는 테스트케이스
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class ProjectAccountControllerMockBeanTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectAccountRepository projectAccountRepository;

    @Test
    void testGetProjectAccountId() throws Exception {
        long testProjectAccountId = 10010;
        long projectId = 1000;
        ProjectAccount testProjectAccount = new ProjectAccount(testProjectAccountId, ProjectAccount.Auth.ADMIN,projectId);

        given(projectAccountRepository.findById(testProjectAccountId)).willReturn(Optional.of(testProjectAccount));

        mockMvc.perform(get("/account"+ testProjectAccountId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.projectAccountId").value(10010))
                .andExpect(jsonPath("$.auth").value(ProjectAccount.Auth.ADMIN))
                .andExpect(jsonPath("$.projectId").value(1000));

    }
}
