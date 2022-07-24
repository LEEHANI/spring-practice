package com.example.springbatch.job.itemReader.flat;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {

    @Override
    public Customer mapFieldSet(FieldSet fieldSet) throws BindException {

        if(fieldSet == null) {
            return null;
        }

        Customer customer = new Customer();
        customer.setName(fieldSet.readString("name"));
        customer.setAge(fieldSet.readInt("age"));
        customer.setYear(fieldSet.readString("year"));

        return customer;
    }
}
