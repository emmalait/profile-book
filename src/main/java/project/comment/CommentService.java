
package project.comment;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.account.Account;
import project.picture.Picture;
import project.post.Post;

@Service
public class CommentService {
    
    @Autowired
    private CommentRepository commentRepository;
    
    public void addCommentToPost(Post post, Comment comment, Account commenter) {
        Comment c = new Comment();
        c.setCommenter(commenter);
        c.setContent(comment.getContent());
        c.setTimestamp(LocalDateTime.now());
        c.setPost(post);
        commentRepository.save(c);
    }
    
    public void addCommentToPicture(Picture picture, Comment comment, Account commenter) {
        Comment c = new Comment();
        c.setCommenter(commenter);
        c.setContent(comment.getContent());
        c.setPicture(picture);
        c.setTimestamp(LocalDateTime.now());
        commentRepository.save(c);
    }
    
    public void deleteCommentsFromPicture(Picture picture) {
        List<Comment> comments = commentRepository.findByPicture(picture);
        
        for (Comment comment : comments) {
            commentRepository.delete(comment);
        }
    }
    
    public List<Comment> findRecentCommentsOnPost(Post post) {
        List<Comment> comments = commentRepository.findTop10ByPostOrderByTimestampDesc(post);
        comments.sort((Comment c1, Comment c2) -> c1.getTimestamp().compareTo(c2.getTimestamp()));
        return comments;
    }
    
    public List<Comment> findRecentCommentsOnPicture(Picture picture) {
        List<Comment> comments = commentRepository.findTop10ByPictureOrderByTimestampDesc(picture);
        comments.sort((Comment c1, Comment c2) -> c1.getTimestamp().compareTo(c2.getTimestamp()));
        return comments;
    }
}
