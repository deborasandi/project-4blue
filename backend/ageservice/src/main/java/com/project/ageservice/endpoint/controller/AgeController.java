package com.project.ageservice.endpoint.controller;

import javax.ws.rs.POST;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.ageservice.endpoint.service.AgeService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping({"/age"})
@Slf4j
public class AgeController {
	private final AgeService ageService;

	public AgeController(AgeService ageService) {
		this.ageService = ageService;
	}
	
	@GetMapping
    public int calculate(@RequestParam(name="birthdate", required = true) String birthdate){
        return ageService.calculate(birthdate);
    }
}
