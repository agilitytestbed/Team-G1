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

    private SessionRepository sessionRepository;

    @Autowired
    public SessionController(TransactionRepository transactionRepo, SessionRepository sessionRepository) {
        this.transactionRepo = transactionRepo;
        this.sessionRepository = sessionRepository;
    }

    private static final Logger log = LoggerFactory.getLogger(SessionController.class);

    @RequestMapping(value = "/sessions", method = RequestMethod.POST,
                   consumes = "application/json", produces = "application/json")
    public ResponseEntity doPostSessions(@RequestBody Session session){
        log.info("The session id is being set...");
        for (Transaction tr : transactionRepo.findAll()){
            session.setTransaction(tr);
            sessionRepository.save(session);
            transactionRepo.save(tr);
        }
        log.info("The initial data now has the following sessionId: " + session);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
