package nl.utwente.ing.util.converter;

import nl.utwente.ing.repository.CategoryRepository;
import nl.utwente.ing.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToCategory implements Converter<String, Category> {

    private CategoryRepository categoryRepo;

    @Autowired
    public StringToCategory(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category convert(String categoryName) {
        return categoryRepo.findByName(categoryName);
    }
}
