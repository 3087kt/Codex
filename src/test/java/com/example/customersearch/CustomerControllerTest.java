package com.example.customersearch;

import com.example.customersearch.bl.service.CustomerService;
import com.example.customersearch.pl.CustomerController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class CustomerControllerTest {

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        CustomerService customerService = new CustomerService();
        customerService.init();
        mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(customerService)).build();
    }

    @Test
    public void shouldShowErrorMessageWhenCustomerDoesNotExist() throws Exception {
        mockMvc.perform(get("/customers/9999"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer-not-found"))
                .andExpect(model().attribute("errorMessage", "顧客情報が存在しません"))
                .andExpect(model().attributeExists("searchForm"));
    }

    @Test
    public void shouldShowErrorMessageWhenCustomerIdIsInvalid() throws Exception {
        mockMvc.perform(get("/customers/abc"))
                .andExpect(status().isOk())
                .andExpect(view().name("customer-not-found"))
                .andExpect(model().attribute("errorMessage", "顧客情報が存在しません"))
                .andExpect(model().attributeExists("searchForm"));
    }
}
