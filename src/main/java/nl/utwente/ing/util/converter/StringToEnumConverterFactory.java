package nl.utwente.ing.util.converter;

import nl.utwente.ing.model.Category;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.stereotype.Component;

@Component
public class StringToEnumConverterFactory implements ConverterFactory<String, Category> {

    @Override
    public <T extends Category> Converter<String, T> getConverter(Class<T> aClass) {
        return null;
    }

    private final class StringToEnumConverter<T extends Enum> implements Converter<String, T>{
        private Class<T> enumType;

        public StringToEnumConverter(Class<T> enumType){
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            return (T) Enum.valueOf(this.enumType, source.trim());
        }
    }
}
