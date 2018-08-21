package nl.utwente.ing.controllers;

import nl.utwente.ing.repository.SessionRepository;
import nl.utwente.ing.repository.TransactionRepository;
import nl.utwente.ing.model.Session;
import nl.utwente.ing.model.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {

    private TransactionRepository transactionRepo;

    private SessionRepository sessionRepo;

    @Autowired
    public SessionController(TransactionRepository transactionRepo, SessionRepository sessionRepository) {
        this.transactionRepo = transactionRepo;
        this.sessionRepo = sessionRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(SessionController.class);

    @RequestMapping(value = "/sessions", method = RequestMethod.POST,
                   consumes = "application/json", produces = "application/json")
    public ResponseEntity doPostSessions(@RequestBody Session session){
        log.info("The session id is being set...");
        sessionRepo.save(session);
        for (Transaction tr : transactionRepo.findAll()){
            tr.setSession(session);
            transactionRepo.save(tr);
        }
        log.info(sessionRepo.findById(session.getId()).get().toString());
        log.info("The initial data now has the previously specified sessionId.");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
