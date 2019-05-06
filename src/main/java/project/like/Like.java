
package project.like;

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

@Entity(name = "Likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like extends AbstractPersistable<Long> {
    
    
    @ManyToOne
    private Account liker;
    
    @ManyToOne
    private Post post;
    
    @ManyToOne
    private Picture picture;
    
}
