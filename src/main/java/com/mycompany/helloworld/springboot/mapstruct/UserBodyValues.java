package com.mycompany.helloworld.springboot.mapstruct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBodyValues {
    private double kilogram;
    private double centimeter;
}
