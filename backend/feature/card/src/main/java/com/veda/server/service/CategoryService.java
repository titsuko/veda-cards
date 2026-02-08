package com.veda.server.service;

import com.veda.server.dto.request.CategoryRequest;
import com.veda.server.dto.request.UpdateCategoryRequest;
import com.veda.server.dto.response.CategoryResponse;
import com.veda.server.exception.CategoryNotFound;
import com.veda.server.exception.DuplicateCategorySlug;
import com.veda.server.model.Category;
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
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        log.info("Creating new category: {}", request.name());

        if (categoryRepository.existsBySlug(request.slug())) {
            throw new DuplicateCategorySlug(request.slug());
        }

        Category category = new Category();
        category.setName(request.name());
        category.setSlug(request.slug());
        category.setDescription(request.description());
        category.setIsVisible((byte) 1);
        category.setCardCount(0);

        Category saved = categoryRepository.save(category);
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories(Integer limit) {
        return categoryRepository.findAll(PageRequest.of(0, limit)).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Integer id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound(id));
        return mapToResponse(category);
    }

    @Transactional
    public CategoryResponse updateCategory(Integer id, UpdateCategoryRequest request) {
        log.info("Updating category id: {}", id);

        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound(id));

        if (request.slug() != null && !request.slug().equals(category.getSlug())) {
            if (categoryRepository.existsBySlug(request.slug())) {
                throw new DuplicateCategorySlug(request.slug());
            }
            category.setSlug(request.slug());
        }

        if (request.name() != null) category.setName(request.name());
        if (request.description() != null) category.setDescription(request.description());

        if (request.isVisible() != null) {
            category.setIsVisible((byte) (request.isVisible() ? 1 : 0));
        }

        Category updated = categoryRepository.save(category);
        return mapToResponse(updated);
    }

    @Transactional
    public void deleteCategoryById(Integer id) {
        log.warn("Deleting category id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFound(id));

        categoryRepository.delete(category);
    }

    private CategoryResponse mapToResponse(Category c) {
        return new CategoryResponse(
                c.getId(),
                c.getName(),
                c.getSlug(),
                c.getDescription(),
                c.getIsVisible(),
                c.getCardCount()
        );
    }
}