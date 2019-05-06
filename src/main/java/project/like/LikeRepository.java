
package project.like;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.account.Account;
import project.picture.Picture;
import project.post.Post;

public interface LikeRepository extends JpaRepository<Like, Long>{
    Like findByLikerAndPost(Account liker, Post post);
    List<Like> findByPicture(Picture picture);
}
