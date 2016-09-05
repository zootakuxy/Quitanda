package com.st.ggviario.client.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.fcannizzaro.materialstepper.AbstractStep;
import com.st.dbutil.android.adapter.BaseRecyclerAdapter;
import com.st.ggviario.client.R;
import com.st.ggviario.client.dao.DaoProduct;
import com.st.ggviario.client.model.Car;
import com.st.ggviario.client.model.ItemSell;
import com.st.ggviario.client.model.Product;
import com.st.ggviario.client.model.builders.ItemSellBuilder;
import com.st.ggviario.client.model.builders.MeasureBuilder;
import com.st.ggviario.client.model.rules.CarAction;
import com.st.ggviario.client.references.RColors;
import com.st.ggviario.client.references.RMap;
import com.st.ggviario.client.view.activitys.CalculatorActivity;
import com.st.ggviario.client.view.adapters.vholders.SupportAdapter;
import com.st.ggviario.client.view.adapters.vfactory.ProductViewHolderFactory;
import com.st.ggviario.client.view.adapters.vfactory.ViewHolderFactory;
import com.st.ggviario.client.view.adapters.dataset.CarDataSet;
import com.st.ggviario.client.view.adapters.dataset.ProductDataSet;
import com.st.ggviario.client.view.adapters.vholders.CarViewHolder;
import com.st.ggviario.client.view.callbaks.OnStartActivityItemView;

import java.util.ArrayList;
import java.util.List;

public class SellCarStep extends AbstractStep implements OnStartActivityItemView {
	public static final String PRODUCT = "PRODUCT";
	private View rootView;
	private SupportAdapter supportAdapter;
	private RecyclerView recyclerView;
	private DaoProduct daoProduct;
	private List list;
	private Car car;
	CarViewHolder carViewHolder;
	private CarDataSet carDataSet;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		this.supportAdapter = new SupportAdapter(this.getContext());
		this.car = new Car();


		this.supportAdapter.addDataSet(this.carDataSet = new CarDataSet(this.car));
		this.supportAdapter.addViewHolderFactory(new ProductViewHolderFactory().onStartActivityItemView(this));
		this.supportAdapter.addViewHolderFactory(new ViewHolderFactory() {
			@NonNull
			@Override
			public View factoryView(ViewGroup viewGroup, LayoutInflater inflater) {
				return inflater.inflate(getViewType(), viewGroup, false);
			}

			@NonNull
			@Override
			public BaseRecyclerAdapter.ItemViewHolder factoryViewHolder(View view) {
				Log.i("DBA:APP.TEST", "Creating new view holder of car");
				carViewHolder = new CarViewHolder(view);
				return carViewHolder;
			}

			@Override
			public int getViewType() {
				return R.layout.item_group_car;
			}
		});

		this.daoProduct = new DaoProduct( this.getContext() );
		ArrayList<Product> listProduct = daoProduct.loadProducts(null);
		this.list = supportAdapter.getListDataSet();
		int colorId;

		for(Product product: listProduct) {
			colorId = RColors.switchColor(product.getName().charAt(0), 500);
			ProductDataSet product1 = new ProductDataSet(colorId, product);
			this.list.add(product1);
		}
	}

	@Override
	public String name() {
		return "Carinho";
	}

	@Override
	public boolean nextIf() {
		return this.car != null && !this.car.isEmpty();
	}

	@Override
	public String error() {
		return "Nenhum produto foi seleciondo";
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		this.rootView = inflater.inflate(R.layout.layout_sell, container, false);
		this.recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

//        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
//        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false);
		final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
		layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);

		this.recyclerView.setHasFixedSize(true);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(layoutManager);
		recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy)
			{
				super.onScrolled(recyclerView, dx, dy);
				int lastCompletPosition = 0;
				int max = -1;
				int row [] = layoutManager.findLastCompletelyVisibleItemPositions(null);
				for (int i: row)
					if(i>max) max = i;

//                lastCompletPosition = layoutManager.findLastCompletelyVisibleItemPosition() ;
//                lastCompletPosition = max;

				if(lastCompletPosition +1 == supportAdapter.getItemCount()){

				}
			}
		});

		this.recyclerView.setAdapter(this.supportAdapter);
		return  this.rootView;
	}

	@Override
	public void startActivity(Bundle bundle, BaseRecyclerAdapter.ItemDataSet dataSet) {
		if(dataSet instanceof ProductDataSet)
		{
			ItemSell itemSell = car.getItem(((ProductDataSet) dataSet).getProduct());

			boolean hasInCar = (itemSell != null);
			if(hasInCar) {
				bundle.putDouble(RMap.ITEM_SELL_QUANTITY, itemSell.getRequestQuantity());

				MeasureBuilder measureBuilder = new MeasureBuilder();
				bundle.putString(RMap.ITEM_SELL_MEASURE, measureBuilder.toXml(itemSell.getMeasure()));
			}

			bundle.putBoolean(RMap.HAS_IN_CAR, hasInCar);
			Intent intent = new Intent(getContext(), CalculatorActivity.class);
			intent.putExtras(bundle);
			this.startActivityForResult(intent, 10);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ItemSellBuilder sellBuilder = new ItemSellBuilder();

		Bundle bundle = data.getExtras();
		String xml = bundle.getString(RMap.ITEM_SELL);
		if(carViewHolder != null)
		{
			ItemSell itemSell = sellBuilder.buildFromXML(xml);
			CarAction carAction = (CarAction) bundle.getSerializable(RMap.CAR_ACTION);
			this.carDataSet.setLastAction(carAction, itemSell);
			this.supportAdapter.notifyItemChanged(0);
		}
	}
}


