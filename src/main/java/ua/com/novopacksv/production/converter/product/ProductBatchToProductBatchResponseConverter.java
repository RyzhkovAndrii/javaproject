package ua.com.novopacksv.production.converter.product;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.com.novopacksv.production.dto.product.ProductBatchResponse;
import ua.com.novopacksv.production.model.productModel.ProductBatch;

@Component
public class ProductBatchToProductBatchResponseConverter implements Converter<ProductBatch, ProductBatchResponse> {

    @Override
    public ProductBatchResponse convert(ProductBatch source) {
        ProductBatchResponse result = new ProductBatchResponse();
        result.setProductTypeId(source.getProductType().getId());
        result.setManufacturedAmount(source.getManufacturedAmount());
        result.setSoldAmount(source.getSoldAmount());
        return result;
    }
}