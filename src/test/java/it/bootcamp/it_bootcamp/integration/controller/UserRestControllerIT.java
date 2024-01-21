package it.bootcamp.it_bootcamp.integration.controller;

import it.bootcamp.it_bootcamp.integration.IntegrationTestBase;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static it.bootcamp.it_bootcamp.dto.UserDto.Fields.*;
import static it.bootcamp.it_bootcamp.integration.controller.util.StringGenerator.generateEmail;
import static it.bootcamp.it_bootcamp.integration.controller.util.StringGenerator.generateString;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
public class UserRestControllerIT extends IntegrationTestBase {
    private final MockMvc mockMvc;

    @Test
    void createUser_shouldReturnCreatedUserAndHttpStatus201() throws Exception {
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .param(name, "testName")
                        .param(secondName, "testSecondName")
                        .param(surname, "testSurname")
                        .param(email, "testemaiil@gmail.com")
                        .param(role, "CUSTOMER_USER")
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.email", is("testemaiil@gmail.com"))
                );
    }

    @Test
    void createUser_whenWhenNameIsWrittenInLatinLetters_shouldReturnHttpStatus400() throws Exception {
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .param(name, "testИмя")
                        .param(secondName, "testSecondName")
                        .param(surname, "testSurname")
                        .param(email, "testemaiil@gmail.com")
                        .param(role, "CUSTOMER_USER")
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors[0]", is("createUser: The field must contain only Latin letters!"))
                );
    }

    @Test
    void createUser_whenNameHasMoreThan20Characters_shouldReturnHttpStatus400() throws Exception {
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .param(name, generateString(25))
                        .param(secondName, "testSecondName")
                        .param(surname, "testSurname")
                        .param(email, "testemaiil@gmail.com")
                        .param(role, "CUSTOMER_USER")
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors[0]", is("name: Name cannot be longer than 20 characters!"))
                );
    }

    @Test
    void createUser_whenSecondNameIsWrittenInLatinLetters_shouldReturnHttpStatus400() throws Exception {
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .param(name, "testName")
                        .param(secondName, "testОтчество")
                        .param(surname, "testSurname")
                        .param(email, "testemaiil@gmail.com")
                        .param(role, "CUSTOMER_USER")
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors[0]", is("createUser: The field must contain only Latin letters!"))
                );
    }

    @Test
    void createUser_whenSecondNameHasMoreThan40Characters_shouldReturnHttpStatus400() throws Exception {
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .param(name, "testName")
                        .param(secondName, generateString(45))
                        .param(surname, "testSurname")
                        .param(email, "testemaiil@gmail.com")
                        .param(role, "CUSTOMER_USER")
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors[0]", is("secondName: Second name cannot be longer than 40 characters!"))
                );
    }

    @Test
    void createUser_whenSurnameIsWrittenInLatinLetters_shouldReturn() throws Exception {
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .param(name, "testИмя")
                        .param(secondName, "testSecondName")
                        .param(surname, "testФамилия")
                        .param(email, "testemaiil@gmail.com")
                        .param(role, "CUSTOMER_USER")
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors[0]", is("createUser: The field must contain only Latin letters!"))
                );
    }

    @Test
    void createUser_whenSurnameHasMoreThan40Characters_shouldReturnHttpStatus400() throws Exception {
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .param(name, "testName")
                        .param(secondName, "testSecondName")
                        .param(surname, generateString(45))
                        .param(email, "testemaiil@gmail.com")
                        .param(role, "CUSTOMER_USER")
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors[0]", is("surname: Surname cannot be longer than 40 characters!"))
                );
    }

    @Test
    void createUser_whenEmailIsValidAndHasMoreThan50Characters_shouldReturnHttpStatus400() throws Exception {
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .param(name, "testName")
                        .param(secondName, "testSecondName")
                        .param(surname, "Surname")
                        .param(email, generateEmail(55))
                        .param(role, "CUSTOMER_USER")
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors[0]", is("email: Email cannot be longer than 50 characters!"))
                );
    }

    @Test
    void createUser_whenEmailIsNotValid_shouldReturnHttpStatus400() throws Exception {
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .param(name, "testName")
                        .param(secondName, "testSecondName")
                        .param(surname, "Surname")
                        .param(email, "not valid email")
                        .param(role, "CUSTOMER_USER")
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors[0]", is("email: must be a well-formed email address"))
                );
    }

    @Test
    void createUser_whenOneParamIsNull_shouldReturnHttpStatus400() throws Exception {
        mockMvc.perform(post("/api/v1/users/create")
                        .contentType(APPLICATION_JSON)
                        .param(name, "testName")
                        .param(secondName, "testSecondName")
                        .param(surname, "Surname")
                        .param(email, "testMail@gmail.com")
                )
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.errors[0]", is("role: must not be null"))
                );
    }

    @Test
    void getUsers_whenAllParamsAreValid_shouldReturnHttpStatus200() throws Exception {
        mockMvc.perform(get("/api/v1/users")
                        .contentType(APPLICATION_JSON)
                        .param(name, "Alex")
                        .param(secondName, "Antonovich")
                        .param(surname, "Alexov")
                        .param(email, "alex1@gmail.com")
                        .param(role, "ADMINISTRATOR")
                )
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content[0].email", is("alex1@gmail.com"))
                );
    }
}
