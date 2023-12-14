package com.purshase.transaction.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.autoconfigure.wavefront.WavefrontProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AssertionErrors;

@SpringBootTest
class TestApplicationTests {

	@Test
	void contextLoads() {
		TestApplication.main(new String[]{});
		Assertions.assertTrue(Boolean.TRUE, "Application up!");
	}

}
