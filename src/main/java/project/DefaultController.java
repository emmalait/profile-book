package project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import project.account.AccountService;

@Controller
public class DefaultController {

    @Autowired
    private AccountService accountService;

    @GetMapping("*")
    public String index() {
        if (accountService.getCurrentUser() != null) {
            return "redirect:/" + accountService.getCurrentUser().getUrl();
        } else {
            return "index";
        }
    }
}
