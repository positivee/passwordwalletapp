package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.Encryption.Aes
import pl.biernacki.passwordwalletapp.Encryption.Sha512
import pl.biernacki.passwordwalletapp.entity.IpAddressAttempt
import pl.biernacki.passwordwalletapp.entity.LoginAttempt
import pl.biernacki.passwordwalletapp.entity.Password
import pl.biernacki.passwordwalletapp.entity.User
import pl.biernacki.passwordwalletapp.repository.InMemoryIpAddressAttemptRepository
import pl.biernacki.passwordwalletapp.repository.InMemoryPasswordRepository
import pl.biernacki.passwordwalletapp.repository.InMemoryUserRepository
import pl.biernacki.passwordwalletapp.repository.IpAdressAttemptRepository
import pl.biernacki.passwordwalletapp.repository.UserRepository
import spock.lang.Specification

import java.time.LocalDateTime

class IpAddressAttemptService_addressAddCorrectAttempts_Test extends Specification {

    private IpAddressAttemptService = new IpAddressAttemptService(new InMemoryIpAddressAttemptRepository());


    def "shouldIpAdressCorrectAttempt"(){
        def date = LocalDateTime.now();

        setup:
        IpAddressAttempt ipAddressAttempt = new IpAddressAttempt(1L,"127.0.0.1",0,0,date);
        def ipAddressTemp = IpAddressAttemptService.saveAddressAttempt(ipAddressAttempt.ipAddress)


        when:
        def savedIpAddressAttemptCorrect = IpAddressAttemptService.addressAddCorrectAttempts(ipAddressTemp.ipAddress)


        then:
        savedIpAddressAttemptCorrect.ipAddress == ipAddressAttempt.ipAddress;
        savedIpAddressAttemptCorrect.attemptsCorrect == ipAddressAttempt.attemptsCorrect+1
        savedIpAddressAttemptCorrect.attemptsIncorrect == ipAddressAttempt.attemptsIncorrect

    }

}
