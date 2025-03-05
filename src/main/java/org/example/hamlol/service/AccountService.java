package org.example.hamlol.service;


import org.example.hamlol.dto.AccountDto;

public interface AccountService {



    //소환사 정보 저장
    void saveAccount(AccountDto accountDto);

    //라이엇에서 소환사 정보 조회
    AccountDto AccountInfo(String gameName,String tagLine) throws Exception;





}
