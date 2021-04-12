package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.entity.IpAddressAttempt
import pl.biernacki.passwordwalletapp.repository.InMemoryIpAddressAttemptRepository
import spock.lang.Specification

import java.time.LocalDateTime

class IpAddressAttemptService_addressAddIncorrectAttempts_Test extends Specification {
    private IpAddressAttemptService = new IpAddressAttemptService(new InMemoryIpAddressAttemptRepository());


    def "shouldAddIncorrectAttempts"(){
        def date = LocalDateTime.now();

        setup:
        IpAddressAttempt ipAddressAttempt = new IpAddressAttempt(1L,"127.0.0.1",0,0,date);
        def ipAddressTemp = IpAddressAttemptService.saveAddressAttempt(ipAddressAttempt.ipAddress)


        when:
        def savedIpAddressAttemptIncorrect = IpAddressAttemptService.addressAddIncorrectAttempts(ipAddressTemp.ipAddress)


        then:
        savedIpAddressAttemptIncorrect.ipAddress == ipAddressAttempt.ipAddress;
        savedIpAddressAttemptIncorrect.attemptsCorrect == ipAddressAttempt.attemptsCorrect
        savedIpAddressAttemptIncorrect.attemptsIncorrect == ipAddressAttempt.attemptsIncorrect+1
 /*       savedIpAddressAttemptIncorrect.blockedUntil == sleep(5000);*/
    }
}
