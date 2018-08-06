package nl.utwente.ing.service;

import nl.utwente.ing.model.Category;
import nl.utwente.ing.model.transaction.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getAllTransactions(int offset, int limit, Category category);
}
