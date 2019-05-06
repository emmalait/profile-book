package project.friendship;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.account.Account;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByRequester(Account account);
    List<Friendship> findByRequestee(Account account);
    List<Friendship> findByAccepted(Boolean accepted);
    List<Friendship> findByRequesteeAndRequesterAndAccepted(Account requester, Account requestee, Boolean accepted);
    List<Friendship> findByRequesteeAndRequesterAndAcceptedAndHandled(Account requester, Account requestee, Boolean accepted, Boolean handled);
    List<Friendship> findByRequesterAndRequesteeAndAccepted(Account requester, Account requestee, Boolean accepted);
    List<Friendship> findByRequesterAndRequesteeAndAcceptedAndHandled(Account requester, Account requestee, Boolean accepted, Boolean handled);
    List<Friendship> findByRequesteeAndAccepted(Account requestee, Boolean accepted);
    List<Friendship> findByRequesterAndAccepted(Account requester, Boolean accepted);
    List<Friendship> findByRequesterAndRequesteeAndHandled(Account requester, Account requestee, Boolean handled);
    Friendship findByRequesterAndRequestee(Account requester, Account requestee);
}
