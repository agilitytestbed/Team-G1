package nl.utwente.ing.controllers;

import nl.utwente.ing.exception.transaction.TransactionNotFoundException;
import nl.utwente.ing.repository.CategoryRepository;
import nl.utwente.ing.repository.SessionRepository;
import nl.utwente.ing.repository.TransactionRepository;
import nl.utwente.ing.service.TransactionServiceImpl;
import nl.utwente.ing.exception.transaction.InvalidSessionIdException;
import nl.utwente.ing.model.Category;
import nl.utwente.ing.model.Session;
import nl.utwente.ing.model.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        log.info("'GET /transactions' has been requested ...");
        verifySessionId(sessionId, headerSessionID);
        List<Transaction> transactions = transactionServ.findByCategoryWithOffsetAndLimit(offset, limit, category);
        for (Transaction tr: transactions){
            log.info(tr.toString());
        }
        log.info("The 'GET /transactions' response has been sent !");
        return transactions;
    }

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public Transaction doPostTransactions(@RequestBody Transaction transaction,
                                 @RequestParam(value = "session_id", required = false) Session sessionId,
                                 @RequestHeader(value = "X-session-ID", required = false) Session headerSessionID){
        log.info("'POST /transactions' has been requested: " + transaction);
        verifySessionId(sessionId, headerSessionID);
        transaction.setId(0); // Set the default value for the Id
        Transaction result = transactionRepo.save(transaction);
        log.info("The 'POST /transactions' response has been sent: " + result);
        return result;
    }

    @RequestMapping(value = "transactions/{transactionId}", method = RequestMethod.GET)
    public Transaction doGetTransaction(@PathVariable Long transactionId,
                                 @RequestParam(value = "session_id", required = false) Session sessionId,
                                 @RequestHeader(value = "X-session-ID", required = false) Session headerSessionID){
        log.info("'GET /transactions/{" + transactionId + "}' has been requested ...");
        verifySessionId(sessionId, headerSessionID);
        RuntimeException transactionNotFound = new TransactionNotFoundException();
        if (transactionId == null){
            log.warn("TransactionId is null !");
            throw transactionNotFound;
        }
        if (transactionRepo.findById(transactionId).isPresent()){
            Transaction transaction = transactionRepo.findById(transactionId).get();
            log.info(transaction.toString());
            log.info("'GET /transactions/{" + transactionId + "}' response has been sent !" );
            return transaction;
        } else {
            log.warn("Transaction with Id: " + transactionId + " has not been found !");
            throw transactionNotFound;
        }
    }

    @RequestMapping(value = "/transactions/{transactionId}", method = RequestMethod.PUT)
    public Transaction doPutTransaction(@PathVariable Long transactionId, @RequestBody Transaction requestTransaction,
                                 @RequestParam(value = "session_id", required = false) Session sessionId,
                                 @RequestHeader(value = "X-session-ID", required = false) Session headerSessionID){

        log.info("'PUT /transactions/{" + transactionId + "}' has been requested ...");
        verifySessionId(sessionId, headerSessionID);
        RuntimeException transactionNotFound = new TransactionNotFoundException();
        if (transactionId == null){
            log.warn("TransactionId is null !");
            throw transactionNotFound;
        }
        if (transactionRepo.findById(transactionId).isPresent()){
            Transaction repoTransaction = transactionRepo.findById(transactionId).get();
            Category requestCategory = requestTransaction.getCategory();
            requestTransaction.setCategory(requestCategory == null || !categoryRepo.existsById(requestCategory.getId())
                    ? repoTransaction.getCategory() : requestCategory);
            requestTransaction.setId(repoTransaction.getId());
            log.info("Transaction has been saved: " + requestTransaction);
            return transactionRepo.save(requestTransaction);
        } else{
            log.warn("Transaction with Id: " + transactionId + " has not been found !");
            throw transactionNotFound;
        }
    }

    @ExceptionHandler(value = {HttpMessageNotReadableException.class, NumberFormatException.class})
    @ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED, reason = "Invalid input given")
    public void handleInvalidFormatException() {
    }

    private void verifySessionId(Session sessionId, Session headerSessionID) {
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
    }

}
