package com.challenge.picpay.service;

import com.challenge.picpay.models.TransferUser;
import com.challenge.picpay.models.UserModel;
import com.challenge.picpay.models.UserRole;
import com.challenge.picpay.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserModel findUserModel(String cpf){
        return userRepository.findById(cpf).get();
    }

    public UserModel registerUser(UserModel user){
        userRepository.save(user);

        return findUserModel(user.getCpf_cnpj());

    }


    @Transactional
    public void authenticationTransfer(TransferUser transferUser){

        UserModel userPayer = findUserModel(transferUser.payer());
        UserModel userPayee = findUserModel(transferUser.payee());

        if(userPayer.getBalance() < transferUser.value())
            throw new RuntimeException("O Pagante não tem valor suficiente para transferir!");

        authenticationRole(userPayer.getRole());

        userPayer.setBalance(userPayer.getBalance() - transferUser.value());
        userPayee.setBalance(userPayee.getBalance() + transferUser.value());

        userRepository.save(userPayee);
        userRepository.save(userPayer);
    }


    public void authenticationRole(UserRole role){

        if(UserRole.LOJISTA == role)
            throw new RuntimeException("O Pagante é LOJISTA e não pode enviar dinheiro!");
    }

}
