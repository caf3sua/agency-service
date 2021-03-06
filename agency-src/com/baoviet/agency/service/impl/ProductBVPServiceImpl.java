package com.baoviet.agency.service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.baoviet.agency.bean.FileContentDTO;
import com.baoviet.agency.bean.InvoiceInfoDTO;
import com.baoviet.agency.bean.InvoiceReceiverUserInfoXMLDTO;
import com.baoviet.agency.config.AgencyConstants;
import com.baoviet.agency.domain.Agency;
import com.baoviet.agency.domain.Contact;
import com.baoviet.agency.domain.SktdRate;
import com.baoviet.agency.domain.TinhtrangSk;
import com.baoviet.agency.dto.AgencyDTO;
import com.baoviet.agency.dto.AgreementDTO;
import com.baoviet.agency.dto.AttachmentDTO;
import com.baoviet.agency.dto.BvpDTO;
import com.baoviet.agency.dto.BvpFile;
import com.baoviet.agency.dto.BvpNdbhObj;
import com.baoviet.agency.dto.BvpNdbhXML;
import com.baoviet.agency.dto.Root;
import com.baoviet.agency.dto.TinhtrangSkDTO;
import com.baoviet.agency.exception.AgencyBusinessException;
import com.baoviet.agency.exception.ErrorCode;
import com.baoviet.agency.repository.AgencyRepository;
import com.baoviet.agency.repository.ContactRepository;
import com.baoviet.agency.repository.SktdRateRepository;
import com.baoviet.agency.repository.TinhtrangSkRepository;
import com.baoviet.agency.service.AgreementService;
import com.baoviet.agency.service.AttachmentService;
import com.baoviet.agency.service.BVPService;
import com.baoviet.agency.service.ProductBVPService;
import com.baoviet.agency.service.TinhtrangSkService;
import com.baoviet.agency.utils.AgencyUtils;
import com.baoviet.agency.utils.AppConstants;
import com.baoviet.agency.utils.DateUtils;
import com.baoviet.agency.utils.ValidateUtils;
import com.baoviet.agency.web.rest.vm.PremiumBVPVM;
import com.baoviet.agency.web.rest.vm.PremiumSKTDVM;
import com.baoviet.agency.web.rest.vm.ProductBvpVM;
import com.baoviet.agency.web.rest.vm.SKTDAddVM;

import doc.glite.DocGLitev2018Service;

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
	private BVPService bVPService;

	@Autowired
	private TinhtrangSkService tinhtrangSkService;

	@Autowired
	private ContactRepository contactRepository;

	@Autowired
	private TinhtrangSkRepository tinhtrangSkRepository;
	
	@Autowired
	private AttachmentService attachmentService;
	
	@Autowired
	private AgreementService agreementService;
	
	@Autowired
	private AgencyRepository agencyRepository;

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

		// comment 21/11/2018: lưu file vào attachment
//		if (!currentAgency.getMa().equals("NCB")) {
//			Integer tuoi = DateUtils.countYears(obj.getNguoidbhNgaysinh(), obj.getInceptionDate());
//			if (tuoi < 18) {
//				FilesDTO fileInfo = new FilesDTO();
//				SimpleDateFormat sdfr = new SimpleDateFormat("ddMMyyyHHmmss");
//				String fileName = "IMG_" + sdfr.format(new Date());
//				fileInfo.setName(fileName);
//				fileInfo.setType(".jpg");
//				fileInfo.setLength(obj.getFiles().length() * 1.0);
//				fileInfo.setLineId("BVP");
//				fileInfo.setLineName("SKTD");
//				fileInfo.setGycbhId("");
//				fileInfo.setTypeOfDocument(fileName);
//				fileInfo.setContentFile(obj.getFiles());
//				String fileId = filesService.save(fileInfo);
//				if (fileId != null) {
//					bvp.setQ1Id(fileId);
//				}
//			}
//		}
		
		// 21/11/2018: lưu file
		if (obj.getImgGks() != null && !StringUtils.isEmpty(obj.getImgGks().getContent())) {
			String fileId = saveFileContent(obj.getImgGks(), currentAgency, AgencyConstants.ATTACHMENT_GROUP_TYPE.ONLLINE_BVP);
			bvp.setQ1Id(fileId);
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
				if (StringUtils.equals(obj.getQ3(), "1") || StringUtils.equals(obj.getQ2(), "1") || StringUtils.equals(obj.getQ1(), "1")) {
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
						if (sk.getChuandoan() != null && !StringUtils.isEmpty(sk.getChuandoan())) {
							tinhtrangSkService.save(skInfo);
						}
					}
				}
				// check TH thêm mới: 0, update: 1 để gửi sms
//		        if (StringUtils.isEmpty(obj.getAgreementId())) {
		        	// pay_action
		         	sendSmsAndSavePayActionInfo(co, agreement, "0");	
//		        } else {
//		        	sendSmsAndSavePayActionInfo(co, agreement, "1");
//		        }
		        obj.setAgreementId(agreement.getAgreementId());
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
				// check TH thêm mới: 0, update: 1 để gửi sms
		        if (StringUtils.isEmpty(obj.getAgreementId())) {
		        	// pay_action
		         	sendSmsAndSavePayActionInfo(co, agreement, "0");	
		        } else {
		        	sendSmsAndSavePayActionInfo(co, agreement, "1");
		        }
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
	private String saveFileContent(FileContentDTO item, AgencyDTO currentAgency, String type) {
		AttachmentDTO attachmenInfo = new AttachmentDTO();
		if (!StringUtils.isEmpty(item.getFilename())) {
			attachmenInfo.setAttachmentName(item.getFilename());
		}
		if (!StringUtils.isEmpty(item.getContent())) {
			attachmenInfo.setContentFile(item.getContent());
		}
		if (!StringUtils.isEmpty(item.getFileType())) {
			attachmenInfo.setAttachmentType(item.getFileType());
		}
		attachmenInfo.setGroupType(type);
		attachmenInfo.setModifyDate(new Date());
		attachmenInfo.setTradeolSysdate(new Date());
		attachmenInfo.setUserId(currentAgency.getId());
		attachmenInfo.setIstransferred(0);
		String attachmentId = attachmentService.save(attachmenInfo);
		log.debug("Request to save Attachment, attachmentId{}", attachmentId);

		return attachmentId;
	}
	
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
		// 05/03/2019 start
		vo.setNguoiycCmnd(obj.getNguoiycCmnd());
		vo.setNguoiycDienthoai(co.getPhone());
		vo.setNguoiycDiachithuongtru(co.getHomeAddress());
		vo.setNguoiycEmail(co.getEmail());
		// 05/03/2019 end
		
		vo.setPolicyParent(obj.getPolicyParent()); // Số HĐBH/GCNBH/Thẻ/Mã đơn hàng Bố(Mẹ):
		vo.setNguoidbhName(obj.getNguoidbhName()); // Họ tên người được bảo hiểm:
		vo.setNguoidbhCmnd(obj.getNguoidbhCmnd()); // Số Chứng minh nhân dân/Hộ chiếu: NDBH
		vo.setNguoidbhNgaysinh(obj.getNguoidbhNgaysinh());
		vo.setNguoidbhQuanhe(obj.getNguoidbhQuanhe());// Quan hệ với người Yêu cầu bảo hiểm:
		
		vo.setNguoidbhDiachithuongtru(obj.getNguoidbhDiachi());
		vo.setNguoidbhGioitinh(obj.getNguoidbhGioitinh());

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
		// 05/03/2019 start
		vo.setNguoithDienthoai(obj.getNguoithDienthoai());
		vo.setNguoithDiachi(obj.getNguoithDiachi());
		vo.setNguoithEmail(obj.getNguoithEmail());
		// 05/03/2019 end
		
		vo.setNguoinhanName(obj.getNguoinhanName());// Họ tên người nhận tiền
		vo.setNguoinhanCmnd(obj.getNguoinhanCmnd());// Số Chứng minh nhân dân/Hộ chiếu
		vo.setNguoinhanQuanhe(obj.getNguoinhanQuanhe());// Quan hệ với người được bảo hiểm
		if (obj.getNguointNgaysinh() != null) {
			vo.setNguointNgaysinh(obj.getNguointNgaysinh());	
		}
		// 05/03/2019 start
		vo.setNguoinhanDienthoai(obj.getNguointDienthoai());
		vo.setNguoinhanDiachi(obj.getNguointDiachi());
		vo.setNguoinhanEmail(obj.getNguointEmail());
		// 05/03/2019 end
		
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
			if (StringUtils.isEmpty(obj.getImgGks().getContent()))
				throw new AgencyBusinessException("files", ErrorCode.NULL_OR_EMPTY, "Thiếu file đính kèm ");
		}

		if (obj.getHasNguoithuhuong()) {
			if (StringUtils.isEmpty(obj.getNguoithName()))
				throw new AgencyBusinessException("nguoithName", ErrorCode.INVALID, "Thiếu tên người thụ hưởng");

			if (StringUtils.isEmpty(obj.getNguoithQuanhe()))
				throw new AgencyBusinessException("nguoithQuanhe", ErrorCode.INVALID, "Thiếu quan hệ người thụ hưởng ");
			else {
				if (obj.getNguoithQuanhe().equals("30") || obj.getNguoithQuanhe().equals("31") || obj.getNguoithQuanhe().equals("32")
						|| obj.getNguoithQuanhe().equals("33") || obj.getNguoithQuanhe().equals("99")) {
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
				if (obj.getNguoinhanQuanhe().equals("30") || obj.getNguoinhanQuanhe().equals("31") || obj.getNguoinhanQuanhe().equals("32")
						|| obj.getNguoinhanQuanhe().equals("33") || obj.getNguoinhanQuanhe().equals("99")) {

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

	@Override
	public BvpFile downloadBVP(AgreementDTO agreement) throws AgencyBusinessException {
			if (agreement != null) {
				Root bvpXML = getInfoBVP(agreement);
				if (bvpXML != null) {
					try {
						JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
						Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
						jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						
						StringWriter sw = new StringWriter();
				        jaxbMarshaller.marshal(bvpXML, sw);
				        String xml = sw.toString();
				        
				        xml = xml.replace("root", "ROOT");
				        xml = xml.replace("<q", "<Q");
				        xml = xml.replace("</q", "</Q");
				        xml = xml.replace("&lt;", "<");
				        xml = xml.replace("&gt;", ">");
				        DocGLitev2018Service service = new DocGLitev2018Service();
				        String result = service.getDocGLitev2018Port().runReportV1("TOL", "BVP_01", "v1", "vi", xml);
				        
				        if (result != null && result.length() > 0) {
				        	BvpFile file = getByteFromResponse(result, "BVP_01");
				        	if (file != null) {
				        		return file;	
				        	} else {
				        		throw new AgencyBusinessException(ErrorCode.UNKNOW_ERROR, "Không tải được File");
				        	}
				        }
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;
			} else {
				throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không tìm thấy đơn hàng");
			}
	}
	
	@Override
	public BvpFile downloadBvpGYCBH(AgreementDTO agreement) throws AgencyBusinessException {
			if (agreement != null) {
				Root bvpXML = getInfoBVP(agreement);
				if (bvpXML != null) {
					try {
						JAXBContext jaxbContext = JAXBContext.newInstance(Root.class);
						Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
						jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
						
						StringWriter sw = new StringWriter();
				        jaxbMarshaller.marshal(bvpXML, sw);
				        String xml = sw.toString();
				        
				        xml = xml.replace("root", "ROOT");
				        xml = xml.replace("<q", "<Q");
				        xml = xml.replace("</q", "</Q");
				        xml = xml.replace("&lt;", "<");
				        xml = xml.replace("&gt;", ">");
				        
				        DocGLitev2018Service service = new DocGLitev2018Service();
				        String result = service.getDocGLitev2018Port().runReportV1("TOL", "BVP_02", "v1", "vi", xml);
				        
				        if (result != null) {
				        	System.out.println(result);
				        	BvpFile file = getByteFromResponse(result, "BVP_02");
				        	if (file != null) {
				        		return file;	
				        	} else {
				        		throw new AgencyBusinessException(ErrorCode.UNKNOW_ERROR, "Không tải được File");
				        	}
				        }
				        
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				return null;
			} else {
				throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không tìm thấy đơn hàng");
			}
	}
	
	private String formatDate (String date) {
		String strDate = "";
		if (StringUtils.isNotEmpty(date)) {
			String[] words = date.split("/");
			strDate = "Ngày " + words[0] + " Tháng " + words[1] + " Năm " + words[2];
		} else {
			strDate = "";
		}
		
		return strDate;
	}
	
	private Root getInfoBVP(AgreementDTO agreement) throws AgencyBusinessException {
		BvpDTO bvp = bVPService.getById(agreement.getGycbhId());
		if (bvp != null) {
			Root bvpXML = new Root();
			bvpXML.setPOLICY_NUMBER(agreement.getPolicyNumber());
			bvpXML.setNGUOIYC_NAME(bvp.getNguoiycName());
			bvpXML.setNGUOIYC_CMND(bvp.getNguoiycCmnd());
			bvpXML.setNGUOIYC_NGAYSINH(DateUtils.date2Str(bvp.getNguoiycNgaysinh()));
			bvpXML.setNGUOIYC_DIACHITHUONGTRU(converAddress(bvp.getNguoiycDiachithuongtru()));
			bvpXML.setNGUOIYC_DIENTHOAI(bvp.getNguoiycDienthoai());
			bvpXML.setNGUOIYC_EMAIL(bvp.getNguoiycEmail());
			bvpXML.setBAOVIET_DEPARTMENT_ID(agreement.getBaovietDepartmentId());
			bvpXML.setBAOVIET_DEPARTMENT_NAME(agreement.getBaovietDepartmentName());
			bvpXML.setBAOVIET_NAME(""); // hiện tại để trống
			bvpXML.setNGAY_HOA_DON(formatDate(DateUtils.date2Str(agreement.getDateOfPayment())));
			bvpXML.setURN_DAILY(agreement.getUrnDaily());
			bvpXML.setAGENT_NAME(agreement.getAgentName());
			bvpXML.setBAOVIET_COMPANY_NAME(agreement.getBaovietCompanyName());
			bvpXML.setCAN_BO_QLDV("");	 // hiện tại để trống
			
			InvoiceReceiverUserInfoXMLDTO receiverUser = new InvoiceReceiverUserInfoXMLDTO();
			receiverUser.setRECEIVER_NAME(agreement.getReceiverName());
			receiverUser.setRECEIVER_MOIBLE(agreement.getReceiverMoible());
			receiverUser.setRECEIVER_ADDRESS(converAddress(agreement.getReceiverAddress()));
			bvpXML.setRECEIVEINFO(receiverUser);
			
			InvoiceReceiverUserInfoXMLDTO invoice = new InvoiceReceiverUserInfoXMLDTO();
			invoice.setINVOICE_BUYER(agreement.getInvoiceBuyer());
			invoice.setINVOICE_COMPANY(agreement.getInvoiceCompany());
			invoice.setINVOICE_TAX_NO(agreement.getInvoiceTaxNo());
			invoice.setINVOICE_ADDRESS(converAddress(agreement.getInvoiceAddress()));
			bvpXML.setINVOICEINFO(invoice);
			
			Agency agency = agencyRepository.findByMa(agreement.getAgentId());
			String kenhPP = agency.getKenhPhanPhoi();
			if (StringUtils.equals(AgencyConstants.KENH_PHAN_PHOI_AGENCY, kenhPP)) {
				bvpXML.setKENH_PHAN_PHOI("Đại lý trực tiếp"); 
			} else {
				bvpXML.setKENH_PHAN_PHOI("Banca");	
			}
			
			if (StringUtils.isNotEmpty(agency.getDienThoai())) {
				bvpXML.setDIEN_THOAI(agency.getDienThoai());	
			} else {
				bvpXML.setDIEN_THOAI("");
			}
			
			bvpXML.setLOGO_BVGI("logo_bvgi.jpg");
			
			String logoDT = agency.getKenhPhanPhoi() + ".jpg";
			bvpXML.setLOGO_DOITAC(logoDT);
			
			BvpNdbhXML bvpNdbhXML = new BvpNdbhXML();
			bvpNdbhXML.setNGUOIDBH_NAME(bvp.getNguoidbhName());
			bvpNdbhXML.setNGUOIDBH_DIACHITHUONGTRU(converAddress(bvp.getNguoidbhDiachithuongtru()));
			bvpNdbhXML.setNGUOIDBH_NGAYSINH(DateUtils.date2Str(bvp.getNguoidbhNgaysinh()));
			bvpNdbhXML.setNGUOIDBH_CMND(bvp.getNguoidbhCmnd());
			bvpNdbhXML.setNGUOIDBH_QUANHE(getQuanHe(bvp.getNguoidbhQuanhe()));
			
//			if (StringUtils.isNotEmpty(bvp.getNguoidbhGioitinh())) {
//				bvpNdbhXML.setNGUOIDBH_GIOITINH(getGioitinh(bvp.getNguoidbhGioitinh()));
//			} else {
				bvpNdbhXML.setNGUOIDBH_GIOITINH("");
//			}
			
			bvpNdbhXML.setNGUOIDBH_DIENTHOAI(""); // hiện tại để trống item.NGUOITH_DIENTHOAI 
			bvpNdbhXML.setNGUOIDBH_EMAIL(""); // hiện tại để trống item.NGUOITH_EMAIL 
			bvpNdbhXML.setCHUONG_TRINH(getChuongTrinh(bvp.getChuongtrinhBh()));
			bvpNdbhXML.setSTBH_CTC(formatNumber(bvp.getTongphiPhi().longValue()));
			if (StringUtils.equals(bvp.getNgoaitru(), "1")) {
				bvpNdbhXML.setSTBH_QLBS_1("x");	// có tham gia
			} else {
				bvpNdbhXML.setSTBH_QLBS_1("-");
			}
			int tuoi = DateUtils.countYears(bvp.getNguoidbhNgaysinh(), agreement.getInceptionDate());
			if (tuoi < 3) {
				bvpNdbhXML.setMUCDONGCHITRA("70/30");
			} else {
				bvpNdbhXML.setMUCDONGCHITRA("0");	
			}
			
			if (StringUtils.equals(bvp.getTncn(), "1")) {
				bvpNdbhXML.setTNCN(formatNumber(bvp.getTncnSotienbh().longValue()));
//				bvpNdbhXML.setTNCN("x");	// có tham gia
			} else {
				bvpNdbhXML.setTNCN("-");
			}
			
			if (StringUtils.equals(bvp.getSinhmang(), "1")) {
				bvpNdbhXML.setSINHMANG_SOTIENBH(formatNumber(bvp.getSinhmangSotienbh().longValue()));
//				bvpNdbhXML.setSINHMANG_SOTIENBH("x");	// có tham gia
			} else {
				bvpNdbhXML.setSINHMANG_SOTIENBH("-");
			}
			
			if (StringUtils.equals(bvp.getNhakhoa(), "1")) {
				bvpNdbhXML.setSTBH_QLBS_4("x");	// có tham gia
			} else {
				bvpNdbhXML.setSTBH_QLBS_4("-");
			}
			if (StringUtils.equals(bvp.getThaisan(), "1")) {
				bvpNdbhXML.setSTBH_QLBS_TS("x");	// có tham gia
			} else {
				bvpNdbhXML.setSTBH_QLBS_TS("-");
			}
			
			bvpNdbhXML.setTHBH_TU(DateUtils.date2Str(agreement.getInceptionDate()));
			bvpNdbhXML.setTHBH_DEN(DateUtils.date2Str(agreement.getExpiredDate()));
			bvpNdbhXML.setTONGPHI_PHI(formatNumber(bvp.getTongphiPhi().longValue()));
			bvpNdbhXML.setTANGGIAM_PHI(formatNumber(bvp.getTanggiamPhi().longValue()));
			bvpNdbhXML.setTONG_PHI(formatNumber(bvp.getTongphiPhi().longValue()));
			bvpNdbhXML.setTONG_PHI_CHU(AgencyUtils.docso(bvp.getTongphiPhi()));
			bvpNdbhXML.setCHANGE_PREMIUM(formatNumber(bvp.getTanggiamPhi().longValue()));
			bvpNdbhXML.setDIEU_KHOAN_CHINH((formatNumber(bvp.getChuongtrinhPhi().longValue())));
			
			if (bvp.getNgoaitruPhi() != null && bvp.getNgoaitruPhi() > 0) {
				bvpNdbhXML.setCHITIETPHIBH_NGOAICHU(formatNumber(bvp.getNgoaitruPhi().longValue()));
			} else {
				bvpNdbhXML.setCHITIETPHIBH_NGOAICHU("0");
			}
			
			if (bvp.getTncnPhi() != null && bvp.getTncnPhi() > 0) {
				bvpNdbhXML.setCHITIETPHIBH_TAINAN(formatNumber(bvp.getTncnPhi().longValue()));
			} else {
				bvpNdbhXML.setCHITIETPHIBH_TAINAN("0");
			}
			
			if (bvp.getSinhmangPhi() != null && bvp.getSinhmangPhi() > 0) {
				bvpNdbhXML.setCHITIETPHIBH_SINHMANG(formatNumber(bvp.getSinhmangPhi().longValue()));
			} else {
				bvpNdbhXML.setCHITIETPHIBH_SINHMANG("0");
			}
			
			if (bvp.getNhakhoaPhi() != null && bvp.getNhakhoaPhi() > 0) {
				bvpNdbhXML.setCHITIETPHIBH_NHAKHOA(formatNumber(bvp.getNhakhoaPhi().longValue()));
			} else {
				bvpNdbhXML.setCHITIETPHIBH_NHAKHOA("0");
			}
			
			if (bvp.getThaisanPhi() != null && bvp.getThaisanPhi() > 0) {
				bvpNdbhXML.setCHITIETPHIBH_THAISAN(formatNumber(bvp.getThaisanPhi().longValue()));
			} else {
				bvpNdbhXML.setCHITIETPHIBH_THAISAN("0");
			}
			
			if (StringUtils.isNotEmpty(bvp.getNguoithName())) {
				bvpNdbhXML.setNGUOITH_NAME(bvp.getNguoithName());	
			} else {
				bvpNdbhXML.setNGUOITH_NAME("");
			}
			if (StringUtils.isNotEmpty(bvp.getNguoithCmnd())) {
				bvpNdbhXML.setNGUOITH_CMND(bvp.getNguoithCmnd());	
			} else {
				bvpNdbhXML.setNGUOITH_CMND("");
			}
			if (StringUtils.isNotEmpty(bvp.getNguoithQuanhe())) {
				bvpNdbhXML.setNGUOITH_QUANHE(getQuanHe(bvp.getNguoithQuanhe()));
			} else {
				bvpNdbhXML.setNGUOITH_QUANHE("");	
			}
			if (StringUtils.isNotEmpty(bvp.getNguoithDienthoai())) {
				bvpNdbhXML.setNGUOITH_DIENTHOAI(bvp.getNguoithDienthoai());	
			} else {
				bvpNdbhXML.setNGUOITH_DIENTHOAI("");
			}
			if (StringUtils.isNotEmpty(bvp.getNguoithEmail())) {
				bvpNdbhXML.setNGUOITH_EMAIL(bvp.getNguoithEmail());	
			} else {
				bvpNdbhXML.setNGUOITH_EMAIL("");
			}
			if (StringUtils.isNotEmpty(bvp.getNguoithDiachi())) {
				bvpNdbhXML.setNGUOITH_DIACHI(converAddress(bvp.getNguoithDiachi()));	
			} else {
				bvpNdbhXML.setNGUOITH_DIACHI("");
			}
			if (StringUtils.isNotEmpty(bvp.getNguoinhanName())) {
				bvpNdbhXML.setNGUOINHAN_NAME(bvp.getNguoinhanName());	
			} else {
				bvpNdbhXML.setNGUOINHAN_NAME("");
			}
			if (StringUtils.isNotEmpty(bvp.getNguoinhanCmnd())) {
				bvpNdbhXML.setNGUOINHAN_CMND(bvp.getNguoinhanCmnd());	
			} else {
				bvpNdbhXML.setNGUOINHAN_CMND("");
			}
			if (StringUtils.isNotEmpty(bvp.getNguoinhanQuanhe())) {
				bvpNdbhXML.setNGUOINHAN_QUANHE(getQuanHe(bvp.getNguoinhanQuanhe()));
			} else {
				bvpNdbhXML.setNGUOINHAN_QUANHE("");	
			}
			if (StringUtils.isNotEmpty(bvp.getNguoinhanDienthoai())) {
				bvpNdbhXML.setNGUOINHAN_DIENTHOAI(bvp.getNguoinhanDienthoai());
			} else {
				bvpNdbhXML.setNGUOINHAN_DIENTHOAI("");	
			}
			if (StringUtils.isNotEmpty(bvp.getNguoinhanEmail())) {
				bvpNdbhXML.setNGUOINHAN_EMAIL(bvp.getNguoinhanEmail());
			} else {
				bvpNdbhXML.setNGUOINHAN_EMAIL("");	
			}
			if (StringUtils.isNotEmpty(bvp.getNguoinhanDiachi())) {
				bvpNdbhXML.setNGUOINHAN_DIACHI(converAddress(bvp.getNguoinhanDiachi()));
			} else {
				bvpNdbhXML.setNGUOINHAN_DIACHI("");
			}
			bvpNdbhXML.setNGAYCAP(DateUtils.date2Str(agreement.getDateOfPayment()));
			bvpNdbhXML.setGHICHU(bvp.getGhichu().trim());
			bvpNdbhXML.setQ1(bvp.getQ1());
			bvpNdbhXML.setQ2(bvp.getQ2());
			bvpNdbhXML.setQ3(bvp.getQ3());
			
			BvpNdbhObj nguoidbh = new BvpNdbhObj();
			nguoidbh.setNGUOIDBH(bvpNdbhXML);
			
			List<BvpNdbhObj> LstNguoidbhCol = new ArrayList<>();
			LstNguoidbhCol.add(nguoidbh);
			
			bvpXML.setNGUOIDBH_COL(LstNguoidbhCol);
			
			List<TinhtrangSk> lstTinhtrangSk = tinhtrangSkRepository.findByIdThamchieuAndMasanpham(bvp.getId(), "BVP");
			if (lstTinhtrangSk != null && lstTinhtrangSk.size() > 0) {
				String strlstTinhtrangSk = "";
				for (TinhtrangSk tinhtrangSk : lstTinhtrangSk) {
					strlstTinhtrangSk += "<TINHTRANGSK>";
						if (tinhtrangSk.getNgaydieutri() != null) {
							strlstTinhtrangSk += "<NGAYKHAM>" + DateUtils.date2Str(tinhtrangSk.getNgaydieutri()) + "</NGAYKHAM>";	
						} else {
							strlstTinhtrangSk += "<NGAYKHAM></NGAYKHAM>";
						}
						
						strlstTinhtrangSk += "<CHUANDOAN>"+ tinhtrangSk.getChuandoan() + "</CHUANDOAN>";
						strlstTinhtrangSk += "<CHITIET>" + tinhtrangSk.getChitietdieutri() + "</CHITIET>";
						strlstTinhtrangSk += "<KETQUA>" + tinhtrangSk.getKetqua() + "</KETQUA>";
						if (StringUtils.isNotEmpty(tinhtrangSk.getBenhvienorbacsy())){
							strlstTinhtrangSk += "<BENHVIEN>" + tinhtrangSk.getBenhvienorbacsy() + "</BENHVIEN>";	
						} else {
							strlstTinhtrangSk += "<BENHVIEN></BENHVIEN>";
						}
						
					strlstTinhtrangSk += "</TINHTRANGSK>";
				}
				strlstTinhtrangSk += "";
				bvpXML.setTINHTRANGSK_LIST(strlstTinhtrangSk);
			}
			
			return bvpXML;
		} else {
			throw new AgencyBusinessException("gycbhNumber", ErrorCode.INVALID, "Không tìm thấy đơn hàng");
		}
	}
	
	private String formatNumber(Long number) {
        if (number < 1000) {
            return String.valueOf(number);
        }
        try {
            NumberFormat formatter = new DecimalFormat("###,###");
            String resp = formatter.format(number);
            resp = resp.replace(".", ",");
            return resp;
        } catch (Exception e) {
            return "";
        }
    }
	
	private String converAddress(String address) {
		if (StringUtils.isNotEmpty(address) && address.contains("::")) {
			String[] words = address.split("::");
            return words[0] + ", " + words[1];
		} else {
			return address;
		}
	}
	
	private String getQuanHe(String id) {
		String name = "";
		switch (id) {
			case "30":
				name = "Bản thân";
			break;
			case "31":
				name = "Vợ/Chồng";
			break;
			case "32":
				name = "Con";
			break;
			case "33":
				name = "Bố/Mẹ";
			break;
			case "34":
				name = "Bố mẹ của Vợ/Chồng";
			break;
			case "99":
				name = "Khác";
			break;
		default:
			break;
		}
		return name;
	}
	
	private String getChuongTrinh(String id) {
		String name = "";
		switch (id) {
			case "1":
				name = "Đồng";
			break;
			case "2":
				name = "Bạc";
			break;
			case "3":
				name = "Vàng";
			break;
			case "4":
				name = "Bạch Kim";
			break;
			case "5":
				name = "Kim Cương";
			break;
		default:
			break;
		}
		return name;
	}
	
	private String getGioitinh(String id) {
		String name = "";
		switch (id) {
			case "1":
				name = "Nam";
			break;
			case "2":
				name = "Nữ";
			break;
		default:
			break;
		}
		return name;
	}
	
	private BvpFile getByteFromResponse(String response, String type) {
		String xmlRecords = "<data><employee>"+response+"</employee></data>";

		DocumentBuilder db;
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlRecords));

			Document doc = db.parse(is);
			NodeList nodes = doc.getElementsByTagName("employee");

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);
				
				NodeList reportStatus = element.getElementsByTagName("reportStatus");
				Element line = (Element) reportStatus.item(0);
				String stauts = getCharacterDataFromElement(line);
				if (StringUtils.equals(stauts, "SUCC")) {
					NodeList reportBytes = element.getElementsByTagName("reportBytes");
					line = (Element) reportBytes.item(0);
					BvpFile file = new BvpFile();
					file.setContentStr(getCharacterDataFromElement(line));
					file.setContent(getCharacterDataFromElement(line).getBytes());
					file.setType("pdf");
					if (type.equals("BVP_01")) {
						file.setFileName("Hop_Dong_Bao_Hiem_An_Gia.pdf");	
					} else {
						file.setFileName("Giay_YCBH_An_Gia.pdf");
					}
					return file;
				}
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static String getCharacterDataFromElement(Element e) {
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData) {
	      CharacterData cd = (CharacterData) child;
	      return cd.getData();
	    }
	    return "";
	  }
}
