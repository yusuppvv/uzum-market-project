package com.company.auth.verfication;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@Table(name = "verification")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Email
    private String email;
    private Integer code;
    @Column(columnDefinition = "default true")
    private Boolean visibility;
}
