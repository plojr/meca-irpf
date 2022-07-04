package grp.meca.irpf.Controllers.Contabilidade;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contabilidade")
public class HomeContabilidadeController {

	@GetMapping
	public String home() {
		return "contabilidade/index";
	}
}
