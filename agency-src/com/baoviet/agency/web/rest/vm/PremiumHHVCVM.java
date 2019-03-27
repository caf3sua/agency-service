package com.baoviet.agency.web.rest.vm;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.baoviet.agency.utils.DoubleSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PremiumHHVCVM {

	// Hàng điện tử, các thiết bị điện tử, máy vi tính
	@NotNull
	@ApiModelProperty(value = "Loại hàng hóa", required = true)
    private int loaiHangHoa;

    // Máy và thiết bị điện; máy ghi và sao âm thanh, máy ghi và sao hình ảnh truyền hình và âm thanh
	@NotNull
	@ApiModelProperty(value = "Tên hàng hóa", required = true)
    private int tenHangHoa;

    // Điều khoản bảo hiểm hàng hóa ( C ) ngày 1.1.1982 ( ICC-1982 )
	@NotNull
	@ApiModelProperty(value = "Điều khoản bảo hiểm", required = true)
    private int dieuKhoanBaoHiem;

    // Hàng chở rời
	@NotNull
	@ApiModelProperty(value = "Phương thức đóng gói", required = true)
    private int phuongThucDongGoi;

    // Dưới 500km
	@NotNull
	@ApiModelProperty(value = "Hành trình vận chuyển", required = true)
    private int hanhTrinhVanChuyen;

    // Tàu biển
	@NotNull
	@ApiModelProperty(value = "Phương tiện vận chuyển", required = true)
    private int phuongTienVanChuyen;

    // Kh tự nhập
	@NotNull
	@ApiModelProperty(value = "Giá trị hàng", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double giaTriHang;

    // Mặc định chưa tính toán đến đang để = 0
	@NotNull
	@ApiModelProperty(value = "Cước phí", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double cuocPhi;

	@NotNull
	@ApiModelProperty(value = "Phương thức thanh toán", required = true)
    private int phuongThucThanhToan; // 1 co bao gom cuoc phi, 2 khong bao gom cuoc phi

	@NotNull
	@ApiModelProperty(value = "Lãi ước tính", required = true)
    private Boolean laiUocTinh; // 10 % lai uoc tinh, true = co, false = khong

	@NotEmpty
	@ApiModelProperty(value = "Loại tiền tệ", required = true)
    private String loaiTienTe; // VND, USD
    
	private String loaiTienTeHangHoa; // VND, USD

	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm chưa giảm", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumNet;

	@NotNull
	@ApiModelProperty(value = "Phần trăm giảm phí", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumDiscount;

	@NotNull
	@ApiModelProperty(value = "Phí bảo hiểm sau khi giảm", required = true)
	@JsonSerialize(using = DoubleSerializer.class)
    private Double premiumHHVC;

	@Override
	public String toString() {
		return "PremiumHHVCVM [loaiHangHoa=" + loaiHangHoa + ", tenHangHoa=" + tenHangHoa + ", dieuKhoanBaoHiem="
				+ dieuKhoanBaoHiem + ", phuongThucDongGoi=" + phuongThucDongGoi + ", hanhTrinhVanChuyen="
				+ hanhTrinhVanChuyen + ", phuongTienVanChuyen=" + phuongTienVanChuyen + ", giaTriHang=" + giaTriHang
				+ ", cuocPhi=" + cuocPhi + ", phuongThucThanhToan=" + phuongThucThanhToan + ", laiUocTinh=" + laiUocTinh
				+ ", loaiTienTe=" + loaiTienTe + ", premiumNet=" + premiumNet + ", premiumDiscount=" + premiumDiscount
				+ ", premiumHHVC=" + premiumHHVC + "]";
	}
    
}