package com.mycompany.helloworld.springboot.mapstruct;

import org.junit.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RestController
public class MapStructStudy {
    @Autowired
    private UserBodyValuesMapper userBodyValuesMapper;

    @GetMapping("/mapStruct")
    public String test1(){
        UserBodyImperialValuesDTO dto = new UserBodyImperialValuesDTO();
        dto.setPound(100);
        dto.setInch(10);

        UserBodyValues obj = userBodyValuesMapper.userBodyValuesMapper(dto);

        assertNotNull(obj);
        assertEquals(45.35, obj.getKilogram(), 0);
        assertEquals(25.4, obj.getCentimeter(), 0);

        return "SUCCESS";
    }

    @Test
    public void test2() {
        UserBodyValuesMapper m = Mappers.getMapper(UserBodyValuesMapper.class);

        UserBodyImperialValuesDTO dto = new UserBodyImperialValuesDTO();
        dto.setPound(100);
        dto.setInch(10);

        UserBodyValues obj = m.userBodyValuesMapper(dto);

        assertNotNull(obj);
        assertEquals(45.35, obj.getKilogram(), 0);
        assertEquals(25.4, obj.getCentimeter(), 0);
    }
}
