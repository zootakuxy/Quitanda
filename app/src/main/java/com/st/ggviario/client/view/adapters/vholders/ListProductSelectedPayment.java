package com.st.ggviario.client.view.adapters.vholders;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.dbutil.android.adapter.SupportRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.view.adapters.ListItemView;

import java.util.List;

/**
 * Created by Daniel Costa at 8/27/16.
 * Using user computer xdata
 */
public class ListProductSelectedPayment extends BaseRecyclerAdapter.ItemViewHolder implements SupportRecyclerAdapter.OnCreateViewHolder
{

    private final RecyclerView recyclerViewProduct;
    private SupportRecyclerAdapter productSelectedAdapters;

    public ListProductSelectedPayment(View itemView)
    {
        super(itemView);
        this.recyclerViewProduct = (RecyclerView) this.itemView.findViewById(R.id.rv_selected_products);
    }

    @Override
    public BaseRecyclerAdapter.ItemViewHolder onCreateViewHolder(View view, int viewType, int onRecyclerViewId)
    {
        switch (viewType)
        {
            case R.layout.item_product_selected:
                ListItemView.ItemViewHolder viewHolder = new ListItemView.ItemViewHolder(view, R.id.tv_car_item_name);
                viewHolder.setTextSecond1dId(R.id.tv_car_item_quantity);
                viewHolder.setTextSecond2Id(R.id.tv_car_item_amount);
                view.findViewById(R.id.bt_remove).setVisibility(View.INVISIBLE);
                return viewHolder;
        }
        return null;
    }

    @Override
    public boolean bind(BaseRecyclerAdapter.ItemDataSet dataSet, int position)
    {
        super.bind(dataSet, position);

        if(productSelectedAdapters == null)
        {
            this.productSelectedAdapters = new SupportRecyclerAdapter(getContext());
            List l = productSelectedAdapters.getListDataSet();

            ListItemView.TextTreeLineDataSet selected;

            l.add(selected = new ListItemView.TextTreeLineDataSet(R.layout.item_product_selected));
            selected.setTextPrimary("Produto 2");
            selected.setTextSecond1("30 Kg.");
            selected.setTextSecond2("1 788 000,00");

            l.add(selected = new ListItemView.TextTreeLineDataSet(R.layout.item_product_selected));
            selected.setTextPrimary("Produto 3");
            selected.setTextSecond1("73 Kg.");
            selected.setTextSecond2("1 893 000,00");

            l.add(selected = new ListItemView.TextTreeLineDataSet(R.layout.item_product_selected));
            selected.setTextPrimary("Produto 18");
            selected.setTextSecond1("672 Kg.");
            selected.setTextSecond2("1 892 000,00");


            StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
            this.recyclerViewProduct.setLayoutManager(llm);
            this.recyclerViewProduct.setAdapter(this.productSelectedAdapters);

            productSelectedAdapters.useTypeViewAsReferenceLayout(true);
            productSelectedAdapters.setOnCreateViewHolder(this);
        }

        return true;
    }
}
