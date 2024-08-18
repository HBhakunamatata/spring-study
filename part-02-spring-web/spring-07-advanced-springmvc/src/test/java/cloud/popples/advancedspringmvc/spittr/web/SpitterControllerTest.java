package cloud.popples.advancedspringmvc.spittr.web;

import cloud.popples.advancedspringmvc.spittr.pojo.Spitter;
import cloud.popples.advancedspringmvc.spittr.repository.SpitterRepository;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class SpitterControllerTest {

    @Test
    public void testRegisterForm() throws Exception {
        SpitterRepository mockRepository = mock(SpitterRepository.class);

        SpitterController mockController = new SpitterController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(mockController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/spitter/register"))
                .andExpect(MockMvcResultMatchers.view().name("registerForm"));
    }

    @Test
    public void shouldProcessRegistration() throws Exception {
        SpitterRepository mockRepository = mock(SpitterRepository.class);
        Spitter unsaved = new Spitter("jbauer", "24hours", "Jack", "Bauer", "d");
        Spitter saved = new Spitter(24L, "jbauer", "24hours", "Jack", "Bauer", "d");
        when(mockRepository.save(unsaved)).thenReturn(saved);

        SpitterController controller = new SpitterController(mockRepository);
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        mockMvc.perform(fileUpload("/spitter/register")
                .file("profilePicture", "aaa".getBytes(StandardCharsets.UTF_8))
                .param("username", "jbauer")
                .param("password", "24hours")
                .param("firstName", "Jack")
                .param("lastName", "Bauer"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/spitter/jbauer?id=24")
        );
        verify(mockRepository, atLeastOnce()).save(unsaved);

    }
}
