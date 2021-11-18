package com.unicap.tcc.usability.api.resources;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/test")
@Api("test api")
@RequiredArgsConstructor
public class TestResource {

    @GetMapping("/ping")
    @ApiOperation("pong")
    public String pong(){
        return "pong";
    }
}
