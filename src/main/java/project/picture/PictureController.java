package project.picture;

import project.account.Account;
import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import project.account.AccountService;
import project.comment.Comment;
import project.comment.CommentService;
import project.friendship.FriendshipService;
import project.like.LikeService;

@Controller
public class PictureController {

    @Autowired
    private PictureService pictureService;

    @Autowired
    private AccountService accountService;
    
    @Autowired
    private FriendshipService friendshipService;
    
    @Autowired
    private LikeService likeService;
    
    @Autowired
    private CommentService commentService;

    /*@GetMapping("/{accountUrl}/pictures/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String accountUrl, @PathVariable Long id) {
        return pictureService.getFile(accountUrl, id);
    }*/
    
    @GetMapping("/{accountUrl}/album")
    public String listPictures(Model model, @PathVariable String accountUrl) {
        Account currentUser = accountService.getCurrentUser();
        Account profile = accountService.findByUrl(accountUrl);

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
        
        model.addAttribute("pictures", profile.getPictures());

        return "album";
    }

    @PostMapping("/{accountUrl}/album")
    public String create(@PathVariable String accountUrl, @RequestParam("file") MultipartFile file, @RequestParam("description") String description) throws IOException {
        Account profile = accountService.findByUrl(accountUrl);
        pictureService.addPicture(profile, file, description);
        return "redirect:/" + accountUrl + "/album";
    }

    @GetMapping("/{accountUrl}/album/{id}")
    public String listPicture(Model model, @PathVariable String accountUrl, @PathVariable Long id, @ModelAttribute Comment comment) {
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
        
        model.addAttribute("picture", pictureService.getOne(id));
        
        return "picture";
    }

    @GetMapping(path = "/{accountUrl}/album/{id}/file", produces = "image/png")
    @ResponseBody
    public byte[] get(@PathVariable String accountUrl, @PathVariable Long id) {
        return pictureService.get(accountUrl, id);
    }

    @PostMapping("/{accountUrl}/album/{id}/set")
    public String setAsProfilePic(@PathVariable String accountUrl, @PathVariable Long id) {
        Picture picture = pictureService.getOne(id);
        pictureService.setAsProfilePic(picture);
        return "redirect:/" + accountUrl + "/album/" + id;
    }
    
    @PostMapping("/{accountUrl}/album/{id}/delete")
    public String delete(@PathVariable String accountUrl, @PathVariable Long id) {
        Picture picture = pictureService.getOne(id);
        pictureService.deletePicture(picture);
        return "redirect:/" + accountUrl + "/album";
    }

    @PostMapping("/{accountUrl}/album/{id}/like")
    public String likePicture(@PathVariable String accountUrl, @PathVariable Long id) {
        Account liker = accountService.getCurrentUser();
        likeService.addLikeToPicture(pictureService.getOne(id), liker);
        return "redirect:/" + accountUrl + "/album/" + id;
    }

    @PostMapping("/{accountUrl}/album/{id}/comment")
    public String commentPicture(@PathVariable String accountUrl, @Valid @ModelAttribute Comment comment,
            BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "redirect:/" + accountUrl;
        }

        Account commenter = accountService.getCurrentUser();
        Picture picture = pictureService.getOne(id);
        commentService.addCommentToPicture(picture, comment, commenter);
        return "redirect:/" + accountUrl + "/album/" + id;
    }

}
