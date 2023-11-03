import org.example.database.DBConnection;
import org.example.logic.CompareData;
import org.example.models.catalog.Product;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class CompareTest {

    @Test
    public void findNewCardTest() {
        List<Product> browserData = new ArrayList<>();
        browserData.add(new Product(1, 1001, 100, "dblabel", null));
        browserData.add(new Product(2, 1002, 105, "dblabel", null));
        browserData.add(new Product(4, 1004, 106, "dblabel", null));
        browserData.add(new Product(333, 1000, 106, "dblabel", null));

        List<Product> dbData = new ArrayList<>();
        dbData.add(new Product(1, 1001, 100, "dblabel", null));
        dbData.add(new Product(2, 1002, 105, "dblabel", null));
        dbData.add(new Product(4, 1004, 106, "dblabel", null));

        List<Product> expectedData = new ArrayList<>();
        expectedData.add(new Product(333, 1000, 106, "dblabel", null));

        CompareData compareData = new CompareData();
        List<Product> actualData = compareData.findNewProducts(browserData, dbData);

        assertEquals(expectedData.size(), actualData.size());
        assertEquals(expectedData.getFirst(), actualData.getFirst());
    }

    @Test
    public void findCardsWithChangedPriceTest() {
        List<Product> browserData = new ArrayList<>();
        browserData.add(new Product(1, 1001, 100, "price has decreased", null));
        browserData.add(new Product(2, 1002, 105, "price has increased", null));
        browserData.add(new Product(4, 1004, 106, "dblabel", null));
        browserData.add(new Product(444, 1000, 106, "dblabel", null));

        List<Product> dbData = new ArrayList<>();
        dbData.add(new Product(1, 1001, 101, "chaged price and history", "99,70"));
        dbData.add(new Product(2, 1002, 104, "chaged price", null));
        dbData.add(new Product(4, 1004, 106, "dblabel", null));

        List<Product> expectedData = new ArrayList<>();
        expectedData.add(new Product(1, 1001, 100, "dblabel", "99,70,101"));
        expectedData.add(new Product(2, 1002, 105, "dblabel", "104"));

        CompareData compareData = new CompareData();
        List<Product> actualData = compareData.findProductsWithChangedPrice(browserData, dbData);

        assertEquals(expectedData.size(), actualData.size());
        assertEquals(expectedData.getFirst().getSalePriceU(), actualData.getFirst().getSalePriceU());
        assertEquals(expectedData.getFirst().getPriceHistory(), actualData.getFirst().getPriceHistory());
        assertEquals(expectedData.getLast().getSalePriceU(), actualData.getLast().getSalePriceU());
        assertEquals(expectedData.getLast().getPriceHistory(), actualData.getLast().getPriceHistory());
    }

    @Test
    public void findProductsWhichAreNoLongerOnSaleTest() {
        List<Product> browserData = new ArrayList<>();
        browserData.add(new Product(1, 1001, 100, "dblabel", null));
        browserData.add(new Product(2, 1002, 105, "dblabel", null));

        List<Product> dbData = new ArrayList<>();
        dbData.add(new Product(1, 1001, 100, "dblabel", null));
        dbData.add(new Product(2, 1002, 105, "dblabel", null));
        dbData.add(new Product(2, 1003, 105, "deleted", null));
        dbData.add(new Product(4, 1004, 106, "deleted", null));

        List<Product> expectedData = new ArrayList<>();
        expectedData.add(new Product(2, 1003, 105, "deleted", null));
        expectedData.add(new Product(4, 1004, 106, "deleted", null));

        CompareData compareData = new CompareData();
        List<Product> actualData = compareData.findProductsWhichAreNoLongerOnSale(browserData, dbData);

        assertEquals(expectedData.size(), actualData.size());
        assertEquals(expectedData.getFirst(), actualData.getFirst());
        assertEquals(expectedData.getLast(), actualData.getLast());
    }


    @Test
    public void discountTest() {
        List<Product> data = new ArrayList<>();
        data.add(new Product(2, 1002, 10500, "price < avg price && > last price", "1000,10600"));
        data.add(new Product(1, 1001, 10000, "price > avg price && > last price", "20000,15000"));
        data.getFirst().print();
        CompareData compareData = new CompareData();
        List<Product> actualData = compareData.calculateDiscount(data);


        assertEquals(0.94d, actualData.getFirst().getDiscount());
        assertEquals(-81.03, actualData.getFirst().getAvgDiscount());
        assertEquals(33.33d, actualData.getLast().getDiscount());
        assertEquals(42.86, actualData.getLast().getAvgDiscount());
    }

    @Test
    public void writeUpdateTest() {
        List<Product> insertData = new ArrayList<>();
        insertData.add(new Product(101, 1001, 100, "dblabel", null));

        List<Product> updateData = new ArrayList<>();
        updateData.add(new Product(101, 1001, 333, "Updated", "222"));

        DBConnection c = new DBConnection();
        c.dbConnect();
        c.insertProducts(insertData);
        c.updateProduct(updateData);
        List<Product> actual = c.selectProductBySeller(Arrays.asList("101"));

        assertTrue(updateData.getFirst().equals(actual.getFirst()));
    }
}
