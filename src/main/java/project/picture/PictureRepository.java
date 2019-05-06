package project.picture;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import project.account.Account;

public interface PictureRepository extends JpaRepository<Picture, Long> {
    List<Picture> findByAccount(Account account);
}
