package pl.biernacki.passwordwalletapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.biernacki.passwordwalletapp.entity.Password;
import pl.biernacki.passwordwalletapp.entity.SharePassword;
import pl.biernacki.passwordwalletapp.entity.User;
import pl.biernacki.passwordwalletapp.repository.PasswordRepository;
import pl.biernacki.passwordwalletapp.repository.SharePasswordRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SharePasswordService {

    @Autowired
    private final SharePasswordRepository sharePasswordRepository;
    @Autowired
    private final PasswordRepository passwordRepository;

    /*Method wich shares password with another user*/
    public SharePassword saveSharePassword(User owner, User borrower, Password password){
        SharePassword sharePassword = new SharePassword();
        sharePassword.setOwnerLogin(owner.getLogin());
        sharePassword.setBorrowerLogin(borrower.getLogin());
        sharePassword.setPassword(password);
        sharePassword = sharePasswordRepository.save(sharePassword);
        password.getSharePasswords().add(sharePassword);

        return sharePassword;
    }

    public List<SharePassword> getSharedPassword(String login){
        return sharePasswordRepository.findAllByBorrowerLogin(login);
    }

    public boolean findByLoginAndOwner(String login, String owner){
        return sharePasswordRepository.existsByBorrowerLoginAndOwnerLogin(login, owner);
    }
    public boolean existsByPasswordId(Long id){
        return sharePasswordRepository.existsByPasswordId(id);
    }
}
