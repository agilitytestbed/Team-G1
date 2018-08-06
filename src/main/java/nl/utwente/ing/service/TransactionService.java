package nl.utwente.ing.service;

import nl.utwente.ing.models.Category;
import nl.utwente.ing.models.transaction.Transaction;

import java.util.List;

public interface SpringService {
    List<Transaction> getAllTransactions(int offset, int limit, Category category);
}
