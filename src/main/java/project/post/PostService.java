
package project.post;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.account.Account;
import project.comment.CommentService;

@Service
public class PostService {
    
    @Autowired
    PostRepository postRepository;
    
    @Autowired
    CommentService commentService;
    
    public Post getOne(Long id) {
        return postRepository.getOne(id);
    }
    
    public List<Post> getRecent(Account account) {
        List<Post> posts = postRepository.findTop25ByRecipientOrderByTimestampDesc(account);
        
        for (Post post : posts) {
            post.setComments(commentService.findRecentCommentsOnPost(post));
        }
        
        return posts;
    }
    
    public void create(Post post, Account sender, Account recipient) {
        Post p = new Post();
        p.setSender(sender);
        p.setContent(post.getContent());
        p.setTimestamp(LocalDateTime.now());
        p.setRecipient(recipient);
        postRepository.save(p);
    }
    
    
    
}
