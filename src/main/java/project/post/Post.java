
package project.post;

import project.like.Like;
import project.comment.Comment;
import project.account.Account;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post extends AbstractPersistable<Long> {
    
    private String content;
    
    @ManyToOne
    private Account recipient;
    
    @ManyToOne
    private Account sender;
    
    private LocalDateTime timestamp;
    
    @OneToMany(mappedBy = "post")
    private List<Like> likes = new ArrayList<>();
    
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();

}
