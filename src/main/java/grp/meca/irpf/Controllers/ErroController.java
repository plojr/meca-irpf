package grp.meca.irpf.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErroController {
	
	@GetMapping("/erro")
	public String getErro(Model model) {
		return "erro";
	}
}
