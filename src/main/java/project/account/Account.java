package project.account;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import project.comment.Comment;
import project.friendship.Friendship;
import project.like.Like;
import project.picture.Picture;
import project.post.Post;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account extends AbstractPersistable<Long> {

    @NotEmpty
    @Size(min = 4, max = 15)
    private String username;

    @NotEmpty
    @Size(min = 5)
    private String password;

    @NotEmpty
    @Size(min = 4, max = 15)
    private String name;

    @NotEmpty
    @Size(min = 4, max = 15)
    private String url;

    @OneToMany(mappedBy = "account")
    private List<Picture> pictures = new ArrayList<>();

    @OneToMany(mappedBy = "liker")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "commenter")
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "sender")
    private List<Post> sentPosts = new ArrayList<>();

    @OneToMany(mappedBy = "recipient")
    private List<Post> receivedPosts = new ArrayList<>();

    @OneToMany(mappedBy = "requester")
    private List<Friendship> initialisedFriendships = new ArrayList<>();

    @OneToMany(mappedBy = "requestee")
    private List<Friendship> receivedFriendships = new ArrayList<>();

}
