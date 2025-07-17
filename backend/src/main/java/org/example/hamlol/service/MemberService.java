package org.example.hamlol.service;

public interface  MemberService {

    void checkMemberByEmail(String email);

    void resetPassword(String uuid, String newPassword);

}
