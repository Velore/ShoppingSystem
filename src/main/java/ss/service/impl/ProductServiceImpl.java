package ss.service.impl;

import org.apache.ibatis.session.SqlSession;
import ss.dao.ProductMapper;
import ss.po.Product;
import ss.service.ProductService;
import ss.utils.MybatisUtils;

import java.util.List;

/**
 * @author Velore
 * @date 2022/1/7
 **/
public class ProductServiceImpl implements ProductService {

    SqlSession session = MybatisUtils.getSqlSession();
    ProductMapper mapper = session.getMapper(ProductMapper.class);

    @Override
    public boolean insertProduct(Product product) {
        SqlSession session = MybatisUtils.getSqlSession();

        ProductMapper mapper = session.getMapper(ProductMapper.class);
        if(mapper.queryProductByProductId(product.getProductId())==null){
            if(mapper.insertProduct(product) == 1){
                session.commit();
                session.close();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        SqlSession session = MybatisUtils.getSqlSession();
        ProductMapper mapper = session.getMapper(ProductMapper.class);
        if(mapper.queryProductByProductId(product.getProductId())==null){
            return false;
        }
        if(mapper.updateProduct(product)==1){
            session.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteProduct(String productId) {
        SqlSession session = MybatisUtils.getSqlSession();
        ProductMapper mapper = session.getMapper(ProductMapper.class);
        if(mapper.deleteProduct(productId) == 1){
            session.commit();
            session.close();
            return true;
        }
        return false;
    }

    @Override
    public List<Product> queryAllProduct() {
        return mapper.queryAllProduct();
    }

    @Override
    public Product queryProductByProductId(String productId) {
        return mapper.queryProductByProductId(productId);
    }

    @Override
    public List<Product> queryProductLikeName(String productName) {
        return mapper.queryProductLikeName(productName);
    }
}
