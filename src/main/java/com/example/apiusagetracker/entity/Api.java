package com.example.apiusagetracker.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "usages")
@Table(name="apis")
public class Api {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String endpoint;

    @OneToMany(mappedBy="api")
    private List<ApiUsage> usages;
}
