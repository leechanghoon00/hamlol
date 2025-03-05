package org.example.hamlol.serviceimpl;

import org.example.hamlol.dto.AccountDto;
import org.example.hamlol.entity.AccountEntity;
import org.example.hamlol.repository.AccountRepository;
import org.example.hamlol.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public void saveAccount(AccountDto accountDto) {

        AccountEntity accountEntity = new AccountEntity();
        accountEntity.setGameName(accountDto.gameName());
        accountEntity.setTagLine(accountDto.tagLine());
        accountEntity.setPuuid(accountDto.puuid());

        accountRepository.save(accountEntity);
        System.out.println("저장");
    }
}
