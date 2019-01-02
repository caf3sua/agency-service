package com.baoviet.agency.domain;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * The persistent class for the AGENCY database table.
 * 
 */
@Entity
@Table(name = "agency")
@Getter
@Setter
public class Agency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    @Column(name="ID", unique=true)
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "AGENCY_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	private String id;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(
      name = "AGENT_AGENCY_ROLE",
      joinColumns = {@JoinColumn(name = "agency_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "role_name", referencedColumnName = "name")})
	@BatchSize(size = 20)
	private Set<Role> roles = new HashSet<>();

	@Column(name="ADDR_CODE")
	private String addrCode;

	@Column(name="AGENCY_P1_ID")
	private String agencyP1Id;

	@Column(name="AGENCY_P1_NAME")
	private String agencyP1Name;

	@Column(name="AGENCY_P2_ID")
	private String agencyP2Id;

	@Column(name="AGENCY_P2_NAME")
	private String agencyP2Name;

	@Column(name="AGENCY_P3_ID")
	private String agencyP3Id;

	@Column(name="AGENCY_P3_NAME")
	private String agencyP3Name;

	@Column(name="AGENCY_P4_ID")
	private String agencyP4Id;

	@Column(name="AGENCY_P4_NAME")
	private String agencyP4Name;

	@Column(name="AGENCY_P5_ID")
	private String agencyP5Id;

	@Column(name="AGENCY_P5_NAME")
	private String agencyP5Name;

	@Column(name="AGENCY_P6_ID")
	private String agencyP6Id;

	@Column(name="AGENCY_P6_NAME")
	private String agencyP6Name;

	@Column(name="CHI_CUC_THUE")
	private String chiCucThue;

	@Column(name="CREATED_BY")
	private String createdBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATED_DATE")
	private Date createdDate;

	@Column
	private String cvdl;

	@Column(name="DIA_CHI_CHI_TIET")
	private String diaChiChiTiet;

	@Column(name="DIEN_THOAI")
	private String dienThoai;

	@Column(name="DIEN_THOAI_CQ")
	private String dienThoaiCq;

	@Column(name="DIEN_THOAI_NR")
	private String dienThoaiNr;

	@Column
	private String dlgt;

	@Column
	private String email;

	@Column(name="GIOI_TINH")
	private String gioiTinh;

	@Column
	private String htlv;

	@Column(name="ID_OLD")
	private String idOld;

	@Column(name="KENH_PHAN_PHOI")
	private String kenhPhanPhoi;

	@Column(name="KINH_NGHIEM")
	private String kinhNghiem;

	@Column(name="LOAI_DAI_LY")
	private String loaiDaiLy;

	@Column(name="LOAI_DIA_CHI")
	private String loaiDiaChi;

	@Column(name="LOAI_GT")
	private String loaiGt;

	@Column(name="LOAI_TK")
	private String loaiTk;

	@Column(name="LY_DO_BN")
	private String lyDoBn;

	@Column(name="LY_DO_STATUS")
	private String lyDoStatus;

	@Column
	private String ma;

	@Column(name="MA_BAN_NT")
	private String maBanNt;

	@Column(name="MA_CBKT")
	private String maCbkt;

	@Column(name="MA_CBQL")
	private String maCbql;

	@Column(name="MA_DL_NT")
	private String maDlNt;

	@Column(name="MA_DON_VI")
	private String maDonVi;

	@Column(name="MA_NHOM_NT")
	private String maNhomNt;

	@Column(name="MA_SO_THUE")
	private String maSoThue;

	@Column
	private String matkhau;

	@Column(name="MODIFIED_BY")
	private String modifiedBy;

	@Temporal(TemporalType.DATE)
	@Column(name="MODIFIED_DATE")
	private Date modifiedDate;

	@Column(name="NGAN_HANG")
	private String nganHang;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_BO_NHIEM")
	private Date ngayBoNhiem;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_CAP")
	private Date ngayCap;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_CAP_CC")
	private Date ngayCapCc;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_CAP_CC_CD")
	private Date ngayCapCcCd;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_CAP_CC_NC")
	private Date ngayCapCcNc;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_CAP_CN_CB")
	private Date ngayCapCnCb;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_CAP_CN_SP")
	private Date ngayCapCnSp;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_CAP_MST")
	private Date ngayCapMst;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_HL_CC")
	private Date ngayHlCc;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_HL_CC_CD")
	private Date ngayHlCcCd;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_HL_CC_NC")
	private Date ngayHlCcNc;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_HL_CN_CB")
	private Date ngayHlCnCb;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_HL_CN_SP")
	private Date ngayHlCnSp;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_HL_HDDL")
	private Date ngayHlHddl;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_KT_CC")
	private Date ngayKtCc;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_KT_CC_CD")
	private Date ngayKtCcCd;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_KT_CC_NC")
	private Date ngayKtCcNc;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_KT_CN_CB")
	private Date ngayKtCnCb;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_KT_CN_SP")
	private Date ngayKtCnSp;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_KY_HDDL")
	private Date ngayKyHddl;

	@Temporal(TemporalType.DATE)
	@Column(name="NGAY_SINH")
	private Date ngaySinh;

	@Column(name="NGHE_NGHIEP")
	private String ngheNghiep;

	@Column(name="NGUON_TD")
	private String nguonTd;

	@Column(name="NOI_CAP")
	private String noiCap;

	@Column(name="NOI_SINH")
	private String noiSinh;

	@Column
	private String pttt;

	@Column(name="QT_LV")
	private String qtLv;

	@Column(name="QUAN_HUYEN")
	private String quanHuyen;

	@Column(name="QUOC_GIA")
	private String quocGia;

	@Column(name="ROLE_ID")
	private String roleId;

	@Column(name="ROLE_NAME")
	private String roleName;

	@Column(name="SO_CC")
	private String soCc;

	@Column(name="SO_CC_CD")
	private String soCcCd;

	@Column(name="SO_CC_NC")
	private String soCcNc;

	@Column(name="SO_CN_CB")
	private String soCnCb;

	@Column(name="SO_CN_SP")
	private String soCnSp;

	@Column(name="SO_FAX")
	private String soFax;

	@Column(name="SO_GT")
	private String soGt;

	@Column(name="SO_HDDL")
	private String soHddl;

	@Column(name="SO_THE_DL")
	private String soTheDl;

	@Column(name="SO_TK")
	private String soTk;

	@Column
	private String status;

	@Column
	private String tdhv;

	@Column
	private String ten;

	@Column(name="TEN_CBKT")
	private String tenCbkt;

	@Column(name="TEN_CBQL")
	private String tenCbql;

	@Column(name="TEN_CHU_TK")
	private String tenChuTk;

	@Column(name="TEN_THOA_THUAN")
	private String tenThoaThuan;

	@Column(name="TINH_THANH")
	private String tinhThanh;

	@Column
	private String token;

	@Column
	private String ttgd;

	@Column(name="TVV_TD")
	private String tvvTd;

	@Column
	private String urn;

	@Column(name="XA_PHUONG")
	private String xaPhuong;
	
	@Column(name="SEND_OTP")
	private Integer sendOtp;
	
	@Column
	private Integer paymentMethod;
	
	@Column(name="BAOVIET_WORKPLACE")
	private String baovietWorkplace;
}