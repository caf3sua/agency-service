package com.baoviet.agency.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import com.baoviet.agency.bean.InvoiceInfoDTO;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.SktdRate;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.BvpDTO;
import com.baoviet.agency.dto.FilesDTO;
import com.baoviet.agency.dto.TinhtrangSkDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.SktdRateRepository;
import com.baoviet.agency.repository.TinhtrangSkRepository;
import com.baoviet.agency.service.BVPService;
import com.baoviet.agency.service.FilesService;
import com.baoviet.agency.service.ProductBVPService;
import com.baoviet.agency.service.TinhtrangSkService;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.PremiumBVPVM;
import com.baoviet.agency.web.rest.vm.PremiumSKTDVM;
import com.baoviet.agency.web.rest.vm.ProductBvpVM;
import com.baoviet.agency.web.rest.vm.SKTDAddVM;

/**
 * Service Implementation for managing bvp.
 * 
 * @author Duc, Le Minh
 */
@Service
@Transactional
@CacheConfig(cacheNames = "product")
public class ProductBVPServiceImpl extends AbstractProductService implements ProductBVPService {

	private final Logger log = LoggerFactory.getLogger(ProductBVPServiceImpl.class);

	@Autowired
	private SktdRateRepository sktdRateRepository;

	@Autowired
	private FilesService filesService;

	@Autowired
	private BVPService bVPService;

	@Autowired
	private TinhtrangSkService tinhtrangSkService;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private TinhtrangSkRepository tinhtrangSkRepository;

	@Override
	public ProductBvpVM createOrUpdatePolicy(ProductBvpVM obj, AgencyDTO currentAgency) throws AgencyBusinessException {
		log.debug("Request to createOrUpdatePolicy, ProductBvpVM{}, currentAgency{} :", obj, currentAgency);
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			validateGycbhNumber(obj.getGycbhNumber(), currentAgency.getMa());
		}

		// Check validate data
		validateDataPolicy(obj, currentAgency, "0");

		Contact co = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}

		BvpDTO bvp = getObjectProduct(obj, co);

		AgreementDTO agreement = getObjectAgreement(obj, co, bvp, currentAgency);

		if (!currentAgency.getMa().equals("NCB")) {
			Integer tuoi = DateUtils.countYears(obj.getNguoidbhNgaysinh(), obj.getInceptionDate());
			if (tuoi < 18) {
				FilesDTO fileInfo = new FilesDTO();
				SimpleDateFormat sdfr = new SimpleDateFormat("ddMMyyyHHmmss");
				String fileName = "IMG_" + sdfr.format(new Date());
				fileInfo.setName(fileName);
				fileInfo.setType(".jpg");
				fileInfo.setLength(obj.getFiles().length() * 1.0);
				fileInfo.setLineId("BVP");
				fileInfo.setLineName("SKTD");
				fileInfo.setGycbhId("");
				fileInfo.setTypeOfDocument(fileName);
				fileInfo.setContentFile(obj.getFiles());
				String fileId = filesService.save(fileInfo);
				if (fileId != null) {
					bvp.setQ1Id(fileId);
				}
			}
		}

		String bvpId = bVPService.Insert(bvp);
		if (bvpId != null) {
			Integer id = Integer.parseInt(bvpId);
			if (id > 0) {
				agreement.setGycbhId(bvpId);
				agreement = agreementService.save(agreement);
				obj.setAgreementId(agreement.getAgreementId());
				if (StringUtils.isNotEmpty(obj.getGycbhId())) {
					if (obj.getLstAdd() != null && obj.getLstAdd().size() > 0) {
						// xóa tình trạng sức khỏe
						tinhtrangSkRepository.deleteByIdThamchieu(obj.getGycbhId());
					}
				}
				if (StringUtils.equals(obj.getQ3(), "1")) {
					for (SKTDAddVM sk : obj.getLstAdd()) {
						TinhtrangSkDTO skInfo = new TinhtrangSkDTO();
						if (sk.getNgaydieutri() != null && !StringUtils.isEmpty(DateUtils.date2Str(sk.getNgaydieutri()))) {
							skInfo.setNgaydieutri(DateUtils.str2Date(DateUtils.date2Str(sk.getNgaydieutri())));	
						}
						skInfo.setChuandoan(sk.getChuandoan());
						skInfo.setChitietdieutri(sk.getChitietdieutri());
						skInfo.setKetqua(sk.getKetqua());
						skInfo.setBenhvienorbacsy(sk.getBenhvienorbacsy());
						skInfo.setMasanpham("BVP");
						skInfo.setIdThamchieu(bvpId);
						skInfo.setQuestionThamchieu("1");
						skInfo.setSohd("");
						skInfo.setCongtybh("");
						skInfo.setSotienbh(0d);
						skInfo.setLydoycbt("");
						skInfo.setSotienycbt(0d);
						skInfo.setKhuoctu("");
						skInfo.setDkdacbiet("");
						skInfo.setLydodc("");
						if (sk.getNgaydieutri() != null && !StringUtils.isEmpty(DateUtils.date2Str(sk.getNgaydieutri()))) {
							tinhtrangSkService.save(skInfo);
						}
					}
				}

				// pay_action
				sendSmsAndSavePayActionInfo(co, agreement);
			}

		}

		return obj;
	}

	@Override
	public PremiumBVPVM calculatePremium(PremiumBVPVM obj) throws AgencyBusinessException {
		log.debug("START calculatePremium, obj: {}", obj);

		// Step 1. Validate input
		validDataCalculate(obj);

		// Step 2. Validate nghiep vu buoc 1
		int tuoitinhphi = validatePremiumStep01(obj);

		SktdRate sktdRate = sktdRateRepository.findByChuongTrinhAndTuoiTuLessThanEqualAndTuoiDenGreaterThanEqual(
				obj.getChuongTrinh(), tuoitinhphi, tuoitinhphi);

		double phichuongtrinhchinh = 0;
		double phingoaitru = 0;
		double phitncn = 0;
		double phismcs = 0;
		double phinhakhoa = 0;
		double phithaisan = 0;
		double tongphibh = 0;

		if (sktdRate != null) {
			phichuongtrinhchinh = sktdRate.getQlChinh();
			obj.setQlChinhPhi(phichuongtrinhchinh);

			// Ngoai tru
			if (obj.getNgoaitruChk()) {
				phingoaitru = sktdRate.getQlNgoaiTru();
				obj.setNgoaitruPhi(phingoaitru);
			} else {
				obj.setNgoaitruPhi(0.0);
			}

			// Nha khoa
			if (obj.getNhakhoaChk()) {
				phinhakhoa = sktdRate.getQlNhakhoa();
				obj.setNhakhoaPhi(phinhakhoa);
			} else {
				obj.setNhakhoaPhi(0.0);
			}

			// TNCN
			if (obj.getTncnChk()) {
				phitncn = obj.getTncnSi();
				if (phitncn < 0) {
					throw new AgencyBusinessException("tncnSi", ErrorCode.INVALID, "Sai so tien BH TNCN");
				} else {
					if (validateRangePremium(phitncn)) {
						throw new AgencyBusinessException("tncnSi", ErrorCode.INVALID, "Sai so tien BH TNCN");
					}
					phitncn = sktdRate.getQlTncn() * obj.getTncnSi() / 100;
					obj.setTncnPhi(phitncn);
				}
			} else {
				obj.setTncnPhi(0.0);
			}

			// SMCN
			if (obj.getSmcnChk()) {
				if (obj.getSmcnSi() < 0) {
					throw new AgencyBusinessException("smcnSi", ErrorCode.INVALID, "Sai so tien BH SMCN");
				} else {
					phismcs = sktdRate.getQlSmcn() * obj.getSmcnSi() / 100;
					obj.setSmcnPhi(phismcs);
				}
			} else {
				obj.setSmcnPhi(0.0);
			}

			// Thai san
			if (obj.getThaisanChk()) {
				if (obj.getChuongTrinh().equals("1") || obj.getChuongTrinh().equals("2")
						|| obj.getChuongTrinh().equals("3")) {
					throw new AgencyBusinessException("thaisanChk", ErrorCode.INVALID,
							"Sai quyen loi Thai san. Chuong trinh");
				} else {
					phithaisan = sktdRate.getQlThaiSan();
					obj.setThaisanPhi(phithaisan);
				}
			} else {
				obj.setThaisanPhi(0.0);
			}

		}

		// kiem tra discount va tinh tong phi
		tongphibh = phichuongtrinhchinh + phingoaitru + phinhakhoa + phitncn + phismcs + phithaisan;
		obj.setPremiumNet(tongphibh);

		double discount = obj.getPremiumDiscount() != 0 ? obj.getPremiumNet() * obj.getPremiumDiscount() / 100 : 0;
		obj.setPhiBH(obj.getPremiumNet() - discount);

		return obj;
	}

	@Override
	public ProductBvpVM createOrUpdatePolicyAdayroi(ProductBvpVM obj, AgencyDTO currentAgency, String loaidon, String ordercode) throws AgencyBusinessException {
		log.debug("Request to createOrUpdatePolicy, ProductBvpVM{}, currentAgency{} :", obj, currentAgency);
		// ValidGycbhNumber : Không dùng trong TH update
		if (StringUtils.isEmpty(obj.getAgreementId())) {
			validateGycbhNumber(obj.getGycbhNumber(), currentAgency.getMa());
		}

		// Check validate data
//		validateDataPolicy(obj, currentAgency, "1");

		Contact co = contactRepository.findOneByContactCodeAndType(obj.getContactCode(), currentAgency.getMa());
		if (co == null) {
			throw new AgencyBusinessException("contactCode", ErrorCode.INVALID, "Mã khách hàng không tồn tại");
		}

		BvpDTO bvp = getObjectProduct(obj, co);

		AgreementDTO agreement = getObjectAgreement(obj, co, bvp, currentAgency);
		agreement.setPaymentTransactionId(ordercode);
		
		// đơn sach
		if (loaidon.equals("0")) {
			bvp.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON);
			bvp.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
			// gửi mail, smsm
			agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON);
			agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
			agreement.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_CHO_BV_CAPDON);
			agreement.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_CHO_BV_CAPDON);
			agreement.setCancelPolicySupport3(1d);	 // gửi mail
			agreement.setCancelPolicyCommision3(1d); // gửi sms
		} else {
			bvp.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_GIAMDINH);
			bvp.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_GIAMDINH);
			// giam dinh
			agreement.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_BV_GIAMDINH);
			agreement.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_BV_GIAMDINH);
			agreement.setStatusGycbhId(AppConstants.STATUS_POLICY_ID_CHO_BV_GIAMDINH);
			agreement.setStatusGycbhName(AppConstants.STATUS_POLICY_NAME_CHO_BV_GIAMDINH);
		}

		String bvpId = bVPService.Insert(bvp);
		if (bvpId != null) {
			Integer id = Integer.parseInt(bvpId);
			if (id > 0) {
				agreement.setGycbhId(bvpId);
				agreement = agreementService.save(agreement);
				if (StringUtils.isNotEmpty(obj.getGycbhId())) {
					if (obj.getLstAdd() != null && obj.getLstAdd().size() > 0) {
						// xóa tình trạng sức khỏe
						tinhtrangSkRepository.deleteByIdThamchieu(obj.getGycbhId());
					}
				}
				if (StringUtils.equals(obj.getQ3(), "1")) {
					for (SKTDAddVM sk : obj.getLstAdd()) {
						TinhtrangSkDTO skInfo = new TinhtrangSkDTO();
						skInfo.setNgaydieutri(sk.getNgaydieutri());
						skInfo.setChuandoan(sk.getChuandoan());
						skInfo.setChitietdieutri(sk.getChitietdieutri());
						skInfo.setKetqua(sk.getKetqua());
						skInfo.setBenhvienorbacsy(sk.getBenhvienorbacsy());
						skInfo.setMasanpham("BVP");
						skInfo.setIdThamchieu(bvpId);
						skInfo.setQuestionThamchieu("1");
						skInfo.setSohd("");
						skInfo.setCongtybh("");
						skInfo.setSotienbh(0d);
						skInfo.setLydoycbt("");
						skInfo.setSotienycbt(0d);
						skInfo.setKhuoctu("");
						skInfo.setDkdacbiet("");
						skInfo.setLydodc("");
						tinhtrangSkService.save(skInfo);
					}
				}

				// pay_action
				sendSmsAndSavePayActionInfo(co, agreement);
			}

		}

		return obj;
	}
	
	public void ValidatePremiumAgency(PremiumSKTDVM obj) throws AgencyBusinessException {
		log.debug("Request to ValidatePremiumAgency, PremiumSKTDVM{} :", obj);
		if (StringUtils.isEmpty(obj.getChuongTrinh()))
			throw new AgencyBusinessException("chuongTrinh", ErrorCode.NULL_OR_EMPTY);

		if (StringUtils.isEmpty(DateUtils.date2Str(obj.getNgaysinh())))
			throw new AgencyBusinessException("ngaysinh", ErrorCode.NULL_OR_EMPTY);
		else {
			if (!DateUtils.isValidDate(DateUtils.date2Str(obj.getNgaysinh()), "dd/MM/yyyy")) {
				throw new AgencyBusinessException("ngaysinh", ErrorCode.FORMAT_DATE_INVALID);
			}
		}

		if (StringUtils.isEmpty(DateUtils.date2Str(obj.getThoihanbhTu())))
			throw new AgencyBusinessException("thoihanbhTu", ErrorCode.NULL_OR_EMPTY);
		else {
			if (!DateUtils.isValidDate(DateUtils.date2Str(obj.getThoihanbhTu()), "dd/MM/yyyy")) {
				throw new AgencyBusinessException("thoihanbhTu", ErrorCode.FORMAT_DATE_INVALID);
			}
		}
	}

	/*
	 * ------------------------------------------------- ---------------- Private
	 * method ----------------- -------------------------------------------------
	 */
	private AgreementDTO getObjectAgreement(ProductBvpVM obj, Contact co, BvpDTO bvp, AgencyDTO currentAgency) throws AgencyBusinessException{
		log.debug("Request to getObjectAgreement, ProductBvpVM{}, Contact{}, BvpDTO{}, AgencyDTO{} :", obj, co, bvp,
				currentAgency);
		AgreementDTO agreementInfo = new AgreementDTO();
		// Insert common data
		insertAgreementCommonInfo("BVP", agreementInfo, co, currentAgency, obj);

		agreementInfo.setUserAgent(co.getType());
		agreementInfo.setInceptionDate(obj.getInceptionDate());
		agreementInfo.setExpiredDate(obj.getExpiredDate());

		agreementInfo.setStandardPremium(0.0);
		agreementInfo.setChangePremium(bvp.getTanggiamPhi());

		agreementInfo.setTotalVat(0.0);
		// tong tien thanh toan
		agreementInfo.setTotalPremium(obj.getChuongtrinhPhi() + obj.getNgoaitruPhi() + obj.getTncnPhi()
				+ obj.getSinhmangPhi() + obj.getNhakhoaPhi() + obj.getThaisanPhi());
		// phi BH chua giam bao gom VAT
		agreementInfo.setNetPremium(agreementInfo.getTotalPremium() - agreementInfo.getChangePremium());
		agreementInfo.setDiscount(String.valueOf(obj.getDiscount()));

		if (co.getContactName() != null) {
			agreementInfo.setStatusRenewalsName(co.getContactName());
		}
		return agreementInfo;
	}

	private BvpDTO getObjectProduct(ProductBvpVM obj, Contact co) {
		log.debug("Request to getObjectProduct, ProductBvpVM{}, Contact{} :", obj, co);
		BvpDTO vo = new BvpDTO();
		// NamNH: 18/06/2018
		if (StringUtils.isNotEmpty(obj.getGycbhId())) {
			vo.setId(obj.getGycbhId());
		}

		obj.setPolicyNumber(obj.getGycbhNumber());
		// khi update thì không update gycbhNumber
		if (StringUtils.isEmpty(vo.getId())) {
			vo.setSogycbh(obj.getGycbhNumber());
			vo.setPolicyNumber(obj.getGycbhNumber());
		} else {
			AgreementDTO data = agreementService.findById(obj.getAgreementId());
			if (data != null) {
				vo.setSogycbh(data.getGycbhNumber());
				vo.setPolicyNumber(data.getGycbhNumber());
			}
		}

		vo.setStatusPolicyId(AppConstants.STATUS_POLICY_ID_CHO_THANHTOAN);
		vo.setStatusPolicyName(AppConstants.STATUS_POLICY_NAME_CHO_THANHTOAN);
		vo.setNguoiycName(obj.getNguoiycName());// Họ và tên người yêu cầu bảo hiểm:
		vo.setNguoiycNgaysinh(obj.getNguoiycNgaysinh());// Ngày sinh:NYCBH
		vo.setPolicyParent(obj.getPolicyParent()); // Số HĐBH/GCNBH/Thẻ/Mã đơn hàng Bố(Mẹ):
		vo.setNguoidbhName(obj.getNguoidbhName()); // Họ tên người được bảo hiểm:
		vo.setNguoidbhCmnd(obj.getNguoidbhCmnd()); // Số Chứng minh nhân dân/Hộ chiếu: NDBH
		vo.setNguoidbhNgaysinh(obj.getNguoidbhNgaysinh());
		vo.setNguoidbhQuanhe(obj.getNguoidbhQuanhe());// Quan hệ với người Yêu cầu bảo hiểm:

		vo.setChuongtrinhBh(obj.getChuongtrinhBh());// Chương trình bảo hiểm
		vo.setNgoaitru(obj.getNgoaitru());// Điều trị ngoại trú do ốm bệnh/tai nạn
		vo.setTncn(obj.getTncn());// Bảo hiểm tai nạn cá nhân
		vo.setTncnSotienbh(obj.getTncnSotienbh());// Bảo hiểm tai nạn cá nhân số tiền
		vo.setSinhmang(obj.getSinhmang());// Bảo hiểm sinh mạng
		vo.setSinhmangSotienbh(obj.getSinhmangSotienbh());// Bảo hiểm sinh mạng số tiền
		vo.setNhakhoa(obj.getNhakhoa());// Nha khoa
		vo.setThaisan(obj.getThaisan());

		vo.setChuongtrinhPhi(obj.getChuongtrinhPhi());// Phí chương trình chính
		vo.setNgoaitruPhi(obj.getNgoaitruPhi());// Phí quyền lợi ngoại trú
		vo.setTncnPhi(obj.getTncnPhi());// Phí quyền lợi tai nạn cá nhân
		vo.setSinhmangPhi(obj.getSinhmangPhi());// Phí quyền lợi sinh mạng
		vo.setNhakhoaPhi(obj.getNhakhoaPhi());// Phí quyền lợi nha khoa
		vo.setThaisanPhi(obj.getThaisanPhi());// Phi quyen loi thai san

		vo.setTanggiamPhi(obj.getTanggiamPhi());// Số tiền giảm phí
		vo.setTanggiamPhiNoidung(obj.getTanggiamPhiNoidung());// Nội dung giảm phí

		// vo.TONGPHI_PHI = Convert.ToDecimal(txtPhibh.Value);//Tổng phí thanh toán
		vo.setTongphiPhi(obj.getChuongtrinhPhi() + obj.getNgoaitruPhi() + obj.getTncnPhi() + obj.getSinhmangPhi()
				+ obj.getNhakhoaPhi() + obj.getThaisanPhi() - obj.getTanggiamPhi());// Tổng phí thanh toán

		vo.setInceptionDate(obj.getInceptionDate());
		vo.setExpiredDate(obj.getExpiredDate());
		vo.setQ1(obj.getQ1());// lua chon cau tra loi cho cau hoi a) ve thong tin tinh trang sk
		vo.setQ2(obj.getQ2());// lua chon cau tra loi cho cau hoi b) ve thong tin tinh trang sk
		vo.setQ3(obj.getQ3());// lua chon cau tra loi cho cau hoi c) ve thong tin tinh trang sk
		
		vo.setNguoithName(obj.getNguoithName());// Họ tên người thụ hưởng
		vo.setNguoithCmnd(obj.getNguoithCmnd());// Số CMND/Hộ chiếu/Số giấy khai sinh
		vo.setNguoithQuanhe(obj.getNguoithQuanhe());// Quan hệ với người được bảo hiểm
		if (obj.getNguoithNgaysinh() != null) {
			vo.setNguoithNgaysinh(obj.getNguoithNgaysinh());
		}
		
		vo.setNguoinhanName(obj.getNguoinhanName());// Họ tên người nhận tiền
		vo.setNguoinhanCmnd(obj.getNguoinhanCmnd());// Số Chứng minh nhân dân/Hộ chiếu
		vo.setNguoinhanQuanhe(obj.getNguoinhanQuanhe());// Quan hệ với người được bảo hiểm
		if (obj.getNguointNgaysinh() != null) {
			vo.setNguointNgaysinh(obj.getNguointNgaysinh());	
		}
		
		vo.setContactId(co.getContactId()); // id khach hang
		vo.setContactCode(co.getContactCode()); // ten truy cap khach hang
		vo.setContactAddress(co.getHomeAddress());// dia chi khach hang
		vo.setContactEmail(co.getEmail()); // email khach hang
		vo.setContactPhone(co.getPhone()); // dien thoai kh.

		vo.setReceiverName(obj.getReceiverUser().getName()); // Họ và tên người nhận:
		vo.setReceiverAddress(obj.getReceiverUser().getAddress());// Địa chỉ người nhận
		vo.setReceiverEmail(obj.getReceiverUser().getEmail());// Email người nhận
		vo.setReceiverMoible(obj.getReceiverUser().getMobile());// Số điện thoại liên hệ

		if (obj.getInvoiceInfo() != null) {
			vo.setInvoiceCheck(Integer.parseInt(obj.getInvoiceInfo().getCheck()));// lay hoa don 0: khong lay, 1: lay
																					// hoa
			vo.setInvoiceBuyer(obj.getInvoiceInfo().getName()); // nguoi mua
			vo.setInvoiceCompany(obj.getInvoiceInfo().getCompany()); // cong ty
			vo.setInvoiceTaxNo(obj.getInvoiceInfo().getTaxNo());// ma so thue
			vo.setInvoiceAddress(obj.getInvoiceInfo().getAddress());// dia chi
			if (obj.getInvoiceInfo().getAccountNo() != null) {
				vo.setInvoiceAccountNo(obj.getInvoiceInfo().getAccountNo()); // so tai khoan	
			}
		}

		return vo;
	}

	private boolean validateRangePremium(double range) {
		return range != 50000000 && range != 100000000 && range != 200000000 && range != 300000000 && range != 400000000
				&& range != 500000000 && range != 600000000 && range != 700000000 && range != 800000000
				&& range != 900000000 && range != 1000000000;
	}

	private int validatePremiumStep01(PremiumBVPVM obj) throws AgencyBusinessException {
		log.debug("Request to validatePremiumStep01, PremiumBVPVM{} :", obj);
		int tuoi = DateUtils.countYears(DateUtils.str2Date(obj.getNgaySinh()),
				DateUtils.str2Date(obj.getThoihanbhTu()));
		int tuoitinhphi = 0;
		tuoitinhphi = tuoi;
		obj.setTuoi(tuoi);
		if (tuoitinhphi < 0 || tuoitinhphi > 65) {
			throw new AgencyBusinessException("ngaySinh", ErrorCode.INVALID, "Người được bảo hiểm phải trong độ tuổi từ 15 ngày tuổi đến 65 tuổi");
		} else {
			if (tuoitinhphi == 0) {
				if (DateUtils.getNumberDaysBetween2DateStr(obj.getNgaySinh(), obj.getThoihanbhTu()) < 15
						&& DateUtils.getNumberMonthsBetween2DateStr(obj.getNgaySinh(), obj.getThoihanbhTu()) == 0) {
					throw new AgencyBusinessException("ngaySinh", ErrorCode.INVALID, "Người được bảo hiểm phải trong độ tuổi từ 15 ngày tuổi đến 65 tuổi");
				} else {
					if (obj.getChuongTrinh().equals("1") || obj.getChuongTrinh().equals("2")
							|| obj.getChuongTrinh().equals("3")) {
						throw new AgencyBusinessException("chuongTrinh", ErrorCode.INVALID, "Người được bảo hiểm dưới 1 tuổi thì chỉ tham gia chương trình Bạch kim hoặc Kim cương");
					} else {
						if (obj.getNhakhoaChk()) {
							throw new AgencyBusinessException("nhakhoaChk", ErrorCode.INVALID,
									"Sai quyen loi Nha khoa.Tuoi");
						}
						if (obj.getThaisanChk()) {
							throw new AgencyBusinessException("thaisanChk", ErrorCode.INVALID,
									"Người được BH nằm ngoài độ tuổi tham gia thai sản");
						}
					}
				}
			}
		}

		if (obj.getChuongTrinh().equals("4") || obj.getChuongTrinh().equals("5")) {
			if (tuoitinhphi >= 18 && tuoitinhphi <= 45) {
			} else {
				if (obj.getThaisanChk()) {
					throw new AgencyBusinessException("thaisanChk", ErrorCode.INVALID, "Người được BH nằm ngoài độ tuổi tham gia thai sản");
				}
			}
		}
		return tuoitinhphi;
	}

	// Valid Data Passed From Client
	private void validDataCalculate(PremiumBVPVM obj) throws AgencyBusinessException {
		log.debug("Request to validDataCalculate, PremiumBVPVM{} :", obj);
		if (!DateUtils.isValidDate(obj.getNgaySinh(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("ngaySinh", ErrorCode.FORMAT_DATE_INVALID);
		}
		if (!DateUtils.isValidDate(obj.getThoihanbhTu(), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("thoihanbhTu", ErrorCode.FORMAT_DATE_INVALID);
		}
	}

	private void validateDataPolicy(ProductBvpVM obj, AgencyDTO currentAgency, String loaidon) throws AgencyBusinessException {
		log.debug("Request to validateDataPolicy, ProductBvpVM{}, AgencyDTO{} :", obj, currentAgency);
		if (!DateUtils.isValidDate(DateUtils.date2Str(obj.getInceptionDate()), "dd/MM/yyy"))
			throw new AgencyBusinessException("inceptionDate", ErrorCode.INVALID);

		Date dateNow = new Date();
		if (!DateUtils.isValidDate(DateUtils.date2Str(obj.getInceptionDate()), "dd/MM/yyyy")) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.FORMAT_DATE_INVALID);
		} else if (obj.getInceptionDate().before(dateNow)) {
			throw new AgencyBusinessException("inceptionDate", ErrorCode.INVALID,
					"Ngày bắt đầu hiệu lực BH phải lớn hơn ngày hiện tại tối thiểu 1 ngày");
		}

		Date expiredDate = obj.getInceptionDate();
		expiredDate = DateUtils.addYear(expiredDate, 1);
		expiredDate = DateUtils.addDay(expiredDate, -1);
		obj.setExpiredDate(expiredDate);
		if (!DateUtils.isValidDate(DateUtils.date2Str(obj.getNguoidbhNgaysinh()), "dd/MM/yyy"))
			throw new AgencyBusinessException("nguoidbhNgaysinh", ErrorCode.INVALID);

		if (!obj.getChuongtrinhBh().equals("1") && !obj.getChuongtrinhBh().equals("2")
				&& !obj.getChuongtrinhBh().equals("3") && !obj.getChuongtrinhBh().equals("4")
				&& !obj.getChuongtrinhBh().equals("5"))
			throw new AgencyBusinessException("chuongtrinhBh", ErrorCode.INVALID);

		// HAS_EXTRACARE
		// = true: có tham gia quyền lợi bổ sung
		// - Nếu tham gia thì cần check các thông tin liên quan, có ít nhất tham gia 1
		// quyền lợi bổ sung
		// - Nếu không thì báo lỗi
		// = false: không tham gia
		if (obj.getHasExtracare()) {
			if (StringUtils.isEmpty(obj.getNgoaitru()) && StringUtils.isEmpty(obj.getTncn())
					&& StringUtils.isEmpty(obj.getSinhmang()) && StringUtils.isEmpty(obj.getNhakhoa())
					&& StringUtils.isEmpty(obj.getThaisan()))
				throw new AgencyBusinessException("invalidate", ErrorCode.NULL_OR_EMPTY,
						"Chưa có thông tin tham gia phí bảo hiểm với quyền lợi sung");
			else {
				if (!obj.getNgoaitru().equals("0") && !obj.getNgoaitru().equals("1"))
					throw new AgencyBusinessException("ngoaitru", ErrorCode.INVALID);

				if (!obj.getTncn().equals("0") && !obj.getTncn().equals("1"))
					throw new AgencyBusinessException("tncn", ErrorCode.INVALID);

				if (!obj.getSinhmang().equals("0") && !obj.getSinhmang().equals("1"))
					throw new AgencyBusinessException("sinhmang", ErrorCode.INVALID);

				if (!obj.getNhakhoa().equals("0") && !obj.getNhakhoa().equals("1"))
					throw new AgencyBusinessException("nhakhoa", ErrorCode.INVALID);

				if (!obj.getThaisan().equals("0") && !obj.getThaisan().equals("1"))
					throw new AgencyBusinessException("thaisan", ErrorCode.INVALID);
			}
		}

		// Nguoi thu huong
		if (obj.getHasNguoithuhuong()) {
			if (StringUtils.isEmpty(obj.getNguoithName()))
				throw new AgencyBusinessException("nguoithName", ErrorCode.NULL_OR_EMPTY);

			if (StringUtils.isEmpty(obj.getNguoithCmnd()))
				throw new AgencyBusinessException("nguoithCmnd", ErrorCode.NULL_OR_EMPTY);

			if (StringUtils.isEmpty(obj.getNguoithQuanhe()))
				throw new AgencyBusinessException("nguoithQuanhe", ErrorCode.NULL_OR_EMPTY);

			if (StringUtils.isEmpty(DateUtils.date2Str(obj.getNguoithNgaysinh())))
				throw new AgencyBusinessException("nguoithNgaysinh", ErrorCode.NULL_OR_EMPTY);
			else {
				if (!DateUtils.isValidDate(DateUtils.date2Str(obj.getNguoithNgaysinh()), "dd/MM/yyy"))
					throw new AgencyBusinessException("nguoithNgaysinh", ErrorCode.FORMAT_DATE_INVALID);
			}
		}

		if (obj.getHasNguoinhantien()) {
			if (StringUtils.isEmpty(obj.getNguoinhanName()))
				throw new AgencyBusinessException("nguoinhanName", ErrorCode.NULL_OR_EMPTY);

			if (StringUtils.isEmpty(obj.getNguoinhanCmnd()))
				throw new AgencyBusinessException("nguoinhanCmnd", ErrorCode.NULL_OR_EMPTY);

			if (StringUtils.isEmpty(obj.getNguoinhanQuanhe()))
				throw new AgencyBusinessException("nguoinhanQuanhe", ErrorCode.NULL_OR_EMPTY);

		}

		// TH tạo đơn bên adayroi thì ko check số tài khoản (loaidon = 1)
		if (loaidon.equals("0")) {
			validateInvoiceInfo(obj.getInvoiceInfo());	
		} else {
			if (obj.getInvoiceInfo() != null && StringUtils.equals(obj.getInvoiceInfo().getCheck(), "1")) {
				if (StringUtils.isEmpty(obj.getInvoiceInfo().getName()))
					throw new AgencyBusinessException("invoiceInfo.name", ErrorCode.NULL_OR_EMPTY,
							"invoiceBuyer (họ tên người mua) not null");

				if (StringUtils.isEmpty(obj.getInvoiceInfo().getCompany()))
					throw new AgencyBusinessException("invoiceInfo.company", ErrorCode.NULL_OR_EMPTY,
							"invoiceCompany (tên đơn vị) not null");

				if (StringUtils.isEmpty(obj.getInvoiceInfo().getTaxNo()))
					throw new AgencyBusinessException("invoiceInfo.taxNo", ErrorCode.NULL_OR_EMPTY,
							"invoiceTaxNo (mã số thuế) not null");

				if (StringUtils.isEmpty(obj.getInvoiceInfo().getAddress()))
					throw new AgencyBusinessException("invoiceInfo.address", ErrorCode.NULL_OR_EMPTY, "invoiceAddress not null");

			} else {
				if (obj.getInvoiceInfo() == null) {
					InvoiceInfoDTO invoiceInfo = new InvoiceInfoDTO();
					invoiceInfo.setCheck("0");
					obj.setInvoiceInfo(invoiceInfo);
				}
			}
		}
		

		if (!ValidateUtils.isPhone(obj.getReceiverUser().getMobile())) {
			throw new AgencyBusinessException("moible", ErrorCode.INVALID,
					"Số điện thoại người nhận không đúng định dạng");
		}

		// kiem tra thong tin khach hang
		if (StringUtils.isEmpty(obj.getContactCode()))
			throw new AgencyBusinessException("contactCode", ErrorCode.NULL_OR_EMPTY);

		if (StringUtils.isEmpty(currentAgency.getMa()))
			throw new AgencyBusinessException("type", ErrorCode.NULL_OR_EMPTY);
		// ==============End Data validate=================
		// ==============Logic validate====================
		PremiumBVPVM premiumBVPVM = new PremiumBVPVM();
		// set tam role agency
		// premiumBVPVM.p_AgencyRole = "agency";
		premiumBVPVM.setChuongTrinh(obj.getChuongtrinhBh());
		premiumBVPVM.setNgaySinh(DateUtils.date2Str(obj.getNguoidbhNgaysinh()));
		premiumBVPVM.setThoihanbhTu(DateUtils.date2Str(obj.getInceptionDate()));

		if (obj.getNgoaitru().equals("0"))
			premiumBVPVM.setNgoaitruChk(false);
		else
			premiumBVPVM.setNgoaitruChk(true);

		if (obj.getTncn().equals("0"))
			premiumBVPVM.setTncnChk(false);
		else {
			premiumBVPVM.setTncnChk(true);
			premiumBVPVM.setTncnSi(obj.getTncnSotienbh());
		}

		if (obj.getSinhmang().equals("0"))
			premiumBVPVM.setSmcnChk(false);
		else {
			premiumBVPVM.setSmcnChk(true);
			premiumBVPVM.setSmcnSi(obj.getSinhmangSotienbh());
		}

		if (obj.getNhakhoa().equals("0"))
			premiumBVPVM.setNhakhoaChk(false);
		else
			premiumBVPVM.setNhakhoaChk(true);

		if (obj.getThaisan().equals("0"))
			premiumBVPVM.setThaisanChk(false);
		else
			premiumBVPVM.setThaisanChk(true);

		premiumBVPVM.setThoihanbhTu(DateUtils.date2Str(obj.getInceptionDate()));
		premiumBVPVM.setNgaySinh(DateUtils.date2Str(obj.getNguoidbhNgaysinh()));
		premiumBVPVM.setChuongTrinh(obj.getChuongtrinhBh());
		premiumBVPVM.setNgoaitruChk(obj.getNgoaitru().equals("0") ? false : true);
		premiumBVPVM.setTncnChk(obj.getTncn().equals("0") ? false : true);
		premiumBVPVM.setTncnPhi(obj.getTncnPhi());
		premiumBVPVM.setSmcnChk(obj.getSinhmang().equals("0") ? false : true);
		premiumBVPVM.setSmcnPhi(obj.getSinhmangPhi());
		premiumBVPVM.setNhakhoaChk(obj.getNhakhoa().equals("0") ? false : true);
		premiumBVPVM.setThaisanChk(obj.getThaisan().equals("0") ? false : true);
		premiumBVPVM.setPremiumDiscount(obj.getDiscount());
		premiumBVPVM = calculatePremium(premiumBVPVM);

		// Hardcode NCB khoong check phi
		if (!currentAgency.getMa().equals("NCB")) {
			if (!premiumBVPVM.getQlChinhPhi().equals(obj.getChuongtrinhPhi()))
				throw new AgencyBusinessException("chuongtrinhPhi", ErrorCode.INVALID, "Sai phí chương trình chính "
						+ premiumBVPVM.getQlChinhPhi().toString() + " <> " + obj.getChuongtrinhPhi());

			if (!premiumBVPVM.getNgoaitruPhi().equals(obj.getNgoaitruPhi()))
				throw new AgencyBusinessException("ngoaitruPhi", ErrorCode.INVALID, "Sai phí quyền lợi ngoại trú "
						+ premiumBVPVM.getNgoaitruPhi().toString() + " <> " + obj.getNgoaitruPhi());

			if (!premiumBVPVM.getTncnPhi().equals(obj.getTncnPhi()))
				throw new AgencyBusinessException("tncnPhi", ErrorCode.INVALID,
						"Sai phí quyền lợi TNCN " + premiumBVPVM.getTncnPhi().toString() + " <> " + obj.getTncnPhi());

			if (!premiumBVPVM.getSmcnPhi().equals(obj.getSinhmangPhi()))
				throw new AgencyBusinessException("sinhmangPhi", ErrorCode.INVALID, "Sai phí quyền lợi SMCN "
						+ premiumBVPVM.getSmcnPhi().toString() + " <> " + obj.getSinhmangPhi());

			if (!premiumBVPVM.getNhakhoaPhi().equals(obj.getNhakhoaPhi()))
				throw new AgencyBusinessException("nhakhoaPhi", ErrorCode.INVALID, "Sai phí quyền lợi Nha khoa "
						+ premiumBVPVM.getNhakhoaPhi().toString() + " <> " + obj.getNhakhoaPhi());

			if (!premiumBVPVM.getThaisanPhi().equals(obj.getThaisanPhi()))
				throw new AgencyBusinessException("thaisanPhi", ErrorCode.INVALID, "Sai phí quyền lợi Thai sản "
						+ premiumBVPVM.getThaisanPhi().toString() + " <> " + obj.getThaisanPhi());
		}

		// kiểm tra logic nghiệp vụ
		// chưa hiểu đoạn này
		int tuoi = DateUtils.countYears(DateUtils.str2Date(DateUtils.date2Str(obj.getNguoidbhNgaysinh())),
				DateUtils.str2Date(DateUtils.date2Str(obj.getInceptionDate())));
		if (tuoi < 18) {
			if (StringUtils.isEmpty(obj.getPolicyParent())) {
				throw new AgencyBusinessException("policyParent", ErrorCode.NULL_OR_EMPTY, "Thiếu số HĐ bố/mẹ ");
			}

			// tam thoi chua kiem tra viec gui file
			if (StringUtils.isEmpty(obj.getFiles()))
				throw new AgencyBusinessException("files", ErrorCode.NULL_OR_EMPTY, "Thiếu file đính kèm ");
		}

		if (obj.getHasNguoithuhuong()) {
			if (StringUtils.isEmpty(obj.getNguoithName()))
				throw new AgencyBusinessException("nguoithName", ErrorCode.INVALID, "Thiếu tên người thụ hưởng");

			if (StringUtils.isEmpty(obj.getNguoithQuanhe()))
				throw new AgencyBusinessException("nguoithQuanhe", ErrorCode.INVALID, "Thiếu quan hệ người thụ hưởng ");
			else {
				if (obj.getNguoithQuanhe().equals("31") || obj.getNguoithQuanhe().equals("32")
						|| obj.getNguoithQuanhe().equals("33")) {
				} else
					throw new AgencyBusinessException("nguoithQuanhe", ErrorCode.INVALID,
							"Lỗi tham số quan hệ người thụ hưởng ");
			}

			if (StringUtils.isEmpty(obj.getNguoithCmnd()))
				throw new AgencyBusinessException("nguoithCmnd", ErrorCode.INVALID,
						"Thiếu số CMND/HC/GKS người thụ hưởng ");

			if (obj.getNguoithNgaysinh() == null
					|| DateUtils.str2Date(DateUtils.date2Str(obj.getNguoithNgaysinh())).after(new Date()))
				throw new AgencyBusinessException("nguoithNgaysinh", ErrorCode.INVALID,
						"Lỗi tham số Ngày sinh người thụ hưởng ");
		}

		if (obj.getHasNguoinhantien()) {
			if (StringUtils.isEmpty(obj.getNguoinhanName())) {
				throw new AgencyBusinessException("nguoinhanName", ErrorCode.INVALID, "Thiếu tên người nhận tiền ");
			}

			if (StringUtils.isEmpty(obj.getNguoinhanQuanhe())) {
				throw new AgencyBusinessException("nguoinhanQuanhe", ErrorCode.INVALID,
						"Thiếu quan hệ người nhận tiền ");
			} else {
				if (obj.getNguoinhanQuanhe().equals("31") || obj.getNguoinhanQuanhe().equals("32")
						|| obj.getNguoinhanQuanhe().equals("33")) {

				} else {
					throw new AgencyBusinessException("nguoinhanQuanhe", ErrorCode.INVALID,
							"Lỗi tham số quan hệ người nhận tiền ");
				}
			}

			if (StringUtils.isEmpty(obj.getNguoinhanCmnd())) {
				throw new AgencyBusinessException("nguoinhanCmnd", ErrorCode.INVALID,
						"Thiếu số CMND/HC/GKS người nhận tiền ");
			}

			if (obj.getNguointNgaysinh() == null
					|| (DateUtils.str2Date(DateUtils.date2Str(obj.getNguointNgaysinh())).after(new Date()))) {
				throw new AgencyBusinessException("nguointNgaysinh", ErrorCode.INVALID,
						"Lỗi tham số Ngày sinh người nhận tiền ");
			} else {
				// thuc hien kiem tra tuoi xem > 18
				int tuoi_nt = DateUtils.countYears(DateUtils.str2Date(DateUtils.date2Str(obj.getNguointNgaysinh())),
						DateUtils.str2Date(DateUtils.date2Str(obj.getInceptionDate())));
				if (tuoi_nt < 18)
					throw new AgencyBusinessException("nguointNgaysinh", ErrorCode.INVALID,
							"Người nhận tiền chưa đủ 18 tuổi ");
			}
		}

		// tinh trang sk
		// bool chksk = true;
		if (obj.getQ1().equals("0") && obj.getQ2().equals("0") && obj.getQ3().equals("0")) {

		} else {
			if (obj.getLstAdd().size() < 0)
				throw new AgencyBusinessException("lstAdd", ErrorCode.NULL_OR_EMPTY,
						"Thiếu thông tin tình trạng sức khỏe ");
			else {
				if (StringUtils.equals(obj.getQ3(), "1")) {
					if (obj.getLstAdd().get(0).getNgaydieutri() != null && StringUtils.isEmpty(DateUtils.date2Str(obj.getLstAdd().get(0).getNgaydieutri()))) {
						if (DateUtils.str2Date(DateUtils.date2Str(obj.getLstAdd().get(0).getNgaydieutri())).after(new Date()))
							throw new AgencyBusinessException("ngaydieutri", ErrorCode.INVALID,
									"Ngày khám/điều trị không được lớn hơn ngày hiện tại ");
					}

					if (StringUtils.isEmpty(obj.getLstAdd().get(0).getChuandoan()))
						throw new AgencyBusinessException("chuandoan", ErrorCode.NULL_OR_EMPTY,
								"Thiếu thông tin chuẩn đoán");

					if (StringUtils.isEmpty(obj.getLstAdd().get(0).getChitietdieutri()))
						throw new AgencyBusinessException("chitietdieutri", ErrorCode.NULL_OR_EMPTY,
								"Thiếu thông tin chi tiết điều trị");

					if (StringUtils.isEmpty(obj.getLstAdd().get(0).getKetqua())) {
						throw new AgencyBusinessException("ketqua", ErrorCode.NULL_OR_EMPTY,
								"Thiếu thông tin kết quả điều trị");
					}
				}
			}
		}

		// ==============End logic validate================
	}
}
