package ru.hse.bot.domain.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@IdClass(TrackPrimaryKey.class)
@Table(name = "tracking")
public class Track {
    @Id
    @Column(name = "chat_id")
    private Long chatId;

    @Id
    @Column(name = "wallet_id")
    private Long walletId;
}
