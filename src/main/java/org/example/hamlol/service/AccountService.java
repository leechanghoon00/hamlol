package org.example.hamlol.service;


import org.example.hamlol.dto.AccountRequestDTO;
import org.example.hamlol.dto.AccountRequestDTO;
import org.example.hamlol.dto.AccountResponseDTO;

public interface AccountService {



    //라이엇에서 소환사 정보 조회, 저장
    AccountResponseDTO getAccountInfoAndSaveAccount(AccountRequestDTO accountRequestDto)throws Exception;






}
