package com.baoviet.agency.adayroi.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	private String offerId;

	private String offerName;

	private String warehouseId;

	private String productType;

	private String unitName;

	private String productImageThumbnail;

	private String brandId;
	
	private String categoryId;
	
	private String articleNo;
	
	private String sapUoM;
	
	private Double quantityPerUnit;

	private String barcode;
	
	private List<String> RelatedBarcode;

	private String orderProductItemStatus;
	
	private String merchantSKU;
	
	private Integer orderQuantity;
	
	private Integer quantityConfirm;
	
	private Integer quantityConfirmPrevious;
	
	private String sellPrice;
	
	private String originalPrice;
	
	private Double totalPrice;
	
	private Double vat;
	
	private Double vatEx;
	
	private String promotions;
	
	private ObjMcVariantInfo variantInfos;
	
	private Boolean AsChangesFromCs;
	
	private String ActualMerchantConfirmationtime;
	
	private String ExpectedMerchantConfirmationtime;
	
	private String ComboId;
	
	private String ComboName;
	
	private String CancelReason;
	
	private String MerchantNote;
	
	private String brandName;
	
	private String categoryName;
	
	private String sapPrice;
	
	private String sapTotalPrice;
}
