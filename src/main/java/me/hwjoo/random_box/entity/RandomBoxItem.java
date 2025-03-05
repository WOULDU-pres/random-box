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
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "random_box_items")
public class RandomBoxItem {

    @Id
    @Column(length = 36)
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ItemCategory category;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(nullable = false)
    private Double probability;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "box_id", nullable = false)
    private RandomBox randomBox;

    @OneToMany(mappedBy = "randomBoxItem", fetch = FetchType.LAZY)
    private List<RandomBoxPurchaseResult> purchaseResults = new ArrayList<>();

    @Builder
    public RandomBoxItem(String id, String name, String description, String imageUrl,
                         ItemCategory category, BigDecimal value, Double probability, RandomBox randomBox) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.value = value;
        this.probability = probability;
        this.randomBox = randomBox;
    }

    public void update(String name, String description, String imageUrl,
                      ItemCategory category, BigDecimal value, Double probability) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.value = value;
        this.probability = probability;
    }
} 