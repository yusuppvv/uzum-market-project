package com.company.delivery;

import com.company.component.BaseMapper;
import com.company.users.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity(name = "delivery")
public class DeliveryEntity extends BaseMapper {
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;


    @Column(name = "user_id")
    private UUID userId;
}

