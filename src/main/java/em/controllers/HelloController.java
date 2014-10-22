package em.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.ws.rs.GET;

import em.model.Greeting;

@RestController
public class HelloController {

	@GET
	@RequestMapping("/greeting")
    public Greeting greeting() {
        return new Greeting(100, "work");
    }
}
