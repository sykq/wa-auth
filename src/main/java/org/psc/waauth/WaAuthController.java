package org.psc.waauth;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WaAuthController {

	@RequestMapping(value = { "/", "/home" })
	public String welcome(Map<String, Object> model) {
		return "home";
	}
}
