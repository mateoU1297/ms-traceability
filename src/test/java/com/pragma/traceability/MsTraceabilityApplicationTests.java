package com.pragma.traceability;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.autoconfigure.exclude=" +
				"org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration," +
				"org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration," +
				"org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration," +
				"org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration"
})
class MsTraceabilityApplicationTests {

	@Test
	void contextLoads() {
	}
}
