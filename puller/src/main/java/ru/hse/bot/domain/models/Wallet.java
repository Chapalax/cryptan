package ru.hse.bot.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data
@NoArgsConstructor
@Table(name = "wallets")
public class Wallet {
    @Id
    @SequenceGenerator(name = "wallet_seq", sequenceName = "wallet_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wallet_seq")
    @Column(name = "id")
    private Long id;

    @Column(name = "number", nullable = false, unique = true)
    private String number;

    @Column(name = "last_activity", nullable = false)
    private Instant lastActivity = Instant.now();

    @Column(name = "checked_at", nullable = false)
    private Instant checkedAt = Instant.now();
}
