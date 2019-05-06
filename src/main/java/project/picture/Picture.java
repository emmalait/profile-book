package project.picture;

import project.like.Like;
import project.comment.Comment;
import project.account.Account;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture extends AbstractPersistable<Long> {
    
    private String name;
    
    private String mediaType;
    
    private Long size;

    @Lob
    @Type(type="org.hibernate.type.ImageType") 
    private byte[] content;
    
    private String description;
    
    private boolean profilePic;
    
    @ManyToOne
    private Account account;
    
    @OneToMany(mappedBy = "picture")
    private List<Like> likes = new ArrayList<>();
    
    @OneToMany(mappedBy = "picture")
    private List<Comment> comments = new ArrayList<>();

}
