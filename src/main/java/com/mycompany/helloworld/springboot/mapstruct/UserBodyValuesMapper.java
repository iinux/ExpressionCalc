package com.mycompany.helloworld.springboot.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserBodyValuesMapper {
    @Mapping(source = "inch", target = "centimeter", qualifiedByName = "inchToCentimeter")
    @Mapping(source = "pound", target = "kilogram", qualifiedBy = PoundToKilogramMapper.class)
    UserBodyValues userBodyValuesMapper(UserBodyImperialValuesDTO dto);

    @Named("inchToCentimeter")
    static double inchToCentimeter(int inch) {
        return inch * 2.54;
    }

    @PoundToKilogramMapper
    static double poundToKilogram(int pound) {
        return pound * 0.4535;
    }

}
