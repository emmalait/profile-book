
package project.comment;

import java.time.LocalDateTime;
import project.account.Account;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import project.picture.Picture;
import project.post.Post;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends AbstractPersistable<Long> {
    
    @ManyToOne
    private Account commenter;
    
    private String content;
    
    private LocalDateTime timestamp;
    
    @ManyToOne
    private Post post;
    
    @ManyToOne
    private Picture picture;
    
}
