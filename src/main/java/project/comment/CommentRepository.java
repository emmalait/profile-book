
package project.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.picture.Picture;
import project.post.Post;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPicture(Picture picture);
    List<Comment> findTop10ByPostOrderByTimestampDesc(Post post);
    List<Comment> findTop10ByPictureOrderByTimestampDesc(Picture picture);
}
