package grp.meca.irpf.Controllers.Basico;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/basico")
public class HomeBasicoController {
	
	@GetMapping
	public String home() {
		return "basico/index";
	}
}
