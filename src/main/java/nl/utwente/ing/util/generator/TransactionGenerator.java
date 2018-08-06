package nl.utwente.ing.util.generator;

import nl.utwente.ing.model.Category;
import nl.utwente.ing.model.transaction.Iban;
import nl.utwente.ing.model.transaction.Transaction;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class TransactionGenerator {

    private Set<Category> categorySet;
    private Random rnd;


    public TransactionGenerator() {
        rnd = new Random();
        rnd.setSeed(123456789);
        categorySet = new HashSet<>();
        Category foodAndDrinks = new Category("food & drinks");
        Category home = new Category("home");
        Category houseAndGarden = new Category("house & garden");
        Category transport = new Category("transport");
        Category shopping = new Category("shopping");
        Category leisure = new Category("leisure");
        Category healthAndBeauty = new Category("health & beauty");
        Category transfers = new Category("transfers");
        Category other = new Category("other");
        categorySet.add(foodAndDrinks);
        categorySet.add(home);
        categorySet.add(houseAndGarden);
        categorySet.add(shopping);
        categorySet.add(transport);
        categorySet.add(leisure);
        categorySet.add(healthAndBeauty);
        categorySet.add(transfers);
        categorySet.add(other);
    }

    private Transaction generateTransaction(){
        Iban iban = generateIban();
        LocalDateTime dateTime = generateDateTime();
        double amount = generateAmount();
        Transaction.Type type = generateType();
        Category category = generateCategory();
        //long id = -1 * Math.abs(rnd.nextInt());
        return new Transaction(dateTime, amount, iban.toString(), type, category);
    }

    private Category generateCategory(){
        int setSize = categorySet.size();
        int i = 0, item = rnd.nextInt(setSize);
        for(Category cat: categorySet){
            if(item == i){
                return cat;
            }
            i++;
        }
        return new Category();
    }

    private Iban generateIban(){
        return new Iban(generateStateCode(), generateCheckDigits(), generateBban());
    }
    private Transaction.Type generateType(){
        return Transaction.Type.values()[rnd.nextInt(2)];
    }

    private String generateBban(){
        String bban = "" ;
        for(int i = 0; i < 30; i++){
            bban += rnd.nextInt(10);
        }
        return bban;
    }

    private int[] generateCheckDigits(){

        return new int[]{rnd.nextInt(10), rnd.nextInt(10)};
    }

    private Iban.StateCode generateStateCode(){
        Iban.StateCode[] stateCodes = Iban.StateCode.values();
        return stateCodes[rnd.nextInt(9)];
    }

    private double generateAmount(){
        return (double) ((int)((rnd.nextFloat() * 10 * rnd.nextInt(1000)) * 100 )) / 100;
    }

    private LocalDateTime generateDateTime() {
        return LocalDateTime.ofInstant( generateInstant(), generateZoneId() );
    }

    private Instant generateInstant() {
        long startYear = -946771200L; // January 1st , 1940
        return Instant.ofEpochSecond( startYear + Math.abs( rnd.nextLong() ) % (78L * 365 * 24 * 60 * 60) );
    }

    private ZoneId generateZoneId() {
        Set<String> zoneIds = ZoneId.getAvailableZoneIds();
        String zoneId = "";
        int i = 0, itemID = rnd.nextInt(zoneIds.size());
        for (String str: zoneIds){
            if (i == itemID){
                zoneId = str.intern();
                break;
            }
            i++;
        }
        return ZoneId.of(zoneId);
    }

    public List<Transaction> getTransactions(int bound){
        List<Transaction> transactions = new ArrayList<>();
        for (int i = 0; i < bound; i++){
            transactions.add(generateTransaction());
        }
        return transactions;
    }

    public Set<Category> getCategorySet() {
        return categorySet;
    }
}
