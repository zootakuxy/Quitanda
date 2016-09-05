package com.st.ggviario.client.view.adapters.dataset;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.util.BaseCharacter;

/**
 * Created by Daniel Costa at 8/29/16.
 * Using user computer xdata
 */
public class ProductDataSet extends BaseCharacter implements BaseRecyclerAdapter.ItemDataSet
{
    private int idColor;
    private Product product;
    public boolean efeito;

    public ProductDataSet(int idColor, Product product)
    {
        this.idColor = idColor;
        this.product = product;
        this.efeito = false;
    }

    @Override
    public String toString()
    {
        return product.toString();
    }

    @Override
    public int getTypeView() {
        return R.layout.item_product;
    }

    public Product getProduct() {
        return product;
    }

    public int getIdColor() {
        return idColor;
    }
}