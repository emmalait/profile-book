package project.account;

import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @EntityGraph(attributePaths = {"pictures"})
    List<Account> findAll();
    
    @EntityGraph(attributePaths = {"pictures"})
    Account findByUsername(String username);
    
    @EntityGraph(attributePaths = {"pictures"})
    Account findByUrl(String url);
}
