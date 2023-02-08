package com.ayush.controller;
import com.ayush.model.User;
import com.ayush.repository.UserRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepo userRepo;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
   public void saveUserTest() throws Exception {
        User u=User.builder()
                .firstName("Ayush")
                .lastName("Tyagi")
                .emailId("a.t@bristlecone.com")
                .build();
        Mockito.when(userRepo.save(u)).thenReturn(u);
        MockHttpServletRequestBuilder builder= post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(this.objectMapper.writeValueAsString(u));
        mockMvc.perform(builder).andExpect(status().isOk())
                .andDo(print());


    }
    @Test
    public void getAllEmployeesTests() throws Exception {
        List<User> allemployees = new ArrayList<>();
        allemployees.add(User.builder().firstName("Ayush").lastName("Tyagi").emailId("a.t@bristlecone.com").build());
        allemployees.add(User.builder().firstName("Hamish").lastName("Green").emailId("h.g@hotmail.com").build());
        given(userRepo.findAll()).willReturn(allemployees);
        ResultActions response = mockMvc.perform(get("/api/v1/employees"));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",
                        is(allemployees.size())));
    }
    @Test
    public void getEmployeeByIdTests() throws Exception{
        long id=1L;
        User u=User.builder()
                .firstName("Ayush")
                .lastName("Tyagi")
                .emailId("a.t@bristlecone.com")
                .build();
        given(userRepo.findById(id)).willReturn(Optional.of(u));
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}",id));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(u.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(u.getLastName())))
                .andExpect(jsonPath("$.emailId", is(u.getEmailId())));



    }
    @Test
    public void givenInvalidEmployeeIdTests() throws Exception{
        long id =1L;
        User u=User.builder()
                .firstName("Ayush")
                .lastName("Tyagi")
                .emailId("a.t@bristlecone.com")
                .build();
        given(userRepo.findById(id)).willReturn(Optional.empty());
        ResultActions response = mockMvc.perform(get("/api/v1/employees/{id}",id));
        response.andExpect(status().isNotFound())
                .andDo(print());
    }
    @Test
    public void updateEmployeesTests() throws Exception{
        long id=1L;
        User u=User.builder()
                .firstName("Ayush")
                .lastName("Tyagi")
                .emailId("a.t@bristlecone.com")
                .build();
        User u1=User.builder()
                .firstName("Jayesh")
                .lastName("Sharma")
                .emailId("j.s@outlook.com")
                .build();
        given(userRepo.findById(id)).willReturn(Optional.of(u));
        given(userRepo.save(any(User.class)))
                .willAnswer((invocation)->invocation.getArgument(0));
        ResultActions response = mockMvc.perform(put("/api/v1/employees/{id}",id)
                                        .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(u1)));
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName" ,is(u1.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(u1.getLastName())))
                .andExpect(jsonPath("$.emailId", is(u1.getEmailId())));

    }
    @Test
    public void deleteEmployeesTests() throws Exception
    {
        User u=User.builder()
                .firstName("Ayush")
                .lastName("Tyagi")
                .emailId("a.t@bristlecone.com")
                .build();

        userRepo.delete(u);
        Mockito.verify(userRepo).delete(u);
    }
}