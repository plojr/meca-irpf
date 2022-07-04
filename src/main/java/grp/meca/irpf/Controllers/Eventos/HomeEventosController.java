package grp.meca.irpf.Controllers.Eventos;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/eventos")
public class HomeEventosController {

	@GetMapping
	public String home() {
		return "eventos/index";
	}
}
