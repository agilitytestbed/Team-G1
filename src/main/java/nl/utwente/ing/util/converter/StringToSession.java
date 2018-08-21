package nl.utwente.ing.util.converter;

        import nl.utwente.ing.model.Session;
        import nl.utwente.ing.repository.SessionRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.core.convert.converter.Converter;
        import org.springframework.stereotype.Component;

        import java.util.Optional;

@Component
public class StringToSession implements Converter<String, Session>{

    private SessionRepository sessionRepo;

    @Autowired
    public StringToSession(SessionRepository sessionRepo) {
        this.sessionRepo = sessionRepo;
    }

    @Override
    public Session convert(String uuid) {
        Optional<Session> queryResponse = sessionRepo.findById(uuid);
        return queryResponse.orElseGet(() -> new Session(uuid));
    }
}
