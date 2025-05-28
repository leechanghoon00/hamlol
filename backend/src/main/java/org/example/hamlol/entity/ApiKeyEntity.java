package org.example.hamlol.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "api_key")
public class ApiKeyEntity {

    @Id
    @Column(name = "api_key", nullable = false)
    private String apiKey;}
