package org.example.hamlol.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.hamlol.service.ChampService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/champ")
@Tag(name = "saveChampAPI", description = "챔피언 데이터 저장API")

public class ChampController {
    @Autowired
    private ChampService champService;

    @GetMapping("/save")
    public ResponseEntity<String> saveChamp() {
        champService.saveChamp();
        return ResponseEntity.ok("챔피언 데이터 저장 완료");
    }
}

