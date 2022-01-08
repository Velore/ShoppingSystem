import org.junit.jupiter.api.Test;
import ss.bo.QueryMarketBo;
import ss.po.Market;
import ss.service.MarketService;
import ss.service.impl.MarketServiceImpl;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/8
 **/
public class MarketTest {

    final MarketService service = new MarketServiceImpl();

    @Test
    public void insertMarketTest(){
        Market market = new Market("test_m", "test_name", "u1");
        System.out.println(service.queryAllMarket());
        System.out.println(service.insertMarket(market));
        System.out.println(service.queryAllMarket());
    }

    @Test
    public void updateMarketTest(){
        Market market = new Market("test_m", "test_name1", "u1");
        System.out.println(service.queryAllMarket());
        System.out.println(service.updateMarket(market));
        System.out.println(service.queryAllMarket());
    }

    @Test
    public void deleteMarketTest(){
        System.out.println(service.queryAllMarket());
        System.out.println(service.deleteMarket("test_m"));
        System.out.println(service.queryAllMarket());
    }

    @Test
    public void queryAllMarketTest(){
        List<Market> marketList = service.queryAllMarket();
        for(Market m : marketList){
            System.out.println(m.toString());
        }
    }

    @Test
    public void queryMarketByMarketIdTest(){
        System.out.println(service.queryMarketByMarketId("ma"));
    }

    @Test
    public void queryMarketByQueryMarketBoTest(){
        QueryMarketBo bo1 = new QueryMarketBo("market", null);
        QueryMarketBo bo2 = new QueryMarketBo(null, "u1");
        QueryMarketBo bo3 = new QueryMarketBo("market", "u3");
        List<Market> mList1 = service.queryMarketByQueryMarketBo(bo1);
        List<Market> mList2 = service.queryMarketByQueryMarketBo(bo2);
        List<Market> mList3 = service.queryMarketByQueryMarketBo(bo3);
        for(Market m1 : mList1){
            System.out.println("bo1:"+m1.toString());
        }
        for(Market m2 : mList2){
            System.out.println("bo2:"+m2.toString());
        }
        for(Market m3 : mList3){
            System.out.println("bo3:"+m3.toString());
        }
    }
}