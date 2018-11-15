package com.baoviet.agency.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "AGENT_TMP_MOMO_CAR")
@Getter
@Setter
public class TmpMomoCar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "STRING_SEQUENCE_GENERATOR", strategy = "com.baoviet.agency.utils.StringSequenceGenerator", parameters = { @Parameter(name = "sequence", value = "CARS_SEQ") })
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STRING_SEQUENCE_GENERATOR")
	@Column(name="ID", unique=true)
	private String Id;
	
	@Column
	private String requestId;
	
	@Column
	private Date createdDate;
	
	// car base VM
	@Column
	public Long actualValue;

	@Column
	public Double changePremium;

	@Column
	public String chassisNumber;

	@Column
	public String engineNumber;

	@Column
	public Integer garageCheck;

	@Column
	public String insuredAddress;

	@Column
	public String insuredName;
	
	@Column
	private String invoiceCheck; // get; set; }

	@Column
	private String invoiceName; // get; set; }

	@Column
	private String invoiceCompany; // get; set; }

	@Column
	private String invoiceTaxNo; // get; set; }

	@Column
	private String invoiceAddress; // get; set; }

	@Column
	private String invoiceAccountNo; // get; set; }

	@Column(name="KHAU_HAO_CHECK")
	public Integer khauHaoCheck;
	
	@Column(name="KHAU_TRU_CHECK")
	public Integer khauTruCheck;
	
	@Column
	public String makeId;
	
	@Column
	public String makeName;
	
	@Column(name="MAT_CAP_CHECK")
	public Integer matCapCheck;
	
	@Column
	public String modelId;
	
	@Column
	public String modelName;

	@Column
	public Integer nntxCheck;

	@Column(name="NGAP_NUOC_CHECK")
	public Integer ngapNuocCheck;

	@Column(name="PASSENGER_ACCIDENT_NUMBER")
	public Integer passengersAccidentNumber;

	@Column(name="PASSENGER_ACCIDENT_PREMIUM")
	public Double passengersAccidentPremium;

	@Column(name="PASSENGER_ACCIDENT_SI")
	public Double passengersAccidentSi;

	@Column
	public String policyNumber;

	@Column
	public Double premium;

	@Column
	public String purposeOfUsageId;

	@Column
	public Long physicalDamagePremium;
	
	@Column
	public Long physicalDamageSi;

	@Column
	private String receiverName; // get; set; } 
	
	@Column
	private String receiverAddress; // get; set; } 
	
	@Column
	private String receiverEmail ; // get; set; } 
	
	@Column
	private String receiverMobile; // get; set; }  
	
	@Column
    private String receiverAddressDistrict; // get; set; }

	@Column
	public String registrationNumber;

	@Column
	public String tndsSocho;

	@Column
	public Integer tndsbbCheck;

	@Column
	public Integer tndstnCheck;

	@Column
	public Double tndstnPhi;

	@Column
	public Double tndstnSotien;

	@Column
	public Double totalPremium;

	@Column
	public Double thirdPartyPremium;

	@Column
	public String thoiHanDen;

	@Column
	public String thoiHanTu;

	@Column
	public Integer vcxCheck;

	@Column
	public String yearOfMake;
	
	@Column
    public String userAgent;
	
	// base
	@Column
    private String contactCode;
    
	@Column
    private String receiveMethod;

	@Column
    private String gycbhNumber;
	
	@Column(name="OLD_GYCBH_NUMBER")
    private String oldGycbhNumber;
	
	@Column
	private String contactName;
	
	@Column
	private String contactEmail;
	
	@Column
	private String contactAddress;
	
	@Column
	private String contactPhonenumber;
	
	@Column
	private String status;
	
	@Column
	private Double tndsbbPhi;
	
	@Column
	private Double vcxPhi;
	
	@Column
	private String checkTaituc;
}
