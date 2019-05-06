
package project.post;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.account.Account;

@Service
public class PostService {
    
    @Autowired
    PostRepository postRepository;
    
    public Post getOne(Long id) {
        return postRepository.getOne(id);
    }
    
    public List<Post> getRecent(Account account) {
        List<Post> posts = account.getReceivedPosts();
        posts.sort((Post o1, Post o2)->o2.getTimestamp().compareTo(o1.getTimestamp()));
        posts.stream().limit(25);
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
