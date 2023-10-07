package com.tokyovending.TokyoVending.integrations;

import com.tokyovending.TokyoVending.dtos.UserDto;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateUserAndRetrieveUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser1");
        userDto.setPassword("password");
        userDto.setEmail("testuser1@example.com");
        userDto.setEnabled(true);
        userDto.setApikey("apikey1");

        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/testuser1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddAndRemoveUserAuthority() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser2");
        userDto.setPassword("password");
        userDto.setEmail("testuser2@example.com");
        userDto.setEnabled(true);
        userDto.setApikey("apikey2");

        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.post("/users/testuser2/authorities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"authority\": \"ROLE_ADMIN\"}"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/testuser2/authorities/ROLE_ADMIN"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    @Transactional
    public void testDeleteUser() throws Exception {
        UserDto userDto = new UserDto();
        userDto.setUsername("testuser3");
        userDto.setPassword("password");
        userDto.setEmail("testuser3@example.com");
        userDto.setEnabled(true);
        userDto.setApikey("apikey3");

        String userDtoJson = objectMapper.writeValueAsString(userDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(userDtoJson))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/testuser3"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.get("/users/testuser3"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}







