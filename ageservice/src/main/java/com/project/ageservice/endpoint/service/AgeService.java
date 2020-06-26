package com.project.ageservice.endpoint.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AgeService {
	class BirthDate {
		String birthDate;

		public String getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(String birthDate) {
			this.birthDate = birthDate;
		}
	}
	
	public int calculate(String birthdate){
		LocalDate birth = LocalDate.parse(birthdate);
		
        return Period.between(birth, LocalDate.now()).getYears();
    }
}
