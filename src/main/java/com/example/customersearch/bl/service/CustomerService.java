package com.example.customersearch.bl.service;

import com.example.customersearch.bl.model.Customer;
import com.example.customersearch.bl.model.CustomerSearchCriteria;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final List<Customer> customers = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (int i = 1; i <= 100; i++) {
            String name = String.format(Locale.ROOT, "顧客%03d", i);
            String phoneNumber = String.format(Locale.ROOT, "090%08d", i);
            String address = String.format(Locale.ROOT, "東京都千代田区サンプル町%d-%d", (i % 20) + 1, (i % 10) + 1);
            String email = String.format(Locale.ROOT, "customer%03d@example.com", i);
            customers.add(new Customer((long) i, name, phoneNumber, address, email));
        }
    }

    public List<Customer> search(CustomerSearchCriteria criteria) {
        return customers.stream()
                .filter(c -> contains(c.getName(), criteria.getName()))
                .filter(c -> contains(c.getAddress(), criteria.getAddress()))
                .filter(c -> contains(c.getPhoneNumber(), criteria.getPhoneNumber()))
                .collect(Collectors.toList());
    }

    public Optional<Customer> findById(Long id) {
        return customers.stream().filter(customer -> customer.getId().equals(id)).findFirst();
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }

    private boolean contains(String value, String query) {
        if (!StringUtils.hasText(query)) {
            return true;
        }
        return value.contains(query.trim());
    }
}
