
package project.post;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.account.Account;
import project.account.AccountService;
import project.comment.Comment;
import project.comment.CommentService;
import project.like.LikeService;

@Controller
public class PostController {
    
    @Autowired
    private PostService postService;
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private LikeService likeService;
    
    @PostMapping("/{accountUrl}/post")
    public String createPost(@PathVariable String accountUrl, @Valid @ModelAttribute Post post, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/" + accountUrl;
        }

        Account sender = accountService.getCurrentUser();
        Account recipient = accountService.findByUrl(accountUrl);
        postService.create(post, sender, recipient);
        return "redirect:/" + accountUrl;
    }

    @PostMapping("/{accountUrl}/post/{id}/comment")
    public String commentPost(@PathVariable String accountUrl, @Valid @ModelAttribute Comment comment,
            BindingResult bindingResult, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            return "redirect:/" + accountUrl;
        }

        Account commenter = accountService.getCurrentUser();
        commentService.addCommentToPost(postService.getOne(id), comment, commenter);
        return "redirect:/" + accountUrl;
    }

    @PostMapping("/{accountUrl}/post/{id}/like")
    public String likePost(@PathVariable String accountUrl, @PathVariable Long id) {
        Account liker = accountService.getCurrentUser();
        likeService.addLikeToPost(postService.getOne(id), liker);
        return "redirect:/" + accountUrl;
    }
    
}
