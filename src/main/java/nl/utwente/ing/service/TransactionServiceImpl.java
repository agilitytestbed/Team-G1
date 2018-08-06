package nl.utwente.ing.service;

import nl.utwente.ing.repository.CategoryRepository;
import nl.utwente.ing.repository.TransactionRepository;
import nl.utwente.ing.model.Category;
import nl.utwente.ing.model.transaction.Transaction;
import nl.utwente.ing.util.filter.OffsetLimitAndSortFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private CategoryRepository categoryRepo;

    private TransactionRepository transactionRepo;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepo, JdbcTemplate jdbcTemplate) {
        this.transactionRepo = transactionRepo;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Transaction> getAllTransactions(int offset, int limit, Category category) throws DataAccessException {
        return jdbcTemplate.query("SELECT *" +
                                      "FROM Transaction t, Category c " +
                                      "WHERE c.cid = ? AND c.cid = t.category " +
                                      "ORDER BY t.tid " +
                                      "LIMIT ? " +
                                      "OFFSET ?",
                                      new Object[] {category.getId(), limit, offset},
                                      new Transaction.RowMapper(categoryRepo) );
    }
    public List<Transaction> findByCategoryWithOffsetAndLimit(int offset, int limit, Category category){
        Pageable pageable = new OffsetLimitAndSortFilter(offset, limit);
        if (category == null){
            return findWithOffsetAndLimit(offset, limit);
        } else {
            return transactionRepo.findTransactionsByCategory(pageable, category);
        }

    }
    private List<Transaction> findWithOffsetAndLimit(int offset, int limit){
        Pageable pageable = new OffsetLimitAndSortFilter(offset, limit);
        return transactionRepo.findAll(pageable).getContent();
    }
}
