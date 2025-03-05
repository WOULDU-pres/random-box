package me.hwjoo.random_box.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "random_boxes")
public class RandomBox {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoxType type;

    @Column(name = "sold_count", nullable = false)
    private Integer soldCount;

    @Column(name = "max_quantity", nullable = false)
    private Integer maxQuantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private RandomBoxEvent event;

    @OneToMany(mappedBy = "randomBox", fetch = FetchType.LAZY)
    private List<RandomBoxItem> items = new ArrayList<>();

    @OneToMany(mappedBy = "randomBox", fetch = FetchType.LAZY)
    private List<RandomBoxPurchase> purchases = new ArrayList<>();

    @Builder
    public RandomBox(String id, String name, String description, String imageUrl,
                    BigDecimal price, BoxType type, Integer maxQuantity, RandomBoxEvent event) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.type = type;
        this.soldCount = 0;
        this.maxQuantity = maxQuantity;
        this.event = event;
    }

    public void update(String name, String description, String imageUrl,
                      BigDecimal price, BoxType type, Integer maxQuantity) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.price = price;
        this.type = type;
        this.maxQuantity = maxQuantity;
    }

    public boolean isSoldOut() {
        return soldCount >= maxQuantity;
    }

    public void incrementSoldCount() {
        this.soldCount++;
    }
} 