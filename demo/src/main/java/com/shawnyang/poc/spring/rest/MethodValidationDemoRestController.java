package com.shawnyang.poc.spring.rest;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class MethodValidationDemoRestController {

	@PostMapping("/resource1")
	public void postResource1(//
			@NotEmpty @RequestParam(value = "input1") String input1,
			@PositiveOrZero @RequestParam(value = "input2") long input2) {
	}

	@PostMapping("/resource2")
	public void postResource2(@Valid @RequestBody InputDto inputDto) {

	}

	public static class InputDto {

		@NotNull
		private String field1;

		@Email
		private String field2;

		public String getField1() {
			return field1;
		}

		public void setField1(String field1) {
			this.field1 = field1;
		}

		public String getField2() {
			return field2;
		}

		public void setField2(String field2) {
			this.field2 = field2;
		}

	}

}
