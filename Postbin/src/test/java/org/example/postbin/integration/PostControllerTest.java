package org.example.postbin.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();;

    @Test
    public void test_get_post_by_hash_url_and_should_return_200() throws Exception {
        final String hash = "12345678";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/" + hash))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());
    }


    @Test
    public void test_get_post_by_hash_url_and_should_return_404() throws Exception {
        final String hash = "abracadabra";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post/" + hash))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void test_get_posts_and_should_return_200() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/post")
                        .contentType(APPLICATION_JSON)
                        .param("page", "0")
                        .param("size", "1")// Укажите параметры запроса
                        .param("title", "")
                        .param("nickname", "j"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void test_save_post_and_should_return_200() throws Exception {
        final String webPostJson = """
                {
                  "title": "Test1",
                  "description": "Test1",
                  "name": "opa",
                  "surname": "hopa",
                  "nickname": "hipa",
                  "deletedAfterSomeDays": 1
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(webPostJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void test_save_post_and_should_return_400() throws Exception {
        final String webPostJson = """
                {
                  "title": "Te",
                  "description": "Test1",
                  "name": "opa",
                  "surname": "hopa",
                  "nickname": "hipa",
                  "deletedAfterSomeDays": 1
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(webPostJson))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}