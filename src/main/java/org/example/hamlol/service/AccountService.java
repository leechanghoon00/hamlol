package org.example.hamlol.service;


import org.example.hamlol.dto.AccountRequestDto;
import org.example.hamlol.dto.AccountResponseDTO;

public interface AccountService {



    //소환사 정보 저장
    AccountResponseDTO saveAccount(AccountRequestDto accountRequestDto);

    //라이엇에서 소환사 정보 조회
    AccountResponseDTO getAccountInfo(String gameName,String tagLine)throws Exception;




}
