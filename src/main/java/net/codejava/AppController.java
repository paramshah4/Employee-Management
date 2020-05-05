package net.codejava;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AppController {



	User user1;

	@Autowired
	private UserService userService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService service;

	@Autowired
	private UserRepository userRepository;


	@RequestMapping(value = "/login")
	public String login(Model model)
	{
//		User user = new User();
//		model.addAttribute("user", user);
		return "login";
	}

//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public void validLogin(@ModelAttribute("user") User user,Model model)
//	{
//		user1=userRepository.findByEmail(user.getEmail());
//		System.out.println(user1)
//	}

//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	public String validLogin(@ModelAttribute("user") User user,Model model)
//	{
//		user1= userRepository.findByUsername(user.getUsername());
//		System.out.println(user);
//		System.out.println(user1);
//
//		if(user1==null)
//		{
//			model.addAttribute("exist",true);
//			return "login";
//		}
////		if(!user1.getPassword().equals(user.getPassword()))
////		{
////			model.addAttribute("exist1",true);
////			return "login";
////		}
//
//
//		return "redirect:/event";
//
//	}


	//REGISTER CONTROLLER

	@RequestMapping("/register")
	public String showForm(Model model) {
		UserRegistrationDto userRegistrationDto=new UserRegistrationDto();
		model.addAttribute("user", userRegistrationDto);

		return "register";
	}


	@RequestMapping(value= "/register", method = RequestMethod.POST)
	public String submitForm(@ModelAttribute("user") @Valid UserRegistrationDto userRegistrationDto, BindingResult bindingResult,Model model) {

		User existing = userService.findByEmail(userRegistrationDto.getEmail());

		if(bindingResult.hasErrors())
		{
			return "register";
		}
		else
		if(existing!=null)
		{
			model.addAttribute("exist",true);
			return "register";
		}

		userService.save(userRegistrationDto);

		//System.out.println(user);

		return "redirect:/";
	}


//	@RequestMapping(value = "/username", method = RequestMethod.GET)
//	@ResponseBody
//	public String currentUserName(Principal principal,Model model) {
//		User user2=userRepository.findByEmail( principal.getName());
//	}


	@RequestMapping(value = "/account", method = RequestMethod.GET)
	public String account(Principal principal,Model model)
	{
		User user2=userRepository.findByEmail( principal.getName());
		model.addAttribute("user",user2);
		return "account";
	}

	@RequestMapping("/logout")
	public String Logout()
	{

		return "redirect:/";
	}



	@RequestMapping("/event")
	public String viewHomePage(Model model, @RequestParam(defaultValue = "") String name) {

//		List<Product> listProducts = service.listAll();
		model.addAttribute("listProducts", productRepository.findByNameLike("%"+name+"%"));
		
		return "index";
	}
	
	@RequestMapping("/new")
	public String showNewProductPage(Model model) {

		Product product = new Product();
		model.addAttribute("product", product);
		
		return "new_product";
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveProduct(@ModelAttribute("product") Product product) {

		service.save(product);
		
		return "redirect:/event";
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView showEditProductPage(@PathVariable(name = "id") int id) {

		ModelAndView mav = new ModelAndView("edit_product");
		Product product = service.get(id);
		mav.addObject("product", product);
		
		return mav;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteProduct(@PathVariable(name = "id") int id) {

		service.delete(id);
		return "redirect:/event";
	}
}
