package com.diviso.graeshoppe.client.activiti.custom;

/**
 * @author sooraj
 *
 */
public class InitiateCancelation {
	
	String oderId;
	String paymentId;
	String refundId;
	String customerMail;
	String shopMail;
	Boolean isCredited;
	Long customerPhone;
	String phoneCode;
	
	
	public Long getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(Long customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getPhoneCode() {
		return phoneCode;
	}
	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
	public String getOderId() {
		return oderId;
	}
	public void setOderId(String oderId) {
		this.oderId = oderId;
	}
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getCustomerMail() {
		return customerMail;
	}
	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}
	public String getShopMail() {
		return shopMail;
	}
	public void setShopMail(String shopMail) {
		this.shopMail = shopMail;
	}
	public Boolean getIsCredited() {
		return isCredited;
	}
	public void setIsCredited(Boolean isCredited) {
		this.isCredited = isCredited;
	}


}
