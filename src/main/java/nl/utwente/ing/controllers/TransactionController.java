package nl.utwente.ing.controllers;

import nl.utwente.ing.repository.CategoryRepository;
import nl.utwente.ing.repository.SessionRepository;
import nl.utwente.ing.repository.TransactionRepository;
import nl.utwente.ing.service.TransactionServiceImpl;
import nl.utwente.ing.exception.InvalidSessionIdException;
import nl.utwente.ing.exception.NoSuchIdForTransactionException;
import nl.utwente.ing.model.Category;
import nl.utwente.ing.model.Session;
import nl.utwente.ing.model.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class TransactionController {

    private TransactionRepository transactionRepo;

    private CategoryRepository categoryRepo;

    private SessionRepository sessionRepo;

    private TransactionServiceImpl transactionServ;

    @Autowired
    public TransactionController(TransactionRepository transactionRepo, CategoryRepository categoryRepo,
                                 SessionRepository sessionRepo, TransactionServiceImpl transactionServ) {
        this.transactionRepo = transactionRepo;
        this.categoryRepo = categoryRepo;
        this.sessionRepo = sessionRepo;
        this.transactionServ = transactionServ;
    }



    private static final Logger log = LoggerFactory.getLogger(TransactionController.class);

    @RequestMapping(value = "/transactions", method = RequestMethod.GET)
    public List<Transaction> doGetTransactions(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                 @RequestParam(value = "limit", defaultValue = "20") int limit,
                                 @RequestParam(value = "category", required = false) Category category,
                                 @RequestParam(value = "session_id", required = false) Session sessionId,
                                 @RequestHeader(value = "X-session-ID", required = false) Session headerSessionID){
        log.info("'GET /transactions' has been requested...");
        RuntimeException invalidSession = new InvalidSessionIdException();
        if (sessionId == null && headerSessionID == null){
            log.warn("No sessionId provided !");
            throw invalidSession;
        }
        if (sessionId == null && !sessionRepo.exists(Example.of(headerSessionID))){
            log.warn("Incorrect headerSessionID provided: " + headerSessionID + " !");
            throw invalidSession;
        }
        if (headerSessionID == null && !sessionRepo.exists(Example.of(sessionId))){
            log.warn("Incorrect sessionId provided: " + sessionId + " !");
            throw invalidSession;
        }
        if (sessionId != null && !sessionRepo.exists(Example.of(sessionId))
                              && headerSessionID != null && !sessionRepo.exists(Example.of(headerSessionID))){
            log.warn("Incorrect headerSessionID and sessionId provided: " + headerSessionID + " ," + sessionId + " !");
            throw invalidSession;
        }

        List<Transaction> transactions = transactionServ.findByCategoryWithOffsetAndLimit(offset, limit, category);
        for (Transaction tr: transactions){
            log.info(tr.toString());
        }
        log.info("The 'GET /transactions' response has been sent!");
        return transactions;
    }

    @RequestMapping(value = "transactions/{transactionId}", method = RequestMethod.PUT)
    public Transaction doPutTransaction(@PathVariable Long transactionId, @RequestBody Transaction requestTransaction)
                                                                                        throws NoSuchIdForTransactionException {
        Transaction transaction = null;
        if (transactionRepo.existsById(transactionId)){
            transaction = transactionRepo.findById(transactionId).get();
            transaction.setCategory(categoryRepo.findByName("food & drinks"));
            log.info("Transaction saved into the service: " + requestTransaction.toString());
            return transactionRepo.save(transaction);
        } else {
            throw new NoSuchIdForTransactionException(transactionId);
        }
    }
}
