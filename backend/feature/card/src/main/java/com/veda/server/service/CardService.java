package com.veda.server.service;

import com.veda.server.dto.request.CardRequest;
import com.veda.server.dto.request.UpdateCardRequest;
import com.veda.server.dto.response.CardResponse;
import com.veda.server.dto.response.CategoryResponse;
import com.veda.server.exception.CardNotFound;
import com.veda.server.exception.CategoryNotFound;
import com.veda.server.model.Card;
import com.veda.server.model.Category;
import com.veda.server.repository.CardRepository;
import com.veda.server.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public CardResponse createCard(CardRequest request) {
        log.info("Creating card: '{}' in category ID: {}", request.name(), request.categoryId());

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new CategoryNotFound(request.categoryId()));

        Card card = new Card();
        card.setName(request.name());
        card.setDescription(request.description());
        card.setIsVisible((byte) 1);
        card.setCategory(category);

        updateCategoryCardCount(category, 1);

        Card savedCard = cardRepository.save(card);
        return mapToResponse(savedCard);
    }

    @Transactional(readOnly = true)
    public List<CardResponse> getAllCards(Integer limit) {
        return cardRepository.findAll(PageRequest.of(0, limit)).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CardResponse> getCardsByCategoryId(Integer categoryId, Integer limit) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new CategoryNotFound(categoryId);
        }

        return cardRepository.findAllByCategoryId(categoryId, PageRequest.of(0, limit)).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CardResponse getCardById(Integer id) {
        return cardRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new CardNotFound(id));
    }

    @Transactional
    public CardResponse updateCard(Integer id, UpdateCardRequest request) {
        log.info("Updating card id: {}", id);
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFound(id));

        if (request.name() != null) card.setName(request.name());
        if (request.description() != null) card.setDescription(request.description());

        if (request.isVisible() != null) {
            card.setIsVisible((byte) (request.isVisible() ? 1 : 0));
        }

        if (request.categoryId() != null && !request.categoryId().equals(card.getCategory().getId())) {
            Category oldCategory = card.getCategory();
            Category newCategory = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new CategoryNotFound(request.categoryId()));

            updateCategoryCardCount(oldCategory, -1);
            updateCategoryCardCount(newCategory, 1);

            card.setCategory(newCategory);
        }

        return mapToResponse(cardRepository.save(card));
    }

    @Transactional
    public void deleteCardById(Integer id) {
        log.warn("Deleting card id: {}", id);
        Card card = cardRepository.findById(id)
                .orElseThrow(() -> new CardNotFound(id));

        updateCategoryCardCount(card.getCategory(), -1);

        cardRepository.delete(card);
    }

    private void updateCategoryCardCount(Category category, int delta) {
        int current = category.getCardCount() == null ? 0 : category.getCardCount();
        category.setCardCount(Math.max(0, current + delta));
        categoryRepository.save(category);
    }

    private CardResponse mapToResponse(Card card) {
        Category cat = card.getCategory();

        CategoryResponse categoryResponse = new CategoryResponse(
                cat.getId(),
                cat.getName(),
                cat.getSlug(),
                cat.getDescription(),
                cat.getIsVisible(),
                cat.getCardCount()
        );

        return new CardResponse(
                card.getId(),
                card.getName(),
                card.getDescription(),
                card.getIsVisible() == 1,
                categoryResponse
        );
    }
}