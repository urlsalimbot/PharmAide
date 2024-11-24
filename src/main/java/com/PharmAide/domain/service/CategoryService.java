package com.PharmAide.domain.service;



import com.PharmAide.domain.dao.Category;
import com.PharmAide.domain.repository.interfaces.ICategoryRepository;
import jakarta.inject.Inject;

import java.util.List;

public class CategoryService {
    ICategoryRepository categoryRepository;

    @Inject
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public  Category addCategory(Category category) {
        var res = categoryRepository.save(category);
        return res.orElse(null);
    }

    public List<Category> getCategories() {
        return categoryRepository.listCategories();
    }

    public Category getCategory(String s) {
        var result = categoryRepository.getbyName(s);
        return result.orElse(null);
    }

    public Category updateCategory(Category category, String s) {
        var res = categoryRepository.update(category,s);
        return res.orElse(null);
    }

    public void deleteCategory(String s) {
        categoryRepository.delete(s);
    }
}
