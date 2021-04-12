package pl.biernacki.passwordwalletapp.service

import pl.biernacki.passwordwalletapp.entity.IpAddressAttempt
import pl.biernacki.passwordwalletapp.repository.InMemoryIpAddressAttemptRepository
import spock.lang.Specification

import java.time.LocalDateTime

class IpAddressAttemptService_saveAddressAttempt_Test extends Specification {
    private IpAddressAttemptService = new IpAddressAttemptService(new InMemoryIpAddressAttemptRepository());
    def "shouldSaveAddressAttempt"(){
        def date = LocalDateTime.now();

        setup:
        def addres ="127.0.0.1"

        when:
        def savedIpAddressAttempt = IpAddressAttemptService.saveAddressAttempt("127.0.0.1")


        then:
        savedIpAddressAttempt.ipAddress == addres
        savedIpAddressAttempt.attemptsCorrect == 0
        savedIpAddressAttempt.attemptsIncorrect == 0

    }
}
