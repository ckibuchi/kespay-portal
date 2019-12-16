package hr254.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import hr254.models.User;
import hr254.repositories.EmployeeRepository;
import hr254.services.UserService;
import hr254.utils.WebClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.text.NumberFormat;
import java.util.Locale;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Value("${kespay.api.url}")
    private String kespayurl;

    @Value("${config.default.cur1}")
    private String cur1;

    @Value("${config.default.cur2}")
    private String cur2;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
        try {
            request.login(username,password);
        } catch (ServletException e) {

        }
    }


    @RequestMapping(value="/registration", method = RequestMethod.GET)
    public ModelAndView registration(){
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        modelAndView.addObject("user", user);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User userExists = userService.findUserByEmail(user.getUsername());
        user.setActive(1);
        String name=user.getSurName()+", "+user.getFirstName()+" "+user.getLastName();
        user.setName(name);
        if (userExists != null) {
            bindingResult
                    .rejectValue("username", "error.user",
                            "There is already a user registered with the username provided");
        }
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            userService.saveUser(user);
            modelAndView.addObject("successMessage", "User has been registered successfully");
            modelAndView.addObject("user", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }

    @RequestMapping(value={"/", "/home"}, method = RequestMethod.GET)
    public ModelAndView index(){
        ModelAndView modelAndView = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
       try{
          System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
           modelAndView.addObject("userName",auth.getName());
        modelAndView.addObject("adminMessage","Content Available Only for Users with Admin Role");}
        catch(Exception e)
        {}
       //Call APIS
        WebClient client=new WebClient();
        String today_sales="0.0";
        String todays_txns="0";

        try{
            todays_txns= client.webRequest(kespayurl+"/payments/getTodaysCount","","POST","");
            if(todays_txns.equalsIgnoreCase("null")) todays_txns="0";

        }
        catch(Exception e)
        {e.printStackTrace();}

        try{
            today_sales=client.webRequest(kespayurl+"/payments/getTodaysTotal","","POST","");
            if(today_sales.equalsIgnoreCase("null")) today_sales="0.0";


        }
        catch(Exception e)
        {e.printStackTrace();}
        try{


                Locale kes = new Locale(cur1, cur2);
                NumberFormat kenyanFormat = NumberFormat.getCurrencyInstance(kes);
                Double num=Double.parseDouble(today_sales);
                today_sales=kenyanFormat.format(num);
                System.out.println("Kenyan: " + kenyanFormat.format(num));


        }
catch (Exception e)
{e.printStackTrace();}

        modelAndView.addObject("totalemps",""+employeeDao.count());
        modelAndView.addObject("totalclients",""+0);
        modelAndView.addObject("today_sales",today_sales);
        modelAndView.addObject("todays_txns",todays_txns);
        modelAndView.addObject("totalprojects",""+0);
        modelAndView.addObject("totaltasks",""+0);

        modelAndView.setViewName("index");
        return modelAndView;
    }
    @Autowired
    private EmployeeRepository employeeDao;

}