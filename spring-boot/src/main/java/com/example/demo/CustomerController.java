package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {this.customerRepository = customerRepository;}

    private void logThread() {
        var current = Thread.currentThread();
        logger.info("Thread name: {}, id: {}, isVirtual: {}", current.getName(), current.threadId(), current.isVirtual());
    }

    @GetMapping("/test")
    public String test(@RequestParam(value = "id") Long id) {
        var result = customerRepository.findById(id);
        logThread();

        if (result.isPresent()) {
            Customer customer = result.get();
            logThread();
            logger.info("Found customer: {}", customer);
            return String.format("Hello %s!", customer.getFirstName());
        } else {
            logThread();
            logger.info("Customer not found");
            return String.format("Not found");
        }
    }
}
