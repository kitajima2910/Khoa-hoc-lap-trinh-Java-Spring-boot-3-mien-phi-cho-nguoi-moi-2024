package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/hellosts4")
	String sayHello() {
		return "Demo: STS4, again!";
	}
	
}
