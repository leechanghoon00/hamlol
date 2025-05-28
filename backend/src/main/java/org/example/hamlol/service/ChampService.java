package org.example.hamlol.service;

import org.example.hamlol.dto.ChampDTO;

import java.util.List;

public interface ChampService {

    void saveChamp();

    List<ChampDTO> getChamp();
}
