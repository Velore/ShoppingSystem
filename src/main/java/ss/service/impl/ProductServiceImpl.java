package ss.service.impl;

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


    @Override
    public boolean insertProduct(Product product) {
        ProductMapper mapper = MybatisUtils.getMapper(ProductMapper.class);
        if(mapper.queryProductByProductId(product.getProductId())==null){
            return mapper.insertProduct(product) == 1;
        }
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        ProductMapper mapper = MybatisUtils.getMapper(ProductMapper.class);
        if(mapper.queryProductByProductId(product.getProductId())==null){
            return false;
        }
        return mapper.updateProduct(product) == 1;
    }

    @Override
    public boolean deleteProduct(String productId) {
        ProductMapper mapper = MybatisUtils.getMapper(ProductMapper.class);
        return mapper.deleteProduct(productId) == 1;
    }

    @Override
    public List<Product> queryAllProduct() {
        ProductMapper mapper = MybatisUtils.getMapper(ProductMapper.class);
        return mapper.queryAllProduct();
    }

    @Override
    public Product queryProductByProductId(String productId) {
        ProductMapper mapper = MybatisUtils.getMapper(ProductMapper.class);
        return mapper.queryProductByProductId(productId);
    }

    @Override
    public List<Product> queryProductLikeName(String productName) {
        ProductMapper mapper = MybatisUtils.getMapper(ProductMapper.class);
        return mapper.queryProductLikeName(productName);
    }
}
