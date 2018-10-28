package com.dropsnorz.owlplug.store.model.json;

import java.util.ArrayList;

import com.dropsnorz.owlplug.core.model.PluginStage;
import com.dropsnorz.owlplug.core.model.PluginType;
import com.dropsnorz.owlplug.core.utils.UrlUtils;
import com.dropsnorz.owlplug.store.model.ProductPlatform;
import com.dropsnorz.owlplug.store.model.Store;
import com.dropsnorz.owlplug.store.model.StoreProduct;

public class StoreModelAdapter {


	/**
	 * Crestes a {@link Store} entity from a {@link StoreJsonMapper}.
	 * @param storeJsonMapper pluginStore json mapper
	 * @return pluginStoreEntity
	 */
	public static Store jsonMapperToEntity(StoreJsonMapper storeJsonMapper) {

		Store store = new Store();
		store.setName(storeJsonMapper.getName());
		store.setUrl(storeJsonMapper.getUrl());
		return store;
	}


	/**
	 * Creates a {@link StoreProduct} entity from a {@link ProductJsonMapper}.
	 * @param productJsonMapper product json mapper
	 * @return product entity
	 */
	public static StoreProduct jsonMapperToEntity(ProductJsonMapper productJsonMapper) {

		StoreProduct product = new StoreProduct();
		product.setName(productJsonMapper.getName());
		product.setPageUrl(productJsonMapper.getPageUrl());
		product.setDownloadUrl(UrlUtils.fixSpaces(productJsonMapper.getDownloadUrl()));
		product.setIconUrl(UrlUtils.fixSpaces(productJsonMapper.getIconUrl()));
		product.setCreator(productJsonMapper.getCreator());
		product.setDescription(productJsonMapper.getDescription());
		
		
		if (productJsonMapper.getType() != null) {
			product.setType(PluginType.fromString(productJsonMapper.getType()));
		}
		
		if (productJsonMapper.getStage() != null) {
			product.setStage(PluginStage.fromString(productJsonMapper.getStage()));
		}

		ArrayList<ProductPlatform> platforms = new ArrayList<>();
		if (productJsonMapper.getPlatforms() != null) {
			for (String platformTag : productJsonMapper.getPlatforms()) {
				platforms.add(new ProductPlatform(platformTag, product));
			}
		}

		product.setPlatforms(platforms);
		return product;
	}

}
