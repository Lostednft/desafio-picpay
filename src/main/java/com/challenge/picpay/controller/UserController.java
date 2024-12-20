package com.challenge.picpay.controller;

import com.challenge.picpay.models.UserModel;
import com.challenge.picpay.models.TransferUser;
import com.challenge.picpay.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity saveUser(@RequestBody UserModel user){

        userService.registerUser(user);

        if(user.getCpf_cnpj().isEmpty() ||
                user.getName().isEmpty() ||
                user.getEmail().isEmpty() ||
                user.getPassword().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Preencha todos os campos necessarios!");

        if(userService.registerUser(user).getCpf_cnpj().equals(user.getCpf_cnpj()))
            return ResponseEntity.ok("Conta criado com sucesso!");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(user);
    }

    @GetMapping("/{cpf}")
    public ResponseEntity findUser(@PathVariable String cpf){
        return ResponseEntity.ok(userService.findUserModel(cpf));
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transferValue(@RequestBody TransferUser user){

        if(user.value() < 0 ||
                user.payer().isEmpty() ||
                user.payee().isEmpty())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Preencha todos os campos corretamente!");

        userService.authenticationTransfer(user);

        return ResponseEntity.ok("Transferencia Feita com Sucesso");
    }
}
