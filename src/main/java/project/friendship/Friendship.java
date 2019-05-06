
package project.friendship;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;
import project.account.Account;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Friendship extends AbstractPersistable<Long>  {
    
    @ManyToOne
    private Account requester;
    
    @ManyToOne
    private Account requestee;
    
    private boolean accepted;
    
    private boolean handled;
    
    private LocalDateTime timestamp;
    
    private LocalDate handledDate;
    
}
