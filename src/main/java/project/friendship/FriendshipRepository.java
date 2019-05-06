package project.friendship;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.account.Account;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByRequesteeAndAccepted(Account requestee, Boolean accepted);
    List<Friendship> findByRequesterAndAccepted(Account requester, Boolean accepted);
    List<Friendship> findByRequesterAndRequesteeAndHandled(Account requester, Account requestee, Boolean handled);
}
