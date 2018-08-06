package nl.utwente.ing.repository;

import nl.utwente.ing.models.Category;
import nl.utwente.ing.models.transaction.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findTransactionsByCategory (Pageable sortCriteria, Category category);
}
