package com.PharmAide.domain.repository.interfaces;

import com.PharmAide.domain.dao.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryRepository
{
    public Optional<Category> save(Category category);

    public List<Category> listCategories();

    public Optional<Category> getbyId(Integer id);

    public Optional<Category> getbyName(String categoryName);

    public  Optional<Category> update(Category category, String categoryName);

    public void delete(String name);
}
