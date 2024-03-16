package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Java6Assertions.assertThat;


@SpringBootTest
class DemoApplicationTests {
	//Creating an instance of calculator
	Calculator calculator = new Calculator();
	@Test
	void itShouldAddNumbers() {
		//given
		int numberOne = 10;
		int numberTwo = 30;
		//when
		int result = calculator.add(numberOne,numberTwo);
		//then
		int expected = 40;
		assertThat(result).isEqualTo(expected);
	}

	class Calculator{
		int add(int a, int b){
			return a+b;
		}
	}

}
