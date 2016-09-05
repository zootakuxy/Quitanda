package com.st.ggviario.client.view.adapters.dataset;

import com.st.ggviario.client.R;
import com.st.ggviario.client.model.Client;
import com.st.ggviario.client.references.RColors;

/**
 * Created by Daniel Costa at 8/28/16.
 * Using user computer xdata
 */
public class ClientDataSet extends SelectableDataSet
{
    public static final int VIEW_ID = R.layout.item_client;
    private final Client client;

    public ClientDataSet(Client client)
    {
        super(VIEW_ID);
        this.client = client;
        super.unSelectedCod(this.client.getName().substring(0, 1));
        int backgroundOvalShap = RColors.switchBackgroundOvalShap(this.client.getName().substring(0, 1).charAt(0),
                R.drawable.shap_oval_primary,
                R.drawable.shap_oval_teal,
                R.drawable.shap_oval_green
        );
        super.unSelectedBackground(backgroundOvalShap);

    }

    public Client getClient() {
        return client;
    }
}
