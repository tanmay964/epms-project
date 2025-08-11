package com.example.epms;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EmployeeTest {
    @Test
    void testEmployeeFields() {
        Employee emp = new Employee();
        emp.setId(1L);
        emp.setName("Tanmay Mishra");
        emp.setEmail("tanmay@gmail.com");

        assertEquals(1L, emp.getId());
        assertEquals("Tanmay Mishra", emp.getName());
        assertEquals("tanmay@gmail.com", emp.getEmail());
    }
}
