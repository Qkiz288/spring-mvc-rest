package com.kkukielka.api.v1.mapper;

import com.kkukielka.api.v1.model.CategoryDTO;
import com.kkukielka.domain.Category;
import org.junit.Test;

import static org.junit.Assert.*;

public class CategoryMapperTest {

    CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void categoryToCategoryDTO() {
        // given
        Category category = new Category();
        category.setId(1L);
        category.setName("Kamil");

        // when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        // then
        assertEquals(category.getId(), categoryDTO.getId());
        assertEquals(category.getName(), categoryDTO.getName());

    }

}