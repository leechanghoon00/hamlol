package org.example.hamlol.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "table_champ")
public class ChampEntity {
    @Id
    private String id;

    @Column(name = "champ_key")
    private String key;
    private String name;




    public ChampEntity(String id, String key, String name) {
        this.id = id;
        this.key = key;
        this.name = name;
    }

    public ChampEntity() {

    }
}
