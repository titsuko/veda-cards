package com.veda.server.controller;

import com.veda.server.dto.request.CardRequest;
import com.veda.server.dto.request.UpdateCardRequest;
import com.veda.server.dto.response.CardResponse;
import com.veda.server.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CardResponse> createCard(@Valid @RequestBody CardRequest request) {
        return ResponseEntity.ok(cardService.createCard(request));
    }

    @GetMapping
    public ResponseEntity<List<CardResponse>> getAllCards(@RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(cardService.getAllCards(limit));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<CardResponse>> getCardsByCategory(
            @PathVariable Integer categoryId,
            @RequestParam(defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(cardService.getCardsByCategoryId(categoryId, limit));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> getCardById(@Valid @PathVariable Integer id) {
        return ResponseEntity.ok(cardService.getCardById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> updateCard(
            @Valid @PathVariable Integer id,
            @Valid @RequestBody UpdateCardRequest request
    ) {
        return ResponseEntity.ok(cardService.updateCard(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCardById(@Valid @PathVariable Integer id) {
        cardService.deleteCardById(id);
    }
}