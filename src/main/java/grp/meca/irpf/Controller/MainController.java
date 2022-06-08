package grp.meca.irpf.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

	@GetMapping("/")
	public String home() {
		return "index";
	}
	
	@GetMapping("/corretagens")
	public String mostrarCorretagens() {
		return "corretagens";
	}
}
