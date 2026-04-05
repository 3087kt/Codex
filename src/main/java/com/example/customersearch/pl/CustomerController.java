package com.example.customersearch.pl;

import com.example.customersearch.bl.model.Customer;
import com.example.customersearch.bl.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"", "/search"})
    public String searchPage(Model model) {
        if (!model.containsAttribute("searchForm")) {
            model.addAttribute("searchForm", new CustomerSearchForm());
        }
        return "search";
    }

    @PostMapping("/search")
    public String search(@Valid @ModelAttribute("searchForm") CustomerSearchForm searchForm,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "search";
        }

        return setResults(model, searchForm);
    }

    @GetMapping("/results")
    public String results(@ModelAttribute("searchForm") CustomerSearchForm searchForm,
                          Model model) {
        return setResults(model, searchForm);
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Long id,
                         @ModelAttribute("searchForm") CustomerSearchForm searchForm,
                         Model model) {
        Customer customer = customerService.findById(id).orElse(null);
        if (customer == null) {
            return showCustomerNotFound(model, searchForm);
        }
        model.addAttribute("customer", customer);
        model.addAttribute("searchForm", searchForm);
        return "detail";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleMethodArgumentTypeMismatch(@ModelAttribute("searchForm") CustomerSearchForm searchForm,
                                                   Model model) {
        return showCustomerNotFound(model, searchForm);
    }

    private String setResults(Model model, CustomerSearchForm searchForm) {
        List<Customer> results = customerService.search(searchForm.toCriteria());
        model.addAttribute("searchForm", searchForm);
        model.addAttribute("results", results);
        return "results";
    }

    private String showCustomerNotFound(Model model, CustomerSearchForm searchForm) {
        model.addAttribute("errorMessage", "顧客情報が存在しません");
        model.addAttribute("searchForm", searchForm);
        return "customer-not-found";
    }
}
