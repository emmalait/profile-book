package project.friendship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.account.Account;
import project.account.AccountService;
import project.picture.PictureService;

@Controller
public class FriendshipController {

    @Autowired
    private FriendshipService friendshipService;
    
    @Autowired
    private PictureService pictureService;
    
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/{accountUrl}/friends")
    public String listFriends(Model model, @PathVariable String accountUrl) {
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
        return "friends";
    }

    @PostMapping("/{accountUrl}/friends")
    public String addFriend(@PathVariable String accountUrl) {
        Account requester = accountService.getCurrentUser();
        Account requestee = accountService.findByUrl(accountUrl);
        friendshipService.addFriendRequest(requester, requestee);

        return "redirect:/" + accountUrl;
    }

    @GetMapping("/{accountUrl}/friend-requests")
    public String listFriendRequests(Model model, @PathVariable String accountUrl) {
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

        return "friend-requests";
    }

    @PostMapping("/{accountUrl}/friend-requests/{id}/accept")
    public String acceptFriendRequest(@PathVariable String accountUrl, @PathVariable Long id) {
        friendshipService.acceptFriendRequest(id);
        return "redirect:/" + accountUrl + "/friends";
    }

    @PostMapping("/{accountUrl}/friend-requests/{id}/reject")
    public String rejectFriendRequest(@PathVariable String accountUrl, @PathVariable Long id) {
        friendshipService.rejectFriendRequest(id);
        return "redirect:/" + accountUrl + "/friends";
    }
    
}
