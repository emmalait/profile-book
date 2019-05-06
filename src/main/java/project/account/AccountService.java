
package project.account;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    PasswordEncoder passwordEncoder;
    
    public void create(Account account) {
        Account a = new Account();
        a.setUsername(account.getUsername());
        a.setPassword(passwordEncoder.encode(account.getPassword()));
        a.setName(account.getName());
        a.setUrl(account.getUrl());
        accountRepository.save(a);
    }
    
    public Account getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return accountRepository.findByUsername(auth.getName());
    }
    
    public Account findByUrl(String url) {
        return accountRepository.findByUrl(url);
    }
    
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    
}
