package com.example.customersearch.pl;

import com.example.customersearch.bl.model.CustomerSearchCriteria;

import javax.validation.constraints.Pattern;

public class CustomerSearchForm {

    @Pattern(regexp = "^$|^[^\\x00-\\x7F]+$", message = "氏名は全角文字のみ入力してください。")
    private String name;

    private String address;

    @Pattern(regexp = "^[0-9]*$", message = "電話番号は半角数字のみ入力してください。")
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public CustomerSearchCriteria toCriteria() {
        CustomerSearchCriteria criteria = new CustomerSearchCriteria();
        criteria.setName(name);
        criteria.setAddress(address);
        criteria.setPhoneNumber(phoneNumber);
        return criteria;
    }
}
