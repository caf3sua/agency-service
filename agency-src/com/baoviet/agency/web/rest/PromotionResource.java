package com.baoviet.agency.web.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baoviet.agency.domain.ProductGenInfo;
import com.baoviet.agency.domain.Promotion;
import com.baoviet.agency.dto.PromoInfoContent;
import com.baoviet.agency.dto.PromoInfoDTO;
import com.baoviet.agency.repository.ProductGenInfoRepository;
import com.baoviet.agency.repository.PromotionRepository;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.codahale.metrics.annotation.Timed;

/**
 * REST controller for promotion resource.
 * 
 * http://www.baeldung.com/spring-security-create-new-custom-security-expression
 */
@RestController
@RequestMapping(AppConstants.API_PATH_BAOVIET_AGENCY_PREFIX + "promotion")
public class PromotionResource {

	private final Logger log = LoggerFactory.getLogger(PromotionResource.class);

	@Autowired
	private PromotionRepository promotionRepository;

	@Autowired
	private ProductGenInfoRepository productGenInfoRepository;

	// Hàm lấy nội dung các tin khuyến mại.
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PROMOTION_VIEW')")
	@GetMapping("/get-all")
	@Timed
	public ResponseEntity<List<PromoInfoContent>> getAll() throws URISyntaxException {
		log.debug("REST request to getPromosAll");

		List<Promotion> lstPromotion = promotionRepository.findByActive("1");

		List<PromoInfoContent> lstPromoInfo = new ArrayList<>();
		if (lstPromotion.size() > 0) {
			for (Promotion item : lstPromotion) {
				PromoInfoContent promo = new PromoInfoContent();
				
				ProductGenInfo obj = new ProductGenInfo();
				obj = productGenInfoRepository.findOne(item.getNewId());
				
				promo.setNewID(item.getNewId());
				promo.setTitle(item.getTitle());
				promo.setUrl(item.getImage());
				promo.setFromDate(DateUtils.date2Str(item.getFromDate()));
				promo.setToDate(DateUtils.date2Str(item.getToDate()));
				
				promo.setContent(obj.getContent());
				lstPromoInfo.add(promo);
			}
		}
		log.debug("REST request to getPromosAll result lstPromoInfo {} " + lstPromoInfo);
		// Return data
		return new ResponseEntity<>(lstPromoInfo, HttpStatus.OK);
	}
	
	// Hàm lấy nội dung các tin khuyến mại.
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PROMOTION_VIEW')")
	@GetMapping("/getPromos")
	@Timed
	public ResponseEntity<List<PromoInfoDTO>> getPromos() throws URISyntaxException {
		log.debug("REST request to getPromos");

		List<Promotion> lstPromotion = promotionRepository.findByActive("1");

		List<PromoInfoDTO> lstPromoInfo = new ArrayList<>();
		if (lstPromotion.size() > 0) {
			for (Promotion item : lstPromotion) {
				PromoInfoDTO promo = new PromoInfoDTO();
				promo.setNewID(item.getNewId());
				promo.setTitle(item.getTitle());
				promo.setUrl(item.getImage());
				promo.setFromDate(DateUtils.date2Str(item.getFromDate()));
				promo.setToDate(DateUtils.date2Str(item.getToDate()));
				lstPromoInfo.add(promo);
			}
		}
		log.debug("REST request to getPromos result lstPromoInfo {} " + lstPromoInfo);
		// Return data
		return new ResponseEntity<>(lstPromoInfo, HttpStatus.OK);
	}

	// Hàm lấy nội dung chi tiết tin khuyến mại
	@PreAuthorize("hasRole('ADMIN') or hasAuthority('PERM_PROMOTION_VIEW')")
	@GetMapping("/getPromosDetail")
	@Timed
	public ResponseEntity<ProductGenInfo> getPromosDetail(String newsId) throws URISyntaxException {
		log.debug("REST request to getPromosDetail");

		List<Promotion> lstPromotion = promotionRepository.findByActive("1");

		ProductGenInfo obj = new ProductGenInfo();

		if (lstPromotion != null && lstPromotion.size() > 0) {
			for (Promotion item : lstPromotion) {
				if (item.getNewId().equals(newsId)) {
					String description = DateUtils.date2Str(item.getFromDate()) + " - "
							+ DateUtils.date2Str(item.getToDate());
					obj.setDescription(description);
					break;
				}
			}

			if (!StringUtils.isEmpty(newsId)) {
				obj = productGenInfoRepository.findOne(newsId);
			}
		}

		log.debug("REST request to getPromosDetail result ProductGenInfo {} " + obj);
		// Return data
		return new ResponseEntity<>(obj, HttpStatus.OK);
	}

	// Hàm ảnh banner sản phẩm là trường Description. pProductGenID truyền vào là
	// 35. Json kết quả là Object PRODUCT_GEN_INFOCollection
	@GetMapping("/getSliderImg")
	@Timed
	public ResponseEntity<List<ProductGenInfo>> getSliderImg(String productGenID) throws URISyntaxException {
		log.debug("REST request to getSliderImg");

		List<ProductGenInfo> lstProductGenInfo = new ArrayList<>();
		if (!StringUtils.isEmpty(productGenID)) {
			List<ProductGenInfo> lstNews = productGenInfoRepository.findAll();
			for (ProductGenInfo item : lstNews) {
				if (item.getFkProductGenId().equals(productGenID) && item.getStatus().equals("1")) {
					lstProductGenInfo.add(item);
				}
			}
		}

		log.debug("REST request to getSliderImg result lstProductGenInfo {} " + lstProductGenInfo);
		// Return data
		return new ResponseEntity<>(lstProductGenInfo, HttpStatus.OK);
	}

	// Hàm lấy nội dung chi tiết tin khuyến mại mới nhất để hiện thị lên topage
	@GetMapping("/getTopPromos")
	@Timed
	public ResponseEntity<ProductGenInfo> getTopPromos() throws URISyntaxException {
		log.debug("REST request to getTopPromos");

		List<Promotion> lstPromotion = promotionRepository.findByActive("1");

		ProductGenInfo productGenInfo = new ProductGenInfo();
		String id = "";
		Date maxDate = new Date();
		maxDate = DateUtils.addDay(maxDate, -3600);
		if (lstPromotion.size() > 0) {
			for (Promotion item : lstPromotion) {
				if (item.getFromDate().compareTo(maxDate) > 0) {
					id = item.getNewId();
					maxDate = item.getFromDate();
				}
			}

			if (!StringUtils.isEmpty(id)) {
				productGenInfo = productGenInfoRepository.findOne(id);
			}
		}
		log.debug("REST request to getTopPromos result ProductGenInfo {} " + productGenInfo);
		// Return data
		return new ResponseEntity<>(productGenInfo, HttpStatus.OK);
	}
}
