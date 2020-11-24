package com.axreng.backend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import com.axreng.backend.bean.EnvironmentParameter;

@TestMethodOrder(OrderAnnotation.class)
public class EnvironmentParameterTest {

	@ParameterizedTest()
	@Order(1)
	@DisplayName("Test for empty or null values ​​for the BASE_URL variable.")
	@NullAndEmptySource
	@ValueSource(strings = { " ", "   " })
	
	void nullEmptyAndBlankStringsForBaseURL(String text) {

		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new EnvironmentParameter.Builder(text).build();
		});
	}	
	
	@ParameterizedTest()
	@Order(2)
	@DisplayName("Test for invalid or relative values ​​of the BASE_URL variable.")
	@ValueSource(strings = { "www.hiring.axreng.com",            
            "/search",
            "htttp://www.hiring.axreng.com/",
            "file:/paste/file",
            "file://localhost/paste/file",
            "file:///paste/file" })
	void invalidAndRelativeForBaseURL(String text) {
		
		assertNotNull(text);
		assertTrue(!text.isEmpty());
		Assertions.assertThrows(IllegalArgumentException.class, () -> {
			new EnvironmentParameter.Builder(text).build();
		});
	}	
	
	@ParameterizedTest()
	@Order(3)
	@DisplayName("Test for valid and absolute URL values ​​(HTTP or HTTPS) for variable BASE_URL.")
	@ValueSource(strings = { "http://hiring.axreng.com/",
			"http://www.hiring.axreng.com/",			
            "https://hiring.axreng.com/",
            "https://www.hiring.axreng.com/",
            "https://hiring.axreng.com/search",
            "HTTP://hiring.axreng.com/search",
            "https://hiring.axreng.com/search/1"})
	void validAndAbsoluteForBaseURL(String text) {
		
		assertNotNull(text);
		assertTrue(!text.isEmpty());
		
		Assertions.assertDoesNotThrow(() -> {
			new EnvironmentParameter.Builder(text).build();
		});
	}	
	
	@ParameterizedTest()
	@Order(4)
	@DisplayName("Test for Null value for the MAX_RESULTS variable.")
	@ValueSource(strings = {"http://hiring.axreng.com/"})
	void nullForMaxResults(String text) {

		assertEquals(-1l, new EnvironmentParameter.Builder(text).build().getMaxResults());		

	}
	
	@ParameterizedTest()
	@Order(5)
	@DisplayName("Test for empty values ​​for a MAX_RESULTS variable.")
	@CsvSource({
	    "http://hiring.axreng.com/,",
	    "http://hiring.axreng.com/, ",
	    "http://hiring.axreng.com/,              "
	})
	void emptyAndBlankStringsForMaxResults(String text) {
		assertEquals(-1l, new EnvironmentParameter.Builder(text).build().getMaxResults());	

	}	
	
	@ParameterizedTest()
	@Order(6)
	@DisplayName("Test for values ​​<-1 for the MAX_RESULTS variable.")
	@CsvSource({
	    "http://hiring.axreng.com/,-5",
	    "http://hiring.axreng.com/,-1"	    
	})
	void menorOuIgualAMenosUmForMaxResults(String text) {
		assertEquals(-1l, new EnvironmentParameter.Builder(text).build().getMaxResults());	

	}
	
	@ParameterizedTest()
	@Order(7)
	@DisplayName("Test for values ​​that is not a number for the MAX_RESULTS variable")
	@CsvSource({
	    "http://hiring.axreng.com/,a",
	    "http://hiring.axreng.com/,$$",
	    "http://hiring.axreng.com/,@",
	    "http://hiring.axreng.com/,test"
	})
	void notANumberForMaxResults(String text) {
		assertEquals(-1l, new EnvironmentParameter.Builder(text).build().getMaxResults());	

	}
	
	@ParameterizedTest()
	@Order(8)
	@DisplayName("Test for correct values ​​for the MAX_RESULTS variable")
	@CsvSource({
	    "http://hiring.axreng.com/,100",
	    "http://hiring.axreng.com/,0",
	    "http://hiring.axreng.com/,-1"
	})
	void corretNumberForMaxResults(String text) {
		Long mynum = new EnvironmentParameter.Builder(text).build().getMaxResults();
		assertTrue(-1 <= mynum && mynum <= Long.MAX_VALUE);
	}
		
}
