package nl.utwente.ing.config;

import nl.utwente.ing.repository.CategoryRepository;
import nl.utwente.ing.repository.TransactionRepository;
import nl.utwente.ing.model.Category;
import nl.utwente.ing.model.transaction.Transaction;
import nl.utwente.ing.util.generator.TransactionGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DemoData implements ApplicationRunner {


    private TransactionRepository transactionRepo;

    private CategoryRepository categoryRepo;

    private static final Logger log = LoggerFactory.getLogger(DemoData.class);

    @Autowired
    public DemoData(TransactionRepository transactionRepo, CategoryRepository categoryRepo) {
        this.transactionRepo = transactionRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        int bound = 50;
        log.info("Load initial data into the service...");
        TransactionGenerator transGen = new TransactionGenerator();
        Set<Category> categorySet = transGen.getCategorySet();
        categoryRepo.saveAll(categorySet);
        transactionRepo.saveAll(transGen.getTransactions(bound));
        log.info("Finished loading the initial data!");
    }
}
