package project.account;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.comment.Comment;
import project.friendship.FriendshipService;
import project.picture.PictureService;
import project.post.Post;
import project.post.PostService;

@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private FriendshipService friendshipService;
    
    @Autowired
    private PictureService pictureService;
    
    @Autowired
    private PostService postService;

    @GetMapping("/register")
    public String register(@ModelAttribute Account account) {
        return "register";
    }

    @GetMapping("/people")
    public String list(Model model) {
        Account currentUser = accountService.getCurrentUser();
        
        // For navbar + sidebar:
        model.addAttribute("currentUser", currentUser);

        // For navbar:
        model.addAttribute("friendRequests", friendshipService.getFriendRequests(currentUser));
        
        model.addAttribute("accounts", accountService.findAll());

        return "accounts";
    }

    @PostMapping("/accounts")
    public String add(@Valid @ModelAttribute Account account, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        accountService.create(account);
        return "redirect:/login";
    }

    @GetMapping("/{accountUrl}")
    public String viewProfile(Model model, @PathVariable String accountUrl, @ModelAttribute Post post, @ModelAttribute Comment comment) {
        Account profile = accountService.findByUrl(accountUrl);
        Account currentUser = accountService.getCurrentUser();

        // For navbar + sidebar:
        model.addAttribute("currentUser", currentUser);

        // For navbar:
        model.addAttribute("friendRequests", friendshipService.getFriendRequests(currentUser));

        // For sidebar:
        model.addAttribute("hasProfilePic", pictureService.hasProfilePic(profile));
        model.addAttribute("profilePic", pictureService.getProfilePic(profile));
        model.addAttribute("account", profile);
        model.addAttribute("areFriends", friendshipService.areFriends(currentUser, profile));
        model.addAttribute("isCurrentUser", currentUser == profile);
        model.addAttribute("pendingFriendRequest", friendshipService.hasPendingFriendRequest(currentUser, profile));
        model.addAttribute("friends", friendshipService.getFriends(profile));
        
        model.addAttribute("posts", postService.getRecent(profile));

        return "account";
    }
}
