package es.santander.darwin.reactivecontroller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class ApplicationTests {

	@Autowired
    private ApplicationContext applicationContext;
    
	@Test
	public void contextLoads() {
		assertNotNull(applicationContext);
	}

}
