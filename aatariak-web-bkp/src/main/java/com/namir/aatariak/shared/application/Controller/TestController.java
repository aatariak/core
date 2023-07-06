package com.namir.aatariak.shared.application.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

@Controller
public class TestController extends BaseApiController{

    @RequestMapping(value = "/public/test", method = RequestMethod.GET)
    public @ResponseBody Object greeting() {
        return Collections.singletonMap("message", "Hello, World");
    }


    @RequestMapping(value = "/v1/test", method = RequestMethod.GET)
    public @ResponseBody Object secured() {
        return Collections.singletonMap("message", "Hello, secured");
    }

}
