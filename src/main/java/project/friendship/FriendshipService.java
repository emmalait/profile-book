package project.friendship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.account.Account;

@Service
public class FriendshipService {

    @Autowired
    private FriendshipRepository friendshipRepository;

    public List<Account> getFriends(Account account) {
        List<Account> friends = new ArrayList<>();
        List<Friendship> friendshipsRequested = friendshipRepository.findByRequesterAndAccepted(account, Boolean.TRUE);
        List<Friendship> friendshipsRequesteed = friendshipRepository.findByRequesteeAndAccepted(account, Boolean.TRUE);
        List<Friendship> friendships = new ArrayList<>();
        friendships.addAll(friendshipsRequested);
        friendships.addAll(friendshipsRequesteed);

        for (Friendship friendship : friendships) {
            if (friendship.getRequester() == account) {
                friends.add(friendship.getRequestee());
            } else {
                friends.add(friendship.getRequester());
            }
        }

        return friends;
    }

    public List<Friendship> getFriendRequests(Account account) {
        return friendshipRepository.findByRequesteeAndAccepted(account, Boolean.FALSE);
    }

    public boolean addFriendRequest(Account requester, Account requestee) {
        if (getFriends(requestee).contains(requester)) {
            return false;
        } else {
            Friendship request = new Friendship();
            request.setRequester(requester);
            request.setRequestee(requestee);
            request.setAccepted(false);
            friendshipRepository.save(request);
            return true;
        }
    }
    
    public void acceptFriendRequest(Long id) {
        Friendship request = friendshipRepository.getOne(id);
        request.setAccepted(true);
        request.setHandled(true);
        request.setHandledDate(LocalDate.now());
        friendshipRepository.save(request);
    }
    
    public void rejectFriendRequest(Long id) {
        Friendship request = friendshipRepository.getOne(id);
        request.setAccepted(false);
        request.setHandled(true);
        request.setHandledDate(LocalDate.now());
        friendshipRepository.save(request);
    }
    
    public boolean areFriends(Account account1, Account account2) {
        return getFriends(account1).contains(account2);
    }
    
    public boolean hasPendingFriendRequest(Account requester, Account requestee) {
        List<Friendship> requests = friendshipRepository.findByRequesterAndRequesteeAndHandled(requester, requestee, false);
        
        return !requests.isEmpty();
    }

}
