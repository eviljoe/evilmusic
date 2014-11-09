package em.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import em.model.Greeting;

@RestController
public class HelloController { // JOE rt

    @RequestMapping(value = "/greeting", method = RequestMethod.GET)
    public Greeting greeting() {
        return new Greeting(100, "work");
    }
}
