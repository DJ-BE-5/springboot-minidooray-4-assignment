import com.nhn.edu.springboot.minidooray.entity.Account;
import com.nhn.edu.springboot.minidooray.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//AccountResitory가 특정 Account을 반환하도록 MOCK된 경우,
//getAccountId 엔드 포인트가 예상된 JSON응답을 반환하는 테스트케이스입니다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class AccountControllerMockBeanTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @Test
    void testGetAccountId() throws Exception {
        Account testAccount = new Account("testId",  "test@naver.com", "testSalt", "testSaltedPassword");

        given(accountRepository.findById("testAccountId")).willReturn(Optional.of(testAccount));

        mockMvc.perform(get("/account/testAccountId"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("testId"))
                .andExpect(jsonPath("$.emal").value("test@naver.com"))
                .andExpect(jsonPath("$.password").doesNotExist());

    }
}


