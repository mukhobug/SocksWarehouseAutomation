package com.example.sockswarehouseautomation.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "socks")
public class Socks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "color")
    private String color;
    @Column(name = "cotton_part")
    private int cottonPart;
    @Column(name = "quantity")
    private int quantity;
}
