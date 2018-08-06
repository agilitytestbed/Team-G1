package tests;

import nl.utwente.ing.util.generator.TransactionGenerator;
import nl.utwente.ing.model.transaction.Iban;
import nl.utwente.ing.model.transaction.Transaction;
import org.junit.Before;
import org.junit.Test;

public class TransactionGeneratorTest {

    private TransactionGenerator transGen;

    @Before
    public void setUp() throws Exception {
        transGen = new TransactionGenerator();
    }

    @Test
    public void print(){
        System.out.println(Iban.StateCode.NL.ordinal());
        System.out.println(Iban.StateCode.DE.ordinal());
        System.out.println(Iban.StateCode.GB.ordinal());
    }

    @Test
    public void OffsetDateTime(){
        for (Transaction tr: transGen.getTransactions(100)){
            System.out.println(tr);
        }
    }
}