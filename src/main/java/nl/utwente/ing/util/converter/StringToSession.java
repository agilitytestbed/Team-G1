package nl.utwente.ing.util.converter;

        import nl.utwente.ing.model.Session;
        import org.springframework.core.convert.converter.Converter;
        import org.springframework.stereotype.Component;

@Component
public class StringToSession implements Converter<String, Session>{

    @Override
    public Session convert(String uuid) {
        return new Session(uuid);
    }
}
