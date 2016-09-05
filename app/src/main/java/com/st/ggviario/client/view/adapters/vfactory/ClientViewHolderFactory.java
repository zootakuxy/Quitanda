package com.st.ggviario.client.view.adapters.vfactory;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.util.animator.SelectableAnimatorManager;
import com.st.ggviario.client.util.animator.SelectableTextViewAnimatorManager;
import com.st.ggviario.client.view.adapters.dataset.ClientDataSet;
import com.st.ggviario.client.view.adapters.vholders.ClientViewHolder;

/**
 * Created by Daniel Costa at 9/3/16.
 * Using user computer xdata
 */
public class ClientViewHolderFactory implements ViewHolderFactory
{
    private SelectableTextViewAnimatorManager animatorManager;

    public ClientViewHolderFactory () {
        this.animatorManager = new SelectableTextViewAnimatorManager();
        this.animatorManager.selectionMode(SelectableAnimatorManager.SelectionMode.ONE_SELECTION);
    }

    @NonNull
    @Override
    public View factoryView(ViewGroup viewGroup, LayoutInflater inflater) {
        return inflater.inflate(getViewType(), viewGroup, false);
    }

    @NonNull
    @Override
    public BaseRecyclerAdapter.ItemViewHolder factoryViewHolder(View view) {
        ClientViewHolder clientHolder = new ClientViewHolder(view);
        clientHolder.setSelectableAnimator(this.animatorManager);
        return clientHolder;
    }

    public SelectableTextViewAnimatorManager getAnimatorManager() {
        return animatorManager;
    }

    @Override
    public int getViewType() {
        return ClientDataSet.VIEW_ID;
    }
}
