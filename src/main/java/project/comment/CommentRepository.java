
package project.comment;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.picture.Picture;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPicture(Picture picture);
}
