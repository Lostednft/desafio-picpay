package com.challenge.picpay.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    private String cpf_cnpj;
    private String name;
    @Column(unique = true)
    private String email;
    private String password;
    private Float balance = 0.0f;
    private UserRole role;


}
