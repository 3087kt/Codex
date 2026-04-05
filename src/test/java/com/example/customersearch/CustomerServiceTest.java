package com.example.customersearch;

import com.example.customersearch.bl.model.CustomerSearchCriteria;
import com.example.customersearch.bl.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CustomerServiceTest {

    private CustomerService customerService;

    @Before
    public void setUp() {
        customerService = new CustomerService();
        customerService.init();
    }

    @Test
    public void shouldProvide100Customers() {
        Assert.assertEquals(100, customerService.getCustomers().size());
    }

    @Test
    public void shouldSearchByPartialNameAndPhoneNumber() {
        CustomerSearchCriteria criteria = new CustomerSearchCriteria();
        criteria.setName("顧客01");
        criteria.setPhoneNumber("0900000001");

        List<?> result = customerService.search(criteria);

        Assert.assertEquals(1, result.size());
    }
}
