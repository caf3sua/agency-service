package com.baoviet.agency.service.impl;

import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baoviet.agency.bean.InvoiceInfoDTO;
import com.baoviet.agency.bean.ReceiverUserInfoDTO;
import com.baoviet.agency.domain.SppCar;
import com.baoviet.agency.domain.SppPrices;
import com.baoviet.agency.dto.CodeManagementDTO;
import com.baoviet.agency.dto.ContactDTO;
import com.baoviet.agency.dto.PurposeOfUsageDTO;
import com.baoviet.agency.dto.TmpMomoCarDTO;
import com.baoviet.agency.dto.momo.component.ItemOptionComponent;
import com.baoviet.agency.dto.momo.component.MomoComponent;
import com.baoviet.agency.dto.momo.component.SelectorComponent;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.SppCarRepository;
import com.baoviet.agency.repository.SppPricesRepository;
import com.baoviet.agency.service.CodeManagementService;
import com.baoviet.agency.service.ContactService;
import com.baoviet.agency.service.MomoCarService;
import com.baoviet.agency.service.ProductCARService;
import com.baoviet.agency.service.PurposeOfUsageService;
import com.baoviet.agency.service.TmpMomoCarService;
import com.baoviet.agency.utils.ComponentUtils;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.ContactCreateVM;
import com.baoviet.agency.web.rest.vm.PremiumCARVM;
import com.baoviet.agency.web.rest.vm.ProductCarVM;


/**
 * Service Implementation for managing GnocCr.
 * @author Nam, Nguyen Hoai
 */
@Service
@Transactional
public class MomoCarServiceImpl implements MomoCarService {

    private final Logger log = LoggerFactory.getLogger(AgencyServiceImpl.class);

    @Autowired
    private TmpMomoCarService tmpMomoCarService;
    
    @Autowired
    private ProductCARService productCARService;
    
    @Autowired
	private CodeManagementService codeManagementService;
    
    @Autowired
    private ContactService contactService;
    
    @Autowired
    private SppCarRepository sppCarRepository;
    
    @Autowired
    private SppPricesRepository sppPricesRepository;
    
    @Autowired
	private PurposeOfUsageService purposeOfUsageService;
    
    private static final String TOOLTIP_VIEW_1 = "Bồi thường thiệt hại về thân thể, tính mạng và tài sản đối với: \n "
    		+ "\t - Bên thứ ba do xe ô tô gây ra. \n "
    		+ "\t - Khách hàng theo hợp đồng vận chuyển do xe khách gây ra.";
    private static final String TOOLTIP_VIEW_2 = "Bồi thường thiệt hại thân thể đối với lái xe và những người được chở trên xe khi xe đang tham gia giao thông";
    private static final String TOOLTIP_VIEW_3 = "Bồi thường thiệt hại vật chất xe do : \n"
    		+ "\t - Tai nạn bất ngờ, ngoài sự kiểm soát của chủ xe trong những trường hợp: đâm va, lật, đổ; rơi chìm; hỏa hoạn, cháy, nổ; bị các vật thể khác rơi, va chạm vào. \n"
    		+ "\t - Các tai họa bất khả kháng do thiên nhiên: bão, lũ, sụt lở, sét đánh, động đất, mưa đá, sóng thần. \n"
    		+ "\t - Mất cắp, mất cướp toàn bộ xe (bồi thường toàn bộ). \n"
    		+ "Ngoài ra Bảo Việt còn thanh toán các chi phí cần thiết, hợp lý phát sinh nhằm ngăn ngừa hạn chế tổn thất tăng thêm và đưa xe thiệt hại đến nơi sửa chữa, giám định.";
    
    private static final String TOOLTIP_DKBS_1 = "Bồi thường đầy đủ số tiền không khấu trừ 500.000đ cho tất cả các vụ thiệt hại thuộc trách nhiệm bảo hiểm.";
    private static final String TOOLTIP_DKBS_2 = "Trong quá trình sửa chữa, khắc phục tổn thất, nếu phải thay thế bộ phận mới thì Bảo Việt sẽ xác định chí phí thay mới vật tư, phụ tùng thuộc trách nhiệm bảo hiểm bằng chi phí thực tế thay thế mới vật tư, phụ tùng đó mà không áp dụng trừ khấu hao vật tư, phụ tùng thay thế mới.";
    private static final String TOOLTIP_DKBS_3 = "Bảo Việt nhận BH và chịu trách nhiệm bồi thường cho Chủ xe về những tổn thất bộ phận do bị mất cắp, mất cướp. Giới hạn số lần mất cắp, mất cướp không vượt quá: \n"
    		+ "\t - 02 lần đối với các Hợp đồng bảo hiểm có thời hạn từ 12 tháng đến 18 tháng (Không nhận bảo hiểm mất cắp bộ phận cho các Hợp đồng bảo hiểm dưới 12 tháng). \n"
    		+ "\t - 03 lần đối với các Hợp đồng bảo hiểm có thời hạn trên 18 tháng. \n"
    		+ "Mức khấu trừ áp dụng riêng cho điều khoản mất cắp bộ phận là 20% số tiền bồi thường và không thấp hơn 2.000.000 ðồng. Không áp dụng thêm mức khấu trừ nào khác đã quy định trong Hợp đồng bảo hiểm (nếu có).";
    private static final String TOOLTIP_DKBS_4 = "Mở rộng nhận bảo hiểm trong trường hợp xe ô tô bị thiệt hại động cơ hoặc hư hỏng về điện do lái xe điều khiển xe đi vào vùng ngập nước sẽ được Bảo Việt bồi thường, nếu xe có tham gia bảo hiểm vật chất xe. \n"
    		+ "\t - Mức khấu trừ/vụ áp dụng riêng đối với trường hợp tổn thất động cõ là 20% số tiền bồi thường không thấp hơn 3.000.000 đồng. Không áp dụng thêm mức khấu trừ nào khác đã quy định trong Hợp đồng bảo hiểm (nếu có).";
    private static final String TOOLTIP_DKBS_5 = "Trên cơ sở yêu cầu và chấp nhận nộp phí của Chủ xe/Người yêu cầu bảo hiểm, Bảo Việt đồng ý để Chủ xe được quyền chỉ định sửa chữa xe bị thiệt hại vật chất tại Xưởng (Garage) đã thoả thuận trước ở thời điểm ký kết Hợp đồng bảo hiểm hoặc chỉ định ở thời điểm xảy ra tổn thất.";
    
    private static final NumberFormat formatter = new DecimalFormat("#,000");
    
	@Override
	public List<MomoComponent> buildFormDataStep1() throws URISyntaxException, AgencyBusinessException{
		List<MomoComponent> form = new ArrayList<>();
		
		ComponentUtils.createStepIndicator(form, "step1", "0");
		
		// Label
		ComponentUtils.createLabel(form, "id", "THÔNG TIN YÊU CẦU BẢO HIỂM", "1");
		
		// Radio buttom
		List<ItemOptionComponent> lstStatus = new ArrayList<>();
		ItemOptionComponent st1 = new ItemOptionComponent("Tham gia mới", "0");
		ItemOptionComponent st2 = new ItemOptionComponent("Tái tục", "1");
		lstStatus.add(st1);
		lstStatus.add(st2);
		ComponentUtils.createRadioInput(form, "status", "Đối tượng được bảo hiểm", lstStatus);
		
		// Bảo hiểm trách nhiệm dân sự bắt buộc
		List<ItemOptionComponent> lstTndsSoCho = new ArrayList<>();
		ItemOptionComponent socho1 = new ItemOptionComponent("Loại xe dưới 6 chỗ ngồi", "1");
		ItemOptionComponent socho2 = new ItemOptionComponent("Loại xe từ 6 đến 11 chỗ ngồi", "2");
		ItemOptionComponent socho3 = new ItemOptionComponent("Loại xe từ 12 đến 24 chỗ ngồi", "3");
		ItemOptionComponent socho4 = new ItemOptionComponent("Loại xe trên 24 chỗ ngồi", "4");
		ItemOptionComponent socho5 = new ItemOptionComponent("Xe vừa chở người vừa chở hàng(Pickup, Minivan)", "5");
		lstTndsSoCho.add(socho1);
		lstTndsSoCho.add(socho2);
		lstTndsSoCho.add(socho3);
		lstTndsSoCho.add(socho4);
		lstTndsSoCho.add(socho5);
		ComponentUtils.createComboboxInput(form, "tnds_Socho", "Chọn số chỗ/trọng tải xe", lstTndsSoCho);
		
		// CheckList: Loại bảo hiểm
		List<ItemOptionComponent> baohiemList = new ArrayList<>();
		ItemOptionComponent bh1 = new ItemOptionComponent("Bảo hiểm trách nhiệm dân sự bắt buộc", "tndsbbCheck", TOOLTIP_VIEW_1);
		ItemOptionComponent bh2 = new ItemOptionComponent("Bảo hiểm trách nhiệm dân sự tự nguyện", "tndstnCheck", TOOLTIP_VIEW_1);
		ItemOptionComponent bh3 = new ItemOptionComponent("Bảo hiểm tai nạn người ngồi trên xe", "nntxCheck", TOOLTIP_VIEW_2);
		ItemOptionComponent bh4 = new ItemOptionComponent("Bảo hiểm vật chất xe", "vcxCheck", TOOLTIP_VIEW_3);
		baohiemList.add(bh1);
		baohiemList.add(bh2);
		baohiemList.add(bh3);
		baohiemList.add(bh4);
		List<Object> list = new ArrayList<Object>();
		list.add("tndsbbCheck");
		list.add("tndstnCheck");
		list.add("vcxCheck");
		ComponentUtils.createCheckListInput(form, "listBaoHiem", "Phạm vi bảo hiểm", baohiemList, "1", list);
		
		// button
		List<String> bindId = new ArrayList<>();
		bindId.add("listBaoHiem");
		bindId.add("tnds_Socho");
		ComponentUtils.createButton(form, "btnContinue", "Tiếp tục", "Tiếp tục", bindId);
				
		return form;
	}
	
	@Override
	public List<MomoComponent> buildFormDataStep2(String requestId, List<MomoComponent> formInput) throws URISyntaxException, AgencyBusinessException{
		List<MomoComponent> form = new ArrayList<>();
		
		ComponentUtils.createStepIndicator(form, "step2", "0");
		
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		if (dto == null) {
			throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
		}
		List<String> bindId = new ArrayList<>();
		
		String status = (String) ComponentUtils.getComponentValue(formInput, "status");
		if(status.equals("1")) {
			// textbox
			ComponentUtils.createTextInput(form, "oldGycbhNumber", "Nhập số hợp đồng bảo hiểm cũ", "Tái tục", "/^(.{3,100})$/ig", null);
			bindId.add("oldGycbhNumber");
		}
		
		// Bảo hiểm trách nhiệm dân sự tự nguyện
		if (dto.getTndstnCheck() != null && dto.getTndstnCheck() > 0) {
			// Label bảo hiểm
			ComponentUtils.createLabel(form, "idBHTN", "BẢO HIỂM TRÁCH NHIỆM DÂN SỰ TỰ NGUYỆN", "1");
			
			// số tiền
			List<ItemOptionComponent> lstTndstnSoTien = new ArrayList<>();
			ItemOptionComponent sotien1 = new ItemOptionComponent("50trđ/người/vụ về người - 50trđ/vụ về tài sản", "50000000");
			ItemOptionComponent sotien2 = new ItemOptionComponent("100trđ/người/vụ về người - 100trđ/vụ về tài sản", "100000000");
			ItemOptionComponent sotien3 = new ItemOptionComponent("150trđ/người/vụ về người - 150trđ/vụ về tài sản", "150000000");
			lstTndstnSoTien.add(sotien1);
			lstTndstnSoTien.add(sotien2);
			lstTndstnSoTien.add(sotien3);
			ComponentUtils.createComboboxInput(form, "tndstnSotien", "Chọn mức trách nhiệm", lstTndstnSoTien);
			bindId.add("tndstnSotien");
		}
		
		// Bảo hiểm tai nạn người ngồi trên xe
		if (dto.getNntxCheck() != null && dto.getNntxCheck() > 0) {
			// Label bảo hiểm
			ComponentUtils.createLabel(form, "idBHTNa", "BẢO HIỂM TAI NẠN NGƯỜI NGỒI TRÊN XE", "1");
			
			// số người
			ComponentUtils.createTextInput(form, "passengersAccidentNumber", "Số người tham gia", "", "/^([0-9a-z]{1,20})$/ig", "1");
			bindId.add("passengersAccidentNumber");
			// số tiền
			List<ItemOptionComponent> lstPhiNNTX = new ArrayList<>();
			ItemOptionComponent sotienNNTX1 = new ItemOptionComponent("10 triệu đồng/người/vụ", "10000000");
			ItemOptionComponent sotienNNTX2 = new ItemOptionComponent("20 triệu đồng/người/vụ", "20000000");
			ItemOptionComponent sotienNNTX3 = new ItemOptionComponent("30 triệu đồng/người/vụ", "30000000");
			ItemOptionComponent sotienNNTX4 = new ItemOptionComponent("40 triệu đồng/người/vụ", "40000000");
			ItemOptionComponent sotienNNTX5 = new ItemOptionComponent("50 triệu đồng/người/vụ", "50000000");
			ItemOptionComponent sotienNNTX100 = new ItemOptionComponent("100 triệu đồng/người/vụ", "100000000");
			ItemOptionComponent sotienNNTX150 = new ItemOptionComponent("150 triệu đồng/người/vụ", "150000000");
			ItemOptionComponent sotienNNTX200 = new ItemOptionComponent("200 triệu đồng/người/vụ", "200000000.");
			lstPhiNNTX.add(sotienNNTX1);
			lstPhiNNTX.add(sotienNNTX2);
			lstPhiNNTX.add(sotienNNTX3);
			lstPhiNNTX.add(sotienNNTX4);
			lstPhiNNTX.add(sotienNNTX5);
			lstPhiNNTX.add(sotienNNTX100);
			lstPhiNNTX.add(sotienNNTX150);
			lstPhiNNTX.add(sotienNNTX200);
			ComponentUtils.createComboboxInput(form, "passengersAccidentSi", "Chọn mức trách nhiệm", lstPhiNNTX);
			bindId.add("passengersAccidentSi");
		}
		
		// Bảo hiểm vật chất xe
		if(dto.getVcxCheck() != null && dto.getVcxCheck() > 0) {
			// Label bảo hiểm
			ComponentUtils.createLabel(form, "idBHVC", "BẢO HIỂM VẬT CHẤT XE", "1");
			
			// get all database
			List<SppCar> lstSppCar = sppCarRepository.findAll();
			List<SppPrices> lstSppPrices = sppPricesRepository.findAll();
			List<String> lstHangxeData = getHangxe(lstSppCar);
			
			// dong xe
			List<SelectorComponent> lstHangxe = new ArrayList<>();
			for (String hangxeData : lstHangxeData) {
					SelectorComponent hangxe = new SelectorComponent();
					List<SelectorComponent> lstDongxe = new ArrayList<>();
					List<SppCar> lstDongxeData = getDongxeByHangxe(lstSppCar, hangxeData);
					// Get dong xe
					for (SppCar dongxeData : lstDongxeData) {
						// Nam san xuat
						List<SelectorComponent> lstNamsanxuat = new ArrayList<>();
						List<SppPrices> lstNamsxData = getNamsxByDongxe(lstSppPrices, dongxeData.getCarId());
						for (SppPrices namsxData : lstNamsxData) {
							List<SppPrices> lstGiaxeData = getGiaxeByNamsx(lstSppPrices, dongxeData.getCarId(), String.valueOf(namsxData.getPriceYear()));
							List<SelectorComponent> lstGiaxe = new ArrayList<>();
							SelectorComponent giaxe = new SelectorComponent();
							giaxe.setText(String.valueOf(formatter.format(lstGiaxeData.get(0).getPriceVnd().longValue())));
							giaxe.setValue(namsxData.getPriceId());
							lstGiaxe.add(giaxe);
							
							SelectorComponent namsx = new SelectorComponent();
							namsx.setText(String.valueOf(namsxData.getPriceYear()));
							namsx.setValue(namsxData.getPriceId());
							namsx.setChildrenTitle("Giá trị xe tham gia bảo hiểm");
							namsx.setChildren(lstGiaxe);
							lstNamsanxuat.add(namsx);
						}
						
						SelectorComponent dongxe = new SelectorComponent();
						dongxe.setText(dongxeData.getCarName());
						dongxe.setValue(dongxeData.getCarId());
						dongxe.setChildrenTitle("Năm sản xuất");
						dongxe.setChildren(lstNamsanxuat);
						lstDongxe.add(dongxe);
					}
					hangxe.setText(hangxeData);
					hangxe.setValue(hangxeData);
					hangxe.setChildrenTitle("Dòng xe");
					hangxe.setChildren(lstDongxe);
					lstHangxe.add(hangxe);
			}
			
			SelectorComponent selector = new SelectorComponent();
			
			selector.setValue("-1");
			selector.setText("parent");
			selector.setChildrenTitle("Chọn hãng xe");
			selector.setChildren(lstHangxe);
			
			MomoComponent comboboxHangxe = new MomoComponent();
			comboboxHangxe.setId("hangxeId");
			comboboxHangxe.setType(MomoComponent.COMPONENT_MULTI_LIST_SELECTOR);
			comboboxHangxe.setValue("Xe");
			comboboxHangxe.setSelector(selector);
			form.add(comboboxHangxe);
			bindId.add("hangxeId");
			
			// CheckList: Điều khoản bổ sung
			List<ItemOptionComponent> dieukhoanList = new ArrayList<>();
//			ItemOptionComponent dieukhoan1 = new ItemOptionComponent("Điều khoản không áp dụng mức khấu trừ", "khauTruCheck", TOOLTIP_DKBS_1);
			ItemOptionComponent dieukhoan2 = new ItemOptionComponent("Điều khoản không khấu hao thay mới", "khaoHaoCheck", TOOLTIP_DKBS_2);
			ItemOptionComponent dieukhoan3 = new ItemOptionComponent("Điều khoản mất cắp bộ phận", "matCapCheck", TOOLTIP_DKBS_3);
			ItemOptionComponent dieukhoan4 = new ItemOptionComponent("Điều khoản ngập nước", "ngapNuocCheck", TOOLTIP_DKBS_4);
			ItemOptionComponent dieukhoan5 = new ItemOptionComponent("Điều khoản lựa chọn garage sửa chữa", "garageCheck", TOOLTIP_DKBS_5);
//			dieukhoanList.add(dieukhoan1);
			dieukhoanList.add(dieukhoan2);
			dieukhoanList.add(dieukhoan3);
			dieukhoanList.add(dieukhoan4);
			dieukhoanList.add(dieukhoan5);
			ComponentUtils.createCheckListInput(form, "listDieukhoanBS", "Điều khoản bổ sung", dieukhoanList, null, null);
			
			// Giảm giá
			//ComponentUtils.createTextInput(form, "changePremium", "Nhập mã giảm giá(nếu có)", "", "/^(.{3,100})$/ig", null);
		}
		
		List<String> bindhangxeId = new ArrayList<>();
		bindhangxeId.add("hangxeId");
		
		// button
//		if(dto.getVcxCheck() != null && dto.getVcxCheck() > 0) {
//			ComponentUtils.createButton(form, "btnContinue", "Tính phí", "Tính phí", bindhangxeId);	
//		} else {
			ComponentUtils.createButton(form, "btnContinue", "Tính phí", "Tính phí", bindId);	
//		}
		return form;
	}
	
	@Override
	public List<MomoComponent> buildFormDataStepPhi(String requestId) throws URISyntaxException, AgencyBusinessException{
		List<MomoComponent> form = new ArrayList<>();
		
		ComponentUtils.createStepIndicator(form, "step3", "0");
		
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		if (dto == null) {
			throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
		}
		
		createTableThongTinPhi(form, dto);
		
		List<String> bindhangxeId = new ArrayList<>();
		bindhangxeId.add("check");
		
		// button
		if(dto.getTotalPremium().doubleValue() > 20000000) {
			
			ComponentUtils.createLabel(form, "idSotien", "HIỆN TẠI VÍ ĐIỆN TỬ CHƯA HỖ TRỢ THANH TOÁN TRÊN 20 TRIỆU ĐỒNG MONG QUÝ KHÁCH THÔNG CẢM.", "1");
			
			ComponentUtils.createButton(form, "btnContinue", "Tiếp tục", "Tiếp tục", bindhangxeId);	
		} else {
			ComponentUtils.createButton(form, "btnContinue", "Tiếp tục", "Tiếp tục", null);	
		}
		
		return form;
	}

	@Override
	public List<MomoComponent> buildFormDataStep3(String requestId, String status) throws URISyntaxException, AgencyBusinessException{
		List<MomoComponent> form = new ArrayList<>();
		
		ComponentUtils.createStepIndicator(form, "step3", "1");
		
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		
		
		List<String> bindId = new ArrayList<>();
		
		// Label Thông tin Chủ hợp đồng
		ComponentUtils.createLabel(form, "idKH", "THÔNG TIN CHỦ HỢP ĐỒNG", "1");
		
		if (status.equals("1")) {
			if (StringUtils.isEmpty(dto.getOldGycbhNumber())) {
				// textbox
				ComponentUtils.createTextInput(form, "oldGycbhNumber", "Nhập số hợp đồng bảo hiểm cũ", "Tái tục", "/^(.{3,100})$/ig", null);
				bindId.add("oldGycbhNumber");
			}
		}
		
		// Build auto fill to get information of  contact
		ComponentUtils.createTextInputAutoFill(form, "contactName", "name", "Tên khách hàng", "", "/^(.{3,100})$/ig", null, null);
		bindId.add("contactName");
		ComponentUtils.createTextInputAutoFill(form, "contactEmail", "email", "Email", "", "/^[A-Z0-9._%+-]+@[A-Z0-9.-]+[.][A-Z]{2,6}$/ig", null, null);
		bindId.add("contactEmail");
		ComponentUtils.createTextInputAutoFill(form, "contactPhoneNumber", "phoneNumber", "Số điện thoại", "", "/^(.{3,100})$/ig", null, "1");
		bindId.add("contactPhoneNumber");
		
		// Label Thông tin chủ xe
		ComponentUtils.createLabel(form, "id", "THÔNG TIN CHỦ XE", "1");
		
		// Tên chủ xe
		ComponentUtils.createTextInputAutoFill(form, "insuredName", "name", "Tên chủ xe(theo đăng ký xe)", "", "/^(.{3,100})$/ig", null, null);
		bindId.add("insuredName");
		// Địa chỉ
		ComponentUtils.createTextInputAutoFill(form, "insuredAddress", "address", "Địa chỉ(theo đăng ký xe)", "", "/^(.{3,100})$/ig", null, null);
		bindId.add("insuredAddress");
		// Biển kiểm soát
		ComponentUtils.createTextInput(form, "registrationNumber", "Biển kiểm soát", "", "/^(.{3,100})$/ig", null);
		bindId.add("registrationNumber");
		// Số khung
		ComponentUtils.createTextInput(form, "chassisNumber", "Số khung", "", "/^(.{3,100})$/ig", null);
		bindId.add("chassisNumber");
		// Số máy
		ComponentUtils.createTextInput(form, "engineNumber", "Số máy", "", "/^(.{3,100})$/ig", null);
		bindId.add("engineNumber");
		// từ ngày
		if (dto.getVcxCheck() > 0) {
			ComponentUtils.createDateInputVatChat(form, "thoihantu", "Bảo hiểm từ ngày", "dd/MM/yyyy", "dd/MM/yyyy");
		} else {
			ComponentUtils.createDateInput(form, "thoihantu", "Bảo hiểm từ ngày", "dd/MM/yyyy", "dd/MM/yyyy");	
		}
		
		// button
		ComponentUtils.createButton(form, "btnContinue", "Tiếp tục", "Tiếp tục", bindId);
		
		return form;
	}

	@Override
	public List<MomoComponent> buildFormDataStep4(String requestId, List<MomoComponent> formInput) throws URISyntaxException, AgencyBusinessException{
		List<MomoComponent> form = new ArrayList<>();
		
		ComponentUtils.createStepIndicator(form, "step4", "2");
		
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		if (dto == null) {
			throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
		}
		List<String> bindId = new ArrayList<>();
		
		// checkbox hóa đơn GTGT
		ComponentUtils.createCheckBoxInput(form, "idCheckGTGT", "Yêu cầu hóa đơn giá trị gia tăng", "0");
		
		// Thông tin nhận đơn bảo hiểm
			// Label Thông tin nhận đơn bảo hiểm
		ComponentUtils.createLabel(form, "idReceiverUser", "THÔNG TIN NHẬN ĐƠN BẢO HIỂM", "1");
			// tên người nhận
		ComponentUtils.createTextInputAutoFill(form, "receiverName", "name", "Họ và tên", "", "/^(.{3,100})$/ig", null, null);
		bindId.add("receiverName");
		ComponentUtils.createTextInputAutoFill(form, "receiverEmail", "email", "Email", "", "/^[A-Z0-9._%+-]+@[A-Z0-9.-]+[.][A-Z]{2,6}$/ig", null, null);
		bindId.add("receiverEmail");
		ComponentUtils.createTextInputAutoFill(form, "receiverMobile", "phoneNumber", "Số điện thoại liên hệ", "", "/^(.{3,100})$/ig", null, "1");
		bindId.add("receiverMobile");
		if (!StringUtils.isEmpty(dto.getInsuredAddress())) {
			ComponentUtils.createTextInputAutoFill(form, "receiverAddress", null, "Địa chỉ nhận", "", "/^(.{3,100})$/ig", dto.getInsuredAddress(), null);
		} else {
			ComponentUtils.createTextInputAutoFill(form, "receiverAddress", "address", "Địa chỉ nhận", "", "/^(.{3,100})$/ig", null, null);	
		}
		bindId.add("receiverAddress");
		// checkbox nhận giấy chứng nhận
		if (dto.getTndsbbCheck() != null && dto.getTndsbbCheck() > 0) {
			ComponentUtils.createCheckBoxInput(form, "checkDieuKhoan", "Nhận giấy chứng nhận bảo hiểm gửi qua đường bưu điện", "1");	
		} else {
			ComponentUtils.createCheckBoxInput(form, "checkDieuKhoan", "Nhận giấy chứng nhận bảo hiểm gửi qua đường bưu điện", "0");
		}
		
		// button
		ComponentUtils.createButton(form, "btnContinue", "Tiếp tục", "Tiếp tục", bindId);
				
		return form;
	}

	@Override
	public List<MomoComponent> buildFormDataStep5(String requestId) throws AgencyBusinessException{
		List<MomoComponent> form = new ArrayList<>();
		
		ComponentUtils.createStepIndicator(form, "step5", "3");
		
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		if (dto == null) {
			throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
		}
		// Tóm tắt đơn hàng
		createTableThongTinBaoHiem(form, dto);
		
		ComponentUtils.createLabel(form, "idTHBH", "THỜI HẠN BẢO HIỂM", "1");
		
		ComponentUtils.createLabel(form, "idTungay", "Từ ngày: " + dto.getThoiHanTu() + " đến ngày " + dto.getThoiHanDen(), "2");
		
		// Thông tin nhận giấy chứng nhận
		createTableNhan(form, dto);

		// Thông tin phí bảo hiểm
		createTableThongTinPhi(form, dto);
		
		// button
		ComponentUtils.createLabel(form, "idKhoangtrang4", "", "0");
		ComponentUtils.createButton(form, "btnContinue", "Đặt mua", "Đặt mua", null);
				
		return form;
	}

	@Override
	public boolean saveFormDataStep1(String requestId, List<MomoComponent> form) {
		log.debug("Request to saveFormDataStep1, requestId{} form", requestId, form);
		
		// Save to temp table
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		if (dto == null) {
			dto = new TmpMomoCarDTO();
		}
		// Set properties of step1
		if(!StringUtils.isEmpty(requestId)) {
			dto.setRequestId(requestId);
			dto.setCreatedDate(new Date());
		}
		int tndsbbCheck = 0;
		int tndstnCheck = 0;
		int nntxCheck = 0;
		int vcxCheck = 0;
		
		if (ComponentUtils.getComponentValue(form, "listBaoHiem") != null) {
			List<String> option = (List<String>) ComponentUtils.getComponentValue(form, "listBaoHiem");
			for (String item : option) {
				if (item.equals("tndsbbCheck")) {
					tndsbbCheck = 1;
				}
				if (item.equals("tndstnCheck")) {
					tndstnCheck = 1;
				}
				if (item.equals("nntxCheck")) {
					nntxCheck = 1;
				}
				if (item.equals("vcxCheck")) {
					vcxCheck = 1;
				}
			}
		}
		
		// tnds số chỗ
		String tndsSoCho = (String) ComponentUtils.getComponentValue(form, "tnds_Socho");
		if(!StringUtils.isEmpty(tndsSoCho)) {
			dto.setTndsSocho(tndsSoCho);	
		} 	
		
		// tnds bắt buộc
		dto.setTndsbbCheck(tndsbbCheck);
		// tnds tu nguyen
		dto.setTndstnCheck(tndstnCheck);
		// nguoi ngoi tren xe
		dto.setNntxCheck(nntxCheck);
		// vat chat xe
		dto.setVcxCheck(vcxCheck);

		String status = (String) ComponentUtils.getComponentValue(form, "status");
		if(!StringUtils.isEmpty(status)) {
			dto.setCheckTaituc(status);	
		}
		
		dto.setStatus("PENDING");
		tmpMomoCarService.save(dto);
				
		return true;
	}

	@Override
	public boolean saveFormDataStep2(String requestId, List<MomoComponent> form) throws AgencyBusinessException{
		// Get value of form
		log.debug("Request to saveFormDataStep2, requestId{} form", requestId, form);
		
		// Save to temp table
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		if (dto == null) {
			throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
		}
		
		String oldGycbhNumber = (String) ComponentUtils.getComponentValue(form, "oldGycbhNumber");
		
		String tndstnSotien = (String) ComponentUtils.getComponentValue(form, "tndstnSotien");
		String passengersAccidentNumber = (String) ComponentUtils.getComponentValue(form, "passengersAccidentNumber");
		String passengersAccidentSi = (String) ComponentUtils.getComponentValue(form, "passengersAccidentSi");

		// giấy ycbhNumber
		if(!StringUtils.isEmpty(oldGycbhNumber)) {
			dto.setOldGycbhNumber(oldGycbhNumber);	
		}	
		// tnds tu nguyen
		if (tndstnSotien != null) {
			dto.setTndstnSotien(Double.parseDouble(tndstnSotien));	
		}
		
		// nguoi ngoi tren xe
		if (passengersAccidentNumber != null) {
			dto.setPassengersAccidentNumber(Double.parseDouble(passengersAccidentNumber));	
		}
		
		if (passengersAccidentSi != null) {
			dto.setPassengersAccidentSi(Double.parseDouble(passengersAccidentSi));	
		}
		
		// vat chat xe
		if (dto.getVcxCheck() > 0) {
//			String changePremium = (String) ComponentUtils.getComponentValue(form, "changePremium");
//			if(!StringUtils.isEmpty(changePremium)) {
//				dto.setChangePremium(Double.parseDouble(changePremium));
//			}
			
			String priceId = (String) ComponentUtils.getComponentValue(form, "hangxeId");
			SppPrices sppPrices = sppPricesRepository.findOne(priceId);
			if (sppPrices != null) {
				SppCar sppCar = sppCarRepository.findOne(sppPrices.getCar());
				if (sppCar != null) {
					dto.setMakeId(sppCar.getCarManufacturer());
					dto.setModelId(sppCar.getCarId());
					dto.setModelName(sppCar.getCarName());
					dto.setYearOfMake(String.valueOf(sppPrices.getPriceYear()));
					dto.setActualValue(sppPrices.getPriceVnd().longValue());
				}
			}
			
			int khauTruCheck = 0;
			int khaoHaoCheck = 0;
			int matCapCheck = 0;
			int ngapNuocCheck = 0;
			int garageCheck = 0;
			
			if (ComponentUtils.getComponentValue(form, "listDieukhoanBS") != null) {
				List<String> option = (List<String>) ComponentUtils.getComponentValue(form, "listDieukhoanBS");
				for (String item : option) {
//					if (item.equals("khauTruCheck")) {
//						khauTruCheck = 1;
//					}
					if (item.equals("khaoHaoCheck")) {
						khaoHaoCheck = 1;
					}
					if (item.equals("matCapCheck")) {
						matCapCheck = 1;
					}
					if (item.equals("ngapNuocCheck")) {
						ngapNuocCheck = 1;
					}
					if (item.equals("garageCheck")) {
						garageCheck = 1;
					}
				}
			}
			
			dto.setKhauHaoCheck(khaoHaoCheck);
			dto.setKhauTruCheck(khauTruCheck);
			dto.setMatCapCheck(matCapCheck);
			dto.setNgapNuocCheck(ngapNuocCheck);
			dto.setGarageCheck(garageCheck);
			
		}
		
		// Tính Phí
		PremiumCARVM objPremium = getValuePremiumCar(dto);
		// calculatePremium
		PremiumCARVM premiumCar = productCARService.calculatePremium(objPremium, "");
		if (premiumCar != null) {
			if (premiumCar.getPremium() != null) {
				dto.setPremium(premiumCar.getPremium());
			}
			if (premiumCar.getTotalPremium() != null) {
				dto.setTotalPremium(premiumCar.getTotalPremium());
			}
			if (premiumCar.getTndsbbPhi() != null) {
				dto.setTndsbbPhi(premiumCar.getTndsbbPhi());
			}
			if (premiumCar.getTndstnPhi() != null) {
				dto.setTndstnPhi(premiumCar.getTndstnPhi());
			}
			if (premiumCar.getNntxPhi() != null) {
				dto.setPassengersAccidentPremium(premiumCar.getNntxPhi());
			}
			if (premiumCar.getVcxPhi() != null) {
				dto.setVcxPhi(premiumCar.getVcxPhi());
			}
		}
		TmpMomoCarDTO updateStep2 = tmpMomoCarService.save(dto);
		log.debug("Result to saveFormDataStep2, {}", updateStep2);
		return true;
	}
	
	@Override
	public boolean saveFormDataStep3(String requestId, List<MomoComponent> form) throws AgencyBusinessException{
		// Get value of form
		log.debug("Request to saveFormDataStep3, requestId{} form", requestId, form);
		// Save to temp table
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		if (dto == null) {
			throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
		}
		
		String contactName = (String) ComponentUtils.getComponentValue(form, "contactName");
		String contactEmail = (String) ComponentUtils.getComponentValue(form, "contactEmail");
		String contactPhoneNumber = (String) ComponentUtils.getComponentValue(form, "contactPhoneNumber");
		
		String insuredName = (String) ComponentUtils.getComponentValue(form, "insuredName");
		String insuredAddress = (String) ComponentUtils.getComponentValue(form, "insuredAddress");
		String registrationNumber = (String) ComponentUtils.getComponentValue(form, "registrationNumber");
		String chassisNumber = (String) ComponentUtils.getComponentValue(form, "chassisNumber");
		String engineNumber = (String) ComponentUtils.getComponentValue(form, "engineNumber");
		String thoihantu = (String) ComponentUtils.getComponentValue(form, "thoihantu");
		
		// Set properties of step2
		if(!StringUtils.isEmpty(contactName)) {
			dto.setContactName(contactName);
		}
		if(!StringUtils.isEmpty(contactEmail)) {
			dto.setContactEmail(contactEmail);
		}
		if(!StringUtils.isEmpty(contactPhoneNumber)) {
			dto.setContactPhonenumber(contactPhoneNumber);
		}
		
		if(!StringUtils.isEmpty(insuredName)) {
			dto.setInsuredName(insuredName);
		}
		if(!StringUtils.isEmpty(insuredAddress)) {
			dto.setInsuredAddress(insuredAddress);
		}
		if(!StringUtils.isEmpty(registrationNumber)) {
			dto.setRegistrationNumber(registrationNumber);
		}
		if(!StringUtils.isEmpty(chassisNumber)) {
			dto.setChassisNumber(chassisNumber);
		}
		if(!StringUtils.isEmpty(engineNumber)) {
			dto.setEngineNumber(engineNumber);
		}
		if(!StringUtils.isEmpty(thoihantu)) {
			dto.setThoiHanTu(thoihantu);
			String thoiHanDen = DateUtils.date2Str(DateUtils.getCurrentYearPrevious(DateUtils.str2Date(thoihantu), -1));
			dto.setThoiHanDen(thoiHanDen);
		}
		TmpMomoCarDTO updateStep3 = tmpMomoCarService.save(dto);
		
		log.debug("Result to saveFormDataStep3, {}", updateStep3);
		return true;
	}

	@Override
	public boolean saveFormDataStep4(String requestId, List<MomoComponent> form) throws AgencyBusinessException{
		// Get value of form
		log.debug("Request to saveFormDataStep4, requestId{} form", requestId, form);
		// Save to temp table
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		if (dto == null) {
			throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
		}
		
		String receiverName = (String) ComponentUtils.getComponentValue(form, "receiverName");
		String receiverAddress = (String) ComponentUtils.getComponentValue(form, "receiverAddress");
		String receiverAddressDistrict = (String) ComponentUtils.getComponentValue(form, "receiverAddressDistrict");
		String receiverEmail = (String) ComponentUtils.getComponentValue(form, "receiverEmail");
		String receiverMobile = (String) ComponentUtils.getComponentValue(form, "receiverMobile");
		
		// Set properties of step4
		if(!StringUtils.isEmpty(receiverName)) {
			dto.setReceiverName(receiverName);
		}
		if(!StringUtils.isEmpty(receiverAddress)) {
			dto.setReceiverAddress(receiverAddress);
		}
		if(!StringUtils.isEmpty(receiverAddressDistrict)) {
			dto.setReceiverAddressDistrict(receiverAddressDistrict);
		}
		if(!StringUtils.isEmpty(receiverEmail)) {
			dto.setReceiverEmail(receiverEmail);
		}
		if(!StringUtils.isEmpty(receiverMobile)) {
			dto.setReceiverMobile(receiverMobile);
		}
		
		TmpMomoCarDTO updateStep4 = tmpMomoCarService.save(dto);
		
		log.debug("Result to saveFormDataStep4, {}", updateStep4);
		
		return true;
	}

	@Override
	public ProductCarVM getValueTmpMomoCar(TmpMomoCarDTO dto, PremiumCARVM premiumCar) throws AgencyBusinessException{
		ProductCarVM obj = new ProductCarVM();

    	if (!StringUtils.isEmpty(dto.getContactPhonenumber())) {
    		ContactDTO contactDTO = contactService.findOneByPhoneAndType(dto.getContactPhonenumber(), "T000080696");
    		if (contactDTO != null) {
    			obj.setContactCode(contactDTO.getContactCode());
    		} else {
    			ContactDTO objContact = new ContactDTO();
    			ContactCreateVM contactCreateVM= new ContactCreateVM();
    			if (!StringUtils.isEmpty(dto.getContactName())) {
    				objContact.setContactName(dto.getContactName());
    				contactCreateVM.setContactName(dto.getContactName());
    	    	}
    			if (!StringUtils.isEmpty(dto.getContactEmail())) {
    				objContact.setEmail(dto.getContactEmail());
    				contactCreateVM.setEmail(dto.getContactEmail());
    	    	}
    			if (!StringUtils.isEmpty(dto.getContactAddress())) {
    				objContact.setHomeAddress(dto.getContactAddress());
    				contactCreateVM.setHomeAddress(dto.getContactAddress());
    	    	}
    			if (!StringUtils.isEmpty(dto.getContactPhonenumber())) {
    				objContact.setPhone(dto.getContactPhonenumber());
    				contactCreateVM.setPhone(dto.getContactPhonenumber());
    	    	}
    			String contactCode = contactService.generateContactCode("T000080696");
    			objContact.setContactCode(contactCode);
    			objContact.setType("MOMO");
    			ContactDTO data = contactService.create(objContact, contactCreateVM);
    			if(data != null) {
    				obj.setContactCode(data.getContactCode());	
    			}
    		}
    	}
    	
    	CodeManagementDTO codeManagementDTO = codeManagementService.getCodeManagement("CAR");
    	if (codeManagementDTO != null) {
    		obj.setGycbhNumber(codeManagementDTO.getIssueNumber());
    	}
    	
    	if (dto.getChangePremium() != null && dto.getChangePremium() > 0){
    		obj.setChangePremium(dto.getChangePremium());
    	} else {
    		obj.setChangePremium(0d);
    	}
    	// Số Khung
    	if (!StringUtils.isEmpty(dto.getChassisNumber())) {
    		obj.setChassisNumber(dto.getChassisNumber());
    	}
    	// Số máy
    	if (!StringUtils.isEmpty(dto.getEngineNumber())) {
    		obj.setEngineNumber(dto.getEngineNumber());
    	}
    	if (!StringUtils.isEmpty(dto.getInsuredName())) {
    		obj.setInsuredName(dto.getInsuredName());
    	}
    	if (!StringUtils.isEmpty(dto.getInsuredAddress())) {
    		obj.setInsuredAddress(dto.getInsuredAddress());
    	}
    	if (dto.getKhauHaoCheck() != null && dto.getKhauHaoCheck() > 0) {
    		obj.setKhaoHaoCheck(true);
    	} else {
    		obj.setKhaoHaoCheck(false);
    	}
    	if (dto.getKhauTruCheck() != null && dto.getKhauTruCheck() > 0) {
    		obj.setKhauTruCheck(true);
    	} else {
    		obj.setKhauTruCheck(false);
    	}
    	if (dto.getNgapNuocCheck() != null && dto.getNgapNuocCheck() > 0) {
    		obj.setNgapNuocCheck(true);
    	} else {
    		obj.setNgapNuocCheck(false);
    	}
    	if (dto.getMatCapCheck() != null && dto.getMatCapCheck() > 0) {
    		obj.setMatCapCheck(true);
    	} else {
    		obj.setMatCapCheck(false);
    	}
    	if (dto.getGarageCheck() != null && dto.getGarageCheck() > 0) {
    		obj.setGarageCheck(true);
    	} else {
    		obj.setGarageCheck(false);
    	}
    	// người ngồi trên xe check
    	if (dto.getNntxCheck() != null && dto.getNntxCheck() > 0) {
    		obj.setNntxCheck(true);
    	} else {
    		obj.setNntxCheck(false);
    	}
    	// nntx số chỗ
    	if (dto.getPassengersAccidentNumber() != null && dto.getPassengersAccidentNumber() > 0) {
    		obj.setPassengersAccidentNumber(dto.getPassengersAccidentNumber());
    	} else {
    		obj.setPassengersAccidentNumber(0d);
    	}
    	// nntx phí
    	if (premiumCar.getNntxPhi() != null && premiumCar.getNntxPhi() > 0) {
    		obj.setPassengersAccidentPremium(premiumCar.getNntxPhi());
    	} else {
    		obj.setPassengersAccidentPremium(0d);
    	}
    	// nntx so tien
    	if (dto.getPassengersAccidentSi() != null && dto.getPassengersAccidentSi() > 0) {
    		obj.setPassengersAccidentSi(dto.getPassengersAccidentSi());
    	} else {
    		obj.setPassengersAccidentSi(0d);
    	}
    	
    	obj.setPurposeOfUsageId("15");
    	// biển kiểm soát
    	if (!StringUtils.isEmpty(dto.getRegistrationNumber())) {
    		obj.setRegistrationNumber(dto.getRegistrationNumber());
    	}
    	if (!StringUtils.isEmpty(dto.getTndsSocho())) {
    		obj.setTndsSoCho(dto.getTndsSocho());
    	}
    	if (dto.getTndsbbCheck() != null && dto.getTndsbbCheck() > 0) {
    		obj.setTndsbbCheck(true);
    	} else {
    		obj.setTndsbbCheck(false);
    	}
    	// TndstnCheck
    	if (dto.getTndstnCheck() != null && dto.getTndstnCheck() > 0) {
    		obj.setTndstnCheck(true);
    	} else {
    		obj.setTndstnCheck(false);
    	}
    	if (premiumCar.getTndstnPhi() != null && premiumCar.getTndstnPhi() > 0) {
    		obj.setTndstnPhi(premiumCar.getTndstnPhi());
    	} else {
    		obj.setTndstnPhi(0d);
    	}
    	if (dto.getTndstnSotien() != null && dto.getTndstnSotien() > 0) {
    		obj.setTndstnSotien(dto.getTndstnSotien());
    	} else {
    		obj.setTndstnSotien(0d);
    	}
    	// vật chất check
    	if (dto.getVcxCheck() != null && dto.getVcxCheck() > 0) {
    		obj.setVcxCheck(true);
    	} else {
    		obj.setVcxCheck(false);
    	}
    	if (!StringUtils.isEmpty(dto.getMakeId())) {
    		obj.setMakeId(dto.getMakeId());
    	}
    	if (!StringUtils.isEmpty(dto.getMakeName())) {
    		obj.setMakeName(dto.getMakeName());
    	}
    	if (!StringUtils.isEmpty(dto.getModelId())) {
    		obj.setModelId(dto.getModelId());
    	}
    	if (!StringUtils.isEmpty(dto.getModelName())) {
    		obj.setModelName(dto.getModelName());
    	}
    	// năm sản xuất
    	if (!StringUtils.isEmpty(dto.getYearOfMake())) {
    		obj.setYearOfMake(dto.getYearOfMake());
    	}
    	// vc phí
    	if (premiumCar.getVcxPhi() != null && premiumCar.getVcxPhi() > 0) {
    		obj.setPhysicalDamagePremium(premiumCar.getVcxPhi());
    	} else {
    		obj.setPhysicalDamagePremium(0d);
    	}
    	// vc số tiền tham gia bảo hiểm hiện đang để theo giá xe thị trường
    	if (premiumCar.getVcxSoTien() != null && premiumCar.getVcxSoTien() > 0) {
    		obj.setPhysicalDamageSi(premiumCar.getVcxSoTien());
    	} else {
    		obj.setPhysicalDamageSi(0d);
    	}
    	// giá trị xe theo thị trường
    	if (dto.getActualValue() != null && dto.getActualValue() > 0) {
    		obj.setActualValue(dto.getActualValue());
    	} 
    	// Tổng tiền thanh toán bao gồm VAT
    	if (premiumCar.getTotalPremium() != null && premiumCar.getTotalPremium() > 0) {
    		obj.setTotalPremium(premiumCar.getTotalPremium());
    	} else {
    		obj.setTotalPremium(0d);
    	}
    	// Tổng phí bảo hiểm bao gồm VAT
    	if (premiumCar.getPremium() != null && premiumCar.getPremium() > 0) {
    		obj.setPremium(premiumCar.getPremium());
    	} else {
    		obj.setPremium(0d);
    	}
    	
    	if (premiumCar.getTndsbbPhi() != null && premiumCar.getTndsbbPhi() > 0) {
    		obj.setThirdPartyPremium(premiumCar.getTndsbbPhi());
    	} else {
    		obj.setThirdPartyPremium(0d);
    	}
    	if (!StringUtils.isEmpty(dto.getThoiHanTu())) {
    		obj.setThoihantu(dto.getThoiHanTu());
    	} 
    	if (!StringUtils.isEmpty(dto.getOldGycbhNumber())) {
    		obj.setOldGycbhNumber(dto.getOldGycbhNumber());
    	}

    	// InvoiceInfoDTO
    	InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
    	if (!StringUtils.isEmpty(dto.getInvoiceCheck()) && Integer.parseInt(dto.getInvoiceCheck()) > 0) {
    		invoiceInfo.setCheck(dto.getInvoiceCheck());
    	} else {
    		invoiceInfo.setCheck("0");
    	}
    	if (Integer.parseInt(invoiceInfo.getCheck()) > 0) {
    		if (!StringUtils.isEmpty(dto.getInvoiceName())) {
    			invoiceInfo.setName(dto.getInvoiceName());
    		}
    		if (!StringUtils.isEmpty(dto.getInvoiceCompany())) {
    			invoiceInfo.setCompany(dto.getInvoiceCompany());
    		}
    		if (!StringUtils.isEmpty(dto.getInvoiceTaxNo())) {
    			invoiceInfo.setTaxNo(dto.getInvoiceTaxNo());
    		}
    		if (!StringUtils.isEmpty(dto.getInvoiceAccountNo())) {
    			invoiceInfo.setAccountNo(dto.getInvoiceAccountNo());
    		}
    		if (!StringUtils.isEmpty(dto.getInvoiceAddress())) {
    			invoiceInfo.setAddress(dto.getInvoiceAddress());
    		}
    	}
    	obj.setInvoiceInfo(invoiceInfo);
    	
    	// ReceiverUserInfoDTO
    	ReceiverUserInfoDTO receiverUser = new ReceiverUserInfoDTO();
    	if (!StringUtils.isEmpty(dto.getReceiverName())) {
    		receiverUser.setName(dto.getReceiverName());
    	}
    	if (!StringUtils.isEmpty(dto.getReceiverAddress())) {
    		receiverUser.setAddress(dto.getReceiverAddress());
    	}
    	if (!StringUtils.isEmpty(dto.getReceiverEmail())) {
    		receiverUser.setEmail(dto.getReceiverEmail());
    	}
    	if (!StringUtils.isEmpty(dto.getReceiverMobile())) {
    		receiverUser.setMobile(dto.getReceiverMobile());
    	}
    	obj.setReceiverUser(receiverUser);
    	obj.setReceiveMethod("2");

		return obj;
	}

	@Override
	public PremiumCARVM getValuePremiumCar(TmpMomoCarDTO dto) throws AgencyBusinessException {
		PremiumCARVM obj = new PremiumCARVM();

    	if (dto.getChangePremium() != null && dto.getChangePremium() > 0){
    		obj.setChangePremium(dto.getChangePremium());
    	} else {
    		obj.setChangePremium(0d);
    	}
    	
    	// người ngồi trên xe
    	if (dto.getNntxCheck() != null && dto.getNntxCheck() > 0) {
    		obj.setNntxCheck(true);
    	} else {
    		obj.setNntxCheck(false);
    	}
    	if (dto.getPassengersAccidentPremium() != null && dto.getPassengersAccidentPremium() > 0) {
    		obj.setNntxPhi(dto.getPassengersAccidentPremium());
    	} else {
    		obj.setNntxPhi(0d);
    	}
    	if (dto.getPassengersAccidentSi() != null && dto.getPassengersAccidentSi() > 0) {
    		obj.setNntxSoTien(dto.getPassengersAccidentSi());
    	} else {
    		obj.setNntxSoTien(0d);
    	}
    	if (dto.getPassengersAccidentNumber() != null && dto.getPassengersAccidentNumber() > 0) {
    		obj.setNntxSoCho(dto.getPassengersAccidentNumber());
    	} else {
    		obj.setNntxSoCho(0d);
    	}
    	obj.setPurposeOfUsageId("15");
    	if (!StringUtils.isEmpty(dto.getTndsSocho())) {
    		obj.setTndsSoCho(dto.getTndsSocho());
    	}
    	// dan su bb
    	if (dto.getTndsbbCheck() != null && dto.getTndsbbCheck() > 0) {
    		obj.setTndsbbCheck(true);
    	} else {
    		obj.setTndsbbCheck(false);
    	}
    	// dan su tu nguyen
    	if (dto.getTndstnCheck() != null && dto.getTndstnCheck() > 0) {
    		obj.setTndstnCheck(true);
    	} else {
    		obj.setTndstnCheck(false);
    	}
    	if (dto.getTndstnSotien() != null && dto.getTndstnSotien() > 0) {
    		obj.setTndstnSoTien(dto.getTndstnSotien());
    	} else {
    		obj.setTndstnSoTien(0d);
    	}
    	// vat chat
    	if (dto.getVcxCheck() != null && dto.getVcxCheck() > 0) {
    		obj.setVcxCheck(true);
    	} else {
    		obj.setVcxCheck(false);
    	}
    	if (!StringUtils.isEmpty(dto.getModelId()) && !StringUtils.isEmpty(dto.getYearOfMake())) {
    		String price = productCARService.getCarPriceWithYear(dto.getModelId(), Integer.parseInt(dto.getYearOfMake()));
    		obj.setVcxSoTien(Double.parseDouble(price));
    	}
    	if (!StringUtils.isEmpty(dto.getYearOfMake())) {
    		obj.setNamSX(Integer.parseInt(dto.getYearOfMake()));
    	}
    	if (dto.getGarageCheck() != null && dto.getGarageCheck() > 0) {
    		obj.setGarage(true);
    	} else {
    		obj.setGarage(false);
    	}
    	if (dto.getKhauHaoCheck() != null && dto.getKhauHaoCheck() > 0) {
    		obj.setKhauHao(true);
    	} else {
    		obj.setKhauHao(false);
    	}
    	if (dto.getKhauTruCheck() != null && dto.getKhauTruCheck() > 0) {
    		obj.setKhauTru(true);
    	} else {
    		obj.setKhauTru(false);
    	}
    	if (dto.getNgapNuocCheck() != null && dto.getNgapNuocCheck() > 0) {
    		obj.setNgapNuoc(true);
    	} else {
    		obj.setNgapNuoc(false);
    	}
    	if (dto.getMatCapCheck() != null && dto.getMatCapCheck() > 0) {
    		obj.setMatCap(true);
    	} else {
    		obj.setMatCap(false);
    	}
		return obj;
	}

	@Override
	public boolean validateDataStep1(List<MomoComponent> form) throws AgencyBusinessException{
		boolean isValid = true;
		// Get value of form
		log.debug("Request to validateDataStep1, {}", form);
		
		int vcxCheck = 0;
		int tndsbbCheck = 0;
		int tndstnCheck = 0;
		
		if (ComponentUtils.getComponentValue(form, "listBaoHiem") != null) {
			List<String> option = (List<String>) ComponentUtils.getComponentValue(form, "listBaoHiem");
			for (String item : option) {
				if (item.equals("vcxCheck")) {
					vcxCheck = 1;
				}
				if (item.equals("tndsbbCheck")) {
					tndsbbCheck = 1;
				}
				if (item.equals("tndstnCheck")) {
					tndstnCheck = 1;
				}
			}
		}
		
		// Check required
		// Số chỗ
		if (tndsbbCheck > 0 || tndstnCheck > 0 || vcxCheck > 0) {
			String tnds_Socho = (String) ComponentUtils.getComponentValue(form, "tnds_Socho");
			if(StringUtils.isEmpty(tnds_Socho)) {
				ComponentUtils.setComponentErrorMessage(form, "tnds_Socho", "Cần lựa chọn số chỗ/trọng tải xe");
				isValid = false;
			}	
		}
		
		return isValid;
	}
	
	@Override
	public boolean validateDataStep2(String requestId, List<MomoComponent> form) throws AgencyBusinessException{
		boolean isValid = true;
		// Get value of form
		log.debug("Request to validateDataStep2, {}", form);
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		if (dto == null) {
			throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
		}
		
		// gycbhNumber
		String oldGycbhNumber = (String) ComponentUtils.getComponentValue(form, "oldGycbhNumber");
		if(oldGycbhNumber != null) {
			if (StringUtils.isEmpty(oldGycbhNumber)) {
				ComponentUtils.setComponentErrorMessage(form, "oldGycbhNumber", "Cần nhập số hợp đồng bảo hiểm cũ");
				isValid = false;
			}	
		}
		
		// trách nhiệm DSTN
		if (dto.getTndstnCheck() != null && dto.getTndstnCheck() > 0) {
			String tndstnSotien = (String) ComponentUtils.getComponentValue(form, "tndstnSotien");
			if(StringUtils.isEmpty(tndstnSotien)) {
				ComponentUtils.setComponentErrorMessage(form, "tndstnSotien", "Cần lựa chọn mức trách nhiệm");
				isValid = false;
			}	
		}
		
		
		// tai nạn người ngồi trên xe
		if (dto.getNntxCheck() != null && dto.getNntxCheck() > 0) {
			String passengersAccidentNumber = (String) ComponentUtils.getComponentValue(form, "passengersAccidentNumber");
			String passengersAccidentSi = (String) ComponentUtils.getComponentValue(form, "passengersAccidentSi");
			if(StringUtils.isEmpty(passengersAccidentNumber)) {
				ComponentUtils.setComponentErrorMessage(form, "passengersAccidentNumber", "Người ngồi trên xe không được để trống");
				isValid = false;
			}
			if(!StringUtils.isEmpty(passengersAccidentNumber)) {
				if(!StringUtils.isNumeric(passengersAccidentNumber)) {
					ComponentUtils.setComponentErrorMessage(form, "passengersAccidentNumber", "Số người không đúng định dạng");
					isValid = false;	
				}
			}
			if(!StringUtils.isEmpty(passengersAccidentNumber)) {
				if(StringUtils.isNumeric(passengersAccidentNumber) && passengersAccidentNumber.equals("0")) {
					ComponentUtils.setComponentErrorMessage(form, "passengersAccidentNumber", "Số người phải lớn hơn hoặc bằng 1");
					isValid = false;	
				}
			}	
			if(!StringUtils.isEmpty(passengersAccidentNumber)) {
				if(StringUtils.isNumeric(passengersAccidentNumber)) {
					PurposeOfUsageDTO p = new PurposeOfUsageDTO();
					p = purposeOfUsageService.getPurposeOfUsageById(dto.getTndsSocho());
					if (p.getSeatNumberTo() != null) {
						if (Integer.parseInt(passengersAccidentNumber) > p.getSeatNumberTo()) {
							ComponentUtils.setComponentErrorMessage(form, "passengersAccidentNumber", "Số người vượt quá số chỗ của xe");
							isValid = false;
						}
					}
				}
			} 
			
			if(StringUtils.isEmpty(passengersAccidentSi)) {
				ComponentUtils.setComponentErrorMessage(form, "passengersAccidentSi", "Cần lựa chọn mức trách nhiệm");
				isValid = false;
			}
		}
		
		// vật chất xe
		if (dto.getVcxCheck() != null && dto.getVcxCheck() > 0) {
			String hangxeId = (String) ComponentUtils.getComponentValue(form, "hangxeId");
			if(StringUtils.isEmpty(hangxeId)) {
				ComponentUtils.setComponentErrorMessage(form, "hangxeId", "Cần lựa thông tin bảo hiểm xe");
				isValid = false;
			} else {
				SppPrices sppPrices = sppPricesRepository.findOne(hangxeId);
				if (sppPrices == null) {
					ComponentUtils.setComponentErrorMessage(form, "hangxeId", "Dữ liệu không phù hợp, cần lựa chọn năm sản xuất");
					isValid = false;
				}
			}
		}
		
		return isValid;
	}
	
	@Override
	public boolean validateDataStep3(List<MomoComponent> form) {
		boolean isValid = true;
		
		String contactName = (String) ComponentUtils.getComponentValue(form, "contactName");
		String contactEmail = (String) ComponentUtils.getComponentValue(form, "contactEmail");
		String contactPhoneNumber = (String) ComponentUtils.getComponentValue(form, "contactPhoneNumber");
		
		String insuredName = (String) ComponentUtils.getComponentValue(form, "insuredName");
		String insuredAddress = (String) ComponentUtils.getComponentValue(form, "insuredAddress");
		String registrationNumber = (String) ComponentUtils.getComponentValue(form, "registrationNumber");
		String chassisNumber = (String) ComponentUtils.getComponentValue(form, "chassisNumber");
		String engineNumber = (String) ComponentUtils.getComponentValue(form, "engineNumber");
		String thoihantu = (String) ComponentUtils.getComponentValue(form, "thoihantu");
		Date currentDate = new Date();
		
		// Check required
		// gycbhNumber
		String oldGycbhNumber = (String) ComponentUtils.getComponentValue(form, "oldGycbhNumber");
		if(oldGycbhNumber != null) {
			if (StringUtils.isEmpty(oldGycbhNumber)) {
				ComponentUtils.setComponentErrorMessage(form, "oldGycbhNumber", "Cần nhập số hợp đồng bảo hiểm cũ");
				isValid = false;
			}	
		}
		
		if (StringUtils.isEmpty(contactName)) {
			ComponentUtils.setComponentErrorMessage(form, "contactName", "Tên khách hàng không được để trống");
			isValid = false;
		}
		if (StringUtils.isEmpty(contactEmail)) {
			ComponentUtils.setComponentErrorMessage(form, "contactEmail", "Email khách hàng không được để trống");
			isValid = false;
		}
		if (StringUtils.isEmpty(contactPhoneNumber)) {
			ComponentUtils.setComponentErrorMessage(form, "contactPhoneNumber", "Email khách hàng không được để trống");
			isValid = false;
		}
		
		if(StringUtils.isEmpty(insuredName)) {
			ComponentUtils.setComponentErrorMessage(form, "insuredName", "Cần nhập tên chủ xe");
			isValid = false;
		}
		if(StringUtils.isEmpty(insuredAddress)) {
			ComponentUtils.setComponentErrorMessage(form, "insuredAddress", "Cần nhập địa chỉ");
			isValid = false;
		}
		if(StringUtils.isEmpty(registrationNumber)) {
			ComponentUtils.setComponentErrorMessage(form, "registrationNumber", "Cần nhập biển kiểm soát");
			isValid = false;
		}
		if(StringUtils.isEmpty(chassisNumber)) {
			ComponentUtils.setComponentErrorMessage(form, "chassisNumber", "Cần nhập số khung");
			isValid = false;
		}
		if(StringUtils.isEmpty(engineNumber)) {
			ComponentUtils.setComponentErrorMessage(form, "engineNumber", "Cần nhập số máy");
			isValid = false;
		}
		if(StringUtils.isEmpty(thoihantu)) {
			ComponentUtils.setComponentErrorMessage(form, "thoihantu", "Cần nhập thời hạn từ");
			isValid = false;
		}
		if(!StringUtils.isEmpty(thoihantu)) {
			if (DateUtils.str2Date(thoihantu).before(currentDate)) {
				ComponentUtils.setComponentErrorMessage(form, "thoihantu", "Thời hạn từ phải > ngày hiện tại");
				isValid = false;
			}
		}
		
		return isValid;
	}

	@Override
	public boolean validateDataStep4(List<MomoComponent> form) {
		boolean isValid = true;
		// Get value of form
		log.debug("Request to validateDataStep4, {}", form);
		
		String invoiceName = (String) ComponentUtils.getComponentValue(form, "invoiceName");
		String invoiceCompany = (String) ComponentUtils.getComponentValue(form, "invoiceCompany");
		String invoiceTaxNo = (String) ComponentUtils.getComponentValue(form, "invoiceTaxNo");
		String invoiceAddress = (String) ComponentUtils.getComponentValue(form, "invoiceAddress");
		String invoiceAccountNo = (String) ComponentUtils.getComponentValue(form, "invoiceAccountNo");
		
		String receiverName = (String) ComponentUtils.getComponentValue(form, "receiverName");
		String receiverAddress = (String) ComponentUtils.getComponentValue(form, "receiverAddress");
		String receiverEmail = (String) ComponentUtils.getComponentValue(form, "receiverEmail");
		String receiverMobile = (String) ComponentUtils.getComponentValue(form, "receiverMobile");
		
		if (invoiceName != null && StringUtils.isEmpty(invoiceName)) {
			ComponentUtils.setComponentErrorMessage(form, "invoiceName", "Cần nhập Họ và tên");
			isValid = false;
		}
		if (invoiceCompany != null && StringUtils.isEmpty(invoiceCompany)) {
			ComponentUtils.setComponentErrorMessage(form, "invoiceCompany", "Cần nhập Tên đơn vị");
			isValid = false;
		}
		if (invoiceTaxNo != null && StringUtils.isEmpty(invoiceTaxNo)) {
			ComponentUtils.setComponentErrorMessage(form, "invoiceTaxNo", "Cần nhập Mã số thuế");
			isValid = false;
		}
		if (invoiceAddress != null && StringUtils.isEmpty(invoiceAddress)) {
			ComponentUtils.setComponentErrorMessage(form, "invoiceAddress", "Cần nhập Địa chỉ đơn vị");
			isValid = false;
		}
		if (invoiceAccountNo != null && StringUtils.isEmpty(invoiceAccountNo)) {
			ComponentUtils.setComponentErrorMessage(form, "invoiceAccountNo", "Cần nhập Số tài khoản");
			isValid = false;
		}
		
		if(StringUtils.isEmpty(receiverName)) {
			ComponentUtils.setComponentErrorMessage(form, "receiverName", "Cần nhập Họ và tên người nhận");
			isValid = false;
		}
		if(StringUtils.isEmpty(receiverAddress)) {
			ComponentUtils.setComponentErrorMessage(form, "receiverAddress", "Cần nhập địa chỉ nhận");
			isValid = false;
		}
		if(StringUtils.isEmpty(receiverEmail)) {
			ComponentUtils.setComponentErrorMessage(form, "receiverEmail", "Cần nhập thông tin email");
			isValid = false;
		}
		if(StringUtils.isEmpty(receiverMobile)) {
			ComponentUtils.setComponentErrorMessage(form, "receiverMobile", "Cần nhập số điện thoại liên hệ");
			isValid = false;
		}
		if(!StringUtils.isEmpty(receiverMobile)) {
			if (!ValidateUtils.isPhone(receiverMobile)) {
				ComponentUtils.setComponentErrorMessage(form, "receiverMobile", "Số điện thoại không đúng định dạng");
				isValid = false;
			}
		}
		if(!StringUtils.isEmpty(receiverEmail)) {
			if (!ValidateUtils.isEmail(receiverEmail)) {
				ComponentUtils.setComponentErrorMessage(form, "receiverEmail", "Email không đúng định dạng");
				isValid = false;
			}
		}
		return isValid;
	}
	
	@Override
	public boolean validateDataStepGTGT(List<MomoComponent> form) {
		boolean isValid = true;
		// Get value of form
		log.debug("Request to validateDataStepGTGT, {}", form);
		
		String invoiceName = (String) ComponentUtils.getComponentValue(form, "invoiceName");
		String invoiceCompany = (String) ComponentUtils.getComponentValue(form, "invoiceCompany");
		String invoiceTaxNo = (String) ComponentUtils.getComponentValue(form, "invoiceTaxNo");
		String invoiceAddress = (String) ComponentUtils.getComponentValue(form, "invoiceAddress");
		String invoiceAccountNo = (String) ComponentUtils.getComponentValue(form, "invoiceAccountNo");
		
		if (invoiceName != null && StringUtils.isEmpty(invoiceName)) {
			ComponentUtils.setComponentErrorMessage(form, "invoiceName", "Cần nhập Họ và tên");
			isValid = false;
		}
		if (invoiceCompany != null && StringUtils.isEmpty(invoiceCompany)) {
			ComponentUtils.setComponentErrorMessage(form, "invoiceCompany", "Cần nhập Tên đơn vị");
			isValid = false;
		}
		if (invoiceTaxNo != null && StringUtils.isEmpty(invoiceTaxNo)) {
			ComponentUtils.setComponentErrorMessage(form, "invoiceTaxNo", "Cần nhập Mã số thuế");
			isValid = false;
		}
		if (invoiceAddress != null && StringUtils.isEmpty(invoiceAddress)) {
			ComponentUtils.setComponentErrorMessage(form, "invoiceAddress", "Cần nhập Địa chỉ đơn vị");
			isValid = false;
		}
		if (invoiceAccountNo != null && StringUtils.isEmpty(invoiceAccountNo)) {
			ComponentUtils.setComponentErrorMessage(form, "invoiceAccountNo", "Cần nhập Số tài khoản");
			isValid = false;
		}
		
		return isValid;
	}

	@Override
	public List<MomoComponent> buildFormDataStepGTGT(List<MomoComponent> formInput) {
		List<MomoComponent> form = new ArrayList<>();
		
		ComponentUtils.createStepIndicator(form, "step4", "2");
		List<String> bindId = new ArrayList<>();
		Boolean idCheckGTGT = (Boolean) ComponentUtils.getComponentValue(formInput, "idCheckGTGT");
		if (idCheckGTGT != null && idCheckGTGT) {
			// Thông tin hóa đơn GTGT
				// Label Thông tin
			ComponentUtils.createLabel(form, "idInvoiceInfo", "THÔNG TIN HÓA ĐƠN GIÁ TRỊ GIA TĂNG", "1");
				// tên người mua
			ComponentUtils.createTextInput(form, "invoiceName", "Họ và tên", "", "/^(.{3,100})$/ig", null);
			bindId.add("invoiceName");
				// tên đơn vị
			ComponentUtils.createTextInput(form, "invoiceCompany", "Tên đơn vị", "", "/^(.{3,100})$/ig", null);
			bindId.add("invoiceCompany");
			// Mã số thuế
			ComponentUtils.createTextInput(form, "invoiceTaxNo", "Mã số thuế", "", "/^(.{3,100})$/ig", null);
			bindId.add("invoiceTaxNo");
				// địa chỉ
			ComponentUtils.createTextInput(form, "invoiceAddress", "Địa chỉ đơn vị", "", "/^(.{3,100})$/ig", null);
			bindId.add("invoiceAddress");
				// Số tài khoản
			ComponentUtils.createTextInput(form, "invoiceAccountNo", "Số tài khoản", "", "/^(.{3,100})$/ig", null);
			bindId.add("invoiceAccountNo");
		}
		// button
		ComponentUtils.createButton(form, "btnContinue", "Tiếp tục", "Tiếp tục", bindId);
		
		return form;
	}

	@Override
	public boolean saveFormDataStepGTGT(String requestId, List<MomoComponent> form) throws AgencyBusinessException {
		log.debug("Request to saveFormDataStepGTGT, requestId{} form", requestId, form);
		// Save to temp table
		TmpMomoCarDTO dto = tmpMomoCarService.findByRequestId(requestId);
		if (dto == null) {
			throw new AgencyBusinessException("requestId", ErrorCode.INVALID, "requestId không tồn tại");
		}
		
		String invoiceName = (String) ComponentUtils.getComponentValue(form, "invoiceName");
		String invoiceCompany = (String) ComponentUtils.getComponentValue(form, "invoiceCompany");
		String invoiceTaxNo = (String) ComponentUtils.getComponentValue(form, "invoiceTaxNo");
		String invoiceAddress = (String) ComponentUtils.getComponentValue(form, "invoiceAddress");
		String invoiceAccountNo = (String) ComponentUtils.getComponentValue(form, "invoiceAccountNo");
		
		if(!StringUtils.isEmpty(invoiceName)) {
			dto.setInvoiceName(invoiceName);
			dto.setInvoiceCheck("1");
		}
		if(!StringUtils.isEmpty(invoiceCompany)) {
			dto.setInvoiceCompany(invoiceCompany);
			dto.setInvoiceCheck("1");
		}
		if(!StringUtils.isEmpty(invoiceTaxNo)) {
			dto.setInvoiceTaxNo(invoiceTaxNo);
		}
		if(!StringUtils.isEmpty(invoiceAddress)) {
			dto.setInvoiceAddress(invoiceAddress);
		}
		if(!StringUtils.isEmpty(invoiceAccountNo)) {
			dto.setInvoiceAccountNo(invoiceAccountNo);
		}
		
		TmpMomoCarDTO updateStepGTGT = tmpMomoCarService.save(dto);
		
		log.debug("Result to saveFormDataStepGTGT, {}", updateStepGTGT);
		
		return true;
	}

    /*
	 * -------------------------------------------------
	 * ---------------- Private method -----------------
	 * -------------------------------------------------
	 */
	
	private List<String> getHangxe(List<SppCar> data){
		List<String> result = new ArrayList<>();
		for (SppCar item : data) {
			if (!result.contains(item.getCarManufacturer())) {
				result.add(item.getCarManufacturer());	
			}
		}
		return result;
	}
	
	private List<SppCar> getDongxeByHangxe(List<SppCar> data, String hangxe){
		List<SppCar> result = new ArrayList<>();
		for (SppCar item : data) {
			if (StringUtils.equals(item.getCarManufacturer(), hangxe) && !result.contains(item)) {
				result.add(item);	
			}
		}
		return result;
	}
	
	private List<SppPrices> getNamsxByDongxe(List<SppPrices> data, String dongxe){
		List<SppPrices> result = new ArrayList<>();
		for (SppPrices item : data) {
			if (StringUtils.equals(item.getCar(), dongxe) && !result.contains(item)) {
				result.add(item);	
			}
		}
		// Sort
		Collections.sort(result, new Comparator<SppPrices>() {
	        @Override
	        public int compare(final SppPrices object1, final SppPrices object2) {
	        	return object2.getPriceYear() - object1.getPriceYear();
	        }
	    } );
		
		Collections.reverseOrder();
		
		return result;
	}
	
	private List<SppPrices> getGiaxeByNamsx(List<SppPrices> data, String dongxe, String namsx){
		List<SppPrices> result = new ArrayList<>();
		for (SppPrices item : data) {
			if (StringUtils.equals(item.getCar(), dongxe) && StringUtils.equals(String.valueOf(item.getPriceYear()), namsx) && !result.contains(item)) {
				result.add(item);	
			}
		}
		return result;
	}
	
	private void createTableNhan(List<MomoComponent> form, TmpMomoCarDTO dto) {
		ComponentUtils.createLabel(form, "idGNBH", "THÔNG TIN GIAO NHẬN GIẤY CHỨNG NHẬN BẢO HIỂM", "1");
		
		List<List<ItemOptionComponent>> lstRowsNhan = new ArrayList<>();
			// Họ và tên người nhận
		List<List<ItemOptionComponent>> rowsNhan1 = ComponentUtils.createRowTable("Họ và tên người nhận", dto.getReceiverName(), "0");
		lstRowsNhan.addAll(rowsNhan1);
			// Địa chỉ người nhận
		List<List<ItemOptionComponent>> rowsNhan2 = ComponentUtils.createRowTable("Địa chỉ người nhận", dto.getReceiverAddress(), "0");
		lstRowsNhan.addAll(rowsNhan2);
			// Số điện thoại liên hệ
		List<List<ItemOptionComponent>> rowsNhan3 = ComponentUtils.createRowTable("Số điện thoại liên hệ", dto.getReceiverMobile(), "0");
		lstRowsNhan.addAll(rowsNhan3);
			// Địa chỉ email
		List<List<ItemOptionComponent>> rowsNhan4 = ComponentUtils.createRowTable("Địa chỉ email", dto.getReceiverEmail(), "0");
		lstRowsNhan.addAll(rowsNhan4);
		
		MomoComponent tableNhan = new MomoComponent();
		tableNhan.setId("tableNhan");
		tableNhan.setType(MomoComponent.COMPONENT_TABLE);
		tableNhan.setRows(lstRowsNhan);
		form.add(tableNhan);
	}
	
	private void createTableThongTinPhi(List<MomoComponent> form, TmpMomoCarDTO dto) {
		ComponentUtils.createLabel(form, "idTNBH", "MỨC TRÁCH NHIỆM VÀ PHÍ BẢO HIỂM", "1");
		
		List<List<ItemOptionComponent>> lstRows = new ArrayList<>();
		List<List<ItemOptionComponent>> rows1 = new ArrayList<>();
		if (dto.getTndsbbPhi() != null && dto.getTndsbbPhi() > 0) {
			String sotien = formatter.format(dto.getTndsbbPhi().doubleValue()) + " VND";
			rows1 = ComponentUtils.createRowTable("Phí bảo hiểm trách nhiệm dân sự", sotien, "1");
		}
		
		List<List<ItemOptionComponent>> rows2 = new ArrayList<>();
		if (dto.getTndstnPhi() != null && dto.getTndstnPhi() > 0) {
			String sotien = formatter.format(dto.getTndstnPhi().doubleValue()) + " VND";
			rows2 = ComponentUtils.createRowTable("Phí bảo hiểm trách nhiệm dân sự tự nguyện", sotien, "1");
		}
		
		List<List<ItemOptionComponent>> rows3 = new ArrayList<>();
		if (dto.getPassengersAccidentPremium() != null && dto.getPassengersAccidentPremium() > 0) {
			String sotien = formatter.format(dto.getPassengersAccidentPremium().doubleValue()) + " VND";
			rows3 = ComponentUtils.createRowTable("Phí bảo hiểm người ngồi trên xe", sotien, "1");
		}
		
		List<List<ItemOptionComponent>> rows4 = new ArrayList<>();
		if (dto.getVcxPhi() != null && dto.getVcxPhi() > 0) {
			String sotien = formatter.format(dto.getVcxPhi().doubleValue()) + " VND";
			rows4 = ComponentUtils.createRowTable("Phí bảo hiểm Vật chất xe tạm tính", sotien, "1");
		}
		
		List<List<ItemOptionComponent>> rowsTong = new ArrayList<>();
		if (dto.getTotalPremium() != null && dto.getTotalPremium() > 0) {
			String sotien = formatter.format(dto.getTotalPremium().doubleValue()) + " VND";
			rowsTong = ComponentUtils.createRowTable("Tổng phí thanh toán", sotien, "2");
		}
		
		if (rows1.size() > 0) {
			lstRows.addAll(rows1);	
		}
		if (rows2.size() > 0) {
			lstRows.addAll(rows2);	
		}
		if (rows3.size() > 0) {
			lstRows.addAll(rows3);	
		}
		if (rows4.size() > 0) {
			lstRows.addAll(rows4);	
		}
		lstRows.addAll(rowsTong);
		
		MomoComponent tablePhi = new MomoComponent();
		tablePhi.setId("tablePhi");
		tablePhi.setType(MomoComponent.COMPONENT_TABLE);
		tablePhi.setRows(lstRows);
		form.add(tablePhi);	
	}

	private void createTableThongTinBaoHiem(List<MomoComponent> form, TmpMomoCarDTO dto) {
		ComponentUtils.createLabel(form, "idTTBH", "THÔNG TIN BẢO HIỂM", "1");
		
		List<List<ItemOptionComponent>> lstRowsBH = new ArrayList<>();
			// Tên chủ xe
		List<List<ItemOptionComponent>> rowsBH1 = ComponentUtils.createRowTable("Tên chủ xe", dto.getInsuredName(), "0");
		lstRowsBH.addAll(rowsBH1);
			 
		if (dto.getVcxCheck() != null && dto.getVcxCheck() > 0) {
			// Hãng xe
			List<List<ItemOptionComponent>> rowsBH2 = ComponentUtils.createRowTable("Hãng xe", dto.getMakeId(), "0");
			lstRowsBH.addAll(rowsBH2);
				// Dòng xe
			List<List<ItemOptionComponent>> rowsBH3 = ComponentUtils.createRowTable("Dòng xe", dto.getModelName(), "0");
			lstRowsBH.addAll(rowsBH3);
		}
		
			// Địa chỉ
		List<List<ItemOptionComponent>> rowsBH4 = ComponentUtils.createRowTable("Địa chỉ", dto.getInsuredAddress(), "0");
		lstRowsBH.addAll(rowsBH4);
			// Biển số
		List<List<ItemOptionComponent>> rowsBH5 = ComponentUtils.createRowTable("Biển số", dto.getRegistrationNumber(), "0");
		lstRowsBH.addAll(rowsBH5);
			// Số khung
		List<List<ItemOptionComponent>> rowsBH6 = ComponentUtils.createRowTable("Số khung", dto.getChassisNumber(), "0");
		lstRowsBH.addAll(rowsBH6);
			// Số máy
		List<List<ItemOptionComponent>> rowsBH7 = ComponentUtils.createRowTable("Số máy", dto.getEngineNumber(), "0");
		lstRowsBH.addAll(rowsBH7);
		
		String strLoaixe = "";
		if (dto.getTndsSocho() != null) {
			switch (dto.getTndsSocho()) {
			case "1":
				strLoaixe = "Loại xe dưới 6 chỗ ngồi";
				break;
			case "2":
				strLoaixe = "Loại xe từ 6 đến 11 chỗ ngồi";
				break;
			case "3":
				strLoaixe = "Loại xe từ 12 đến 24 chỗ ngồi";
				break;
			case "4":
				strLoaixe = "Loại xe trên 24 chỗ ngồi";
				break;
			case "5":
				strLoaixe = "Xe vừa chở người vừa chở hàng(Pickup, Minivan)";
				break;
			default:
				break;
			}	
		}
			//Loại xe
		List<List<ItemOptionComponent>> rowsBH8 = ComponentUtils.createRowTable("Loại xe", strLoaixe, "0");
		lstRowsBH.addAll(rowsBH8);
		
		MomoComponent tableTTBH = new MomoComponent();
		tableTTBH.setId("tableTTBH");
		tableTTBH.setType(MomoComponent.COMPONENT_TABLE);
		tableTTBH.setRows(lstRowsBH);
		form.add(tableTTBH);
	}
	
}
