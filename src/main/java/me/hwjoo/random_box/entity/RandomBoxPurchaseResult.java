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
@Table(name = "random_box_purchase_results")
public class RandomBoxPurchaseResult {

    @Id
    @Column(length = 36)
    private String id;

    @Column(name = "revealed_at", nullable = false)
    private LocalDateTime revealedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private RandomBoxPurchase randomBoxPurchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private RandomBoxItem randomBoxItem;

    @Builder
    public RandomBoxPurchaseResult(String id, RandomBoxPurchase randomBoxPurchase, RandomBoxItem randomBoxItem) {
        this.id = id;
        this.randomBoxPurchase = randomBoxPurchase;
        this.randomBoxItem = randomBoxItem;
        this.revealedAt = LocalDateTime.now();
    }
} 