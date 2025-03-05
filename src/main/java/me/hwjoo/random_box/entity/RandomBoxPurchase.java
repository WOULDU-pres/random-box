package me.hwjoo.random_box.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "random_box_purchases")
public class RandomBoxPurchase {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "user_uuid", nullable = false, length = 36)
    private String userUuid;

    @Column(name = "purchase_date", nullable = false)
    private LocalDateTime purchaseDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id", nullable = false)
    private RandomBox randomBox;

    @OneToOne(mappedBy = "randomBoxPurchase", fetch = FetchType.LAZY)
    private RandomBoxPurchaseResult purchaseResult;

    @Builder
    public RandomBoxPurchase(String id, String userUuid, RandomBox randomBox) {
        this.id = id;
        this.userUuid = userUuid;
        this.randomBox = randomBox;
        this.purchaseDate = LocalDateTime.now();
    }
} 