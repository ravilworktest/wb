package org.example.pageLoader;

import org.example.models.catalog.Product;
import org.example.models.catalog.RootCatalog;
import org.example.models.filters.RootFilter;
import org.example.utils.JSONUtil;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.example.utils.Util.printProductCount;


public class PageLoader {
    private Client client = new Client();
    private JSONUtil jsonUtil = new JSONUtil();
    private String FILTER_URL = "https://catalog.wb.ru/sellers/filters?curr=rub&dest=-1257786&supplier=%s";
  //  private String CATALOG_URL = "https://catalog.wb.ru/sellers/catalog?curr=rub&dest=-1257786&page=%s&supplier=%s";

    private String CATALOG_URL = "https://catalog.wb.ru/sellers/catalog?TestGroup=no_test&TestID=no_test&appType=1&curr=rub&dest=-1257786&page=%s&sort=popular&spp=33&supplier=%s";

    public List<Product> loadBySeller(List<String> sellerId){
        List<Product> products = new ArrayList<>();
        for(String id : sellerId) {
            int pageCount = calcPageCount(getFilterData(id));
            for (int i = 1; i <= pageCount; i++) {
                products.addAll(getCatalogData(String.format(CATALOG_URL, i, id)));
            }
        }
        printProductCount(products);

        return products;
    }


    private List<Product> getCatalogData(String url){
        HttpResponse<String> response = client.send(url);
        RootCatalog rootCatalog = jsonUtil.jsonToObject(response.body(), RootCatalog.class);
        if(rootCatalog == null){
           return Collections.emptyList();
        }
        return rootCatalog.getData().getProducts();
    }
    private RootFilter getFilterData(String sellerId){
        HttpResponse<String> response = client.send(String.format(FILTER_URL, sellerId));
        return jsonUtil.jsonToObject(response.body(), RootFilter.class);
    }
    private int calcPageCount(RootFilter filter){
        if(filter == null){
            System.out.println("ERROR RootFilter is null");
            return 0;
        }

        int total = filter.getData().getTotal();
        int result = total / 100;
        if(result == 0 && filter.getData().getTotal() > 0){
            return 1;
        } else{
            if(total - result > 0){
                 result += 1;
            }
            if(result > 100){
                return 100;
            }else {
                return result;
            }

        }
    }


}


