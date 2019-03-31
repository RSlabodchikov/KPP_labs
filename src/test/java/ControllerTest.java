import com.netcracker.vmsis.kpp.controller.LineController;
import com.netcracker.vmsis.kpp.service.LineService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LineController.class, LineService.class})
@AutoConfigureMockMvc
public class ControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void test400() throws Exception {
        int status = mockMvc.perform(get("/lab2/line")
                .param("params","1,2,3,4"))
                .andReturn().getResponse().getStatus();
        Assertions.assertThat(status).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void test500() throws Exception {
        int status = mockMvc.perform(get("/lab2/line")
                .param("x1","5")
                .param("x2","2")
                .param("y1","3")
                .param("y2","3"))
                .andReturn().getResponse().getStatus();
        Assertions.assertThat(status).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }


}
