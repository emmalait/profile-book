
package project.post;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.account.Account;

public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findTop25ByRecipientOrderByTimestampDesc(Account recipient);
}
