package controler.homePage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import po.HomePage;
import service.HomePageService;

@Controller
@RequestMapping(value = "/homePage")
public class HomePageControler {
	
	@Autowired
	private HomePageService homePageService;
	
	@RequestMapping(value = "/phoneHomePage")
	@ResponseBody
	public List<HomePage> phoneHomePage(HttpServletRequest request,HttpServletResponse response) throws Exception {
		HomePage hop=new HomePage();
		return homePageService.selectAll(hop);
	}

}
