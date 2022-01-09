package ss.service.impl;

import ss.constant.Constant;
import ss.po.View;
import ss.service.MarketService;
import ss.service.ViewService;

/**
 * @author Velore
 * @date 2022/1/9
 **/
public class ViewServiceImpl implements ViewService {

    @Override
    public boolean checkUser(View view) {
        if(Constant.DEFAULT_ADMIN_ID.equals(view.getUserId())){
            return true;
        }
        MarketService marketService = new MarketServiceImpl();
        return view.getUserId().equals(marketService.queryMarketByMarketId(view.getMarketId()).getUserId());
    }
}
