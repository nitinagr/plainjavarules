package org.pjr.rulesengine;

import java.util.Date;

public class TestModel {

	

	/** The appointment id. */
	private String appointmentID;
	
	/** The appointment date. */
	private Date appointmentDate;
	
	/** The user id. */
	private String userID;
	
	/** The corp id. */
	private int corpID;
	
	/** The corp seq. */
	private int corpSeq;
	
	private String accountNo;
	/** The appointment status. */
	private String appointmentStatus;
	
	/** The appointment type. */
	private String appointmentType;
	
	/** The appointment duration. */
	private String appointmentDuration;
	
	/** The appointment preference. */
	private String appointmentPreference;
	
	/** The work points. */
	private String workPoints;
	
	/** The sequence. */
	private int sequence;
	
	/** The window. */
	private int window;
	
	/** The window position. */
	private int windowPosition;
	
	/** The planned start time. */
	private String plannedStartTime;
	
	/** The planned end time. */
	private String plannedEndTime;
	
	/** The schedule start time. */
	private String scheduleStartTime;
	
	/** The schedule end time. */
	private String scheduleEndTime;
	
	/** The delivery start time. */
	private String deliveryStartTime;
	
	/** The delivery end time. */
	private String deliveryEndTime;
	
	/** The appointment no. */
	private String appointmentNo;
	
	/** The transaction counter. */
	private String transactionCounter;
	
	/** The dslot. */
	private int dslot;
	
	/** The dseq. */
	private String dseq;
	
	/** The house no. */
	private String houseNo;
	
	/** The customer no. */
	private String customerNo;
	
	/** The customer type. */
	private String customerType;
	
	/** The customer title. */
	private String customerTitle;
	
	/** The customer first name. */
	private String customerFirstName;
	
	/** The customer last name. */
	private String customerLastName;
	
	/** The address line1. */
	private String addressLine1;
	
	/** The address line2. */
	private String addressLine2;
	
	/** The apt designator. */
	private String aptDesignator;
	
	/** The apt number. */
	private String aptNumber;
	
	/** The city. */
	private String city;
	
	/** The state. */
	private String state;
	
	/** The zip code. */
	private String zipCode;
	
	/** The email id. */
	private String emailID;
	
	/** The secondary email id. */
	private String secondaryEmailID;
	
	/** The residence phone. */
	private String residencePhone;
	
	/** The business phone. */
	private String businessPhone;
	
	/** The job identifier. */
	private String jobIdentifier;
	
	/** The planned arrival time. */
	private String plannedArrivalTime;
	
	private String plannedArrivalTimeRange;
	
	//Action taken on the previous appointment
	private String appointmentActionType;
	
	private String communicationTypeId;
	
	//Rule return Value
	private String communicationType;
	
	
	//Out of process Communication
	private boolean impactByDelayAction;
	
	private boolean impactByNewOrderAction;
	
	private String delayInMinutes;	
		
	//populate the following data
	private String triggerType;
	private String[] appointmentCommunicated;
	private int noOfCommunicationsSent;	
	private boolean servAssureData;
	
	//Travel Time calculation
	// based on ETA=false, Google call=true
	//Exclusive for timeline
	private Boolean travelTime;

	public String getAppointmentID() {
		return appointmentID;
	}

	public void setAppointmentID(String appointmentID) {
		this.appointmentID = appointmentID;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public int getCorpID() {
		return corpID;
	}

	public void setCorpID(int corpID) {
		this.corpID = corpID;
	}

	public int getCorpSeq() {
		return corpSeq;
	}

	public void setCorpSeq(int corpSeq) {
		this.corpSeq = corpSeq;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(String appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}

	public String getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(String appointmentType) {
		this.appointmentType = appointmentType;
	}

	public String getAppointmentDuration() {
		return appointmentDuration;
	}

	public void setAppointmentDuration(String appointmentDuration) {
		this.appointmentDuration = appointmentDuration;
	}

	public String getAppointmentPreference() {
		return appointmentPreference;
	}

	public void setAppointmentPreference(String appointmentPreference) {
		this.appointmentPreference = appointmentPreference;
	}

	public String getWorkPoints() {
		return workPoints;
	}

	public void setWorkPoints(String workPoints) {
		this.workPoints = workPoints;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public int getWindow() {
		return window;
	}

	public void setWindow(int window) {
		this.window = window;
	}

	public int getWindowPosition() {
		return windowPosition;
	}

	public void setWindowPosition(int windowPosition) {
		this.windowPosition = windowPosition;
	}

	public String getPlannedStartTime() {
		return plannedStartTime;
	}

	public void setPlannedStartTime(String plannedStartTime) {
		this.plannedStartTime = plannedStartTime;
	}

	public String getPlannedEndTime() {
		return plannedEndTime;
	}

	public void setPlannedEndTime(String plannedEndTime) {
		this.plannedEndTime = plannedEndTime;
	}

	public String getScheduleStartTime() {
		return scheduleStartTime;
	}

	public void setScheduleStartTime(String scheduleStartTime) {
		this.scheduleStartTime = scheduleStartTime;
	}

	public String getScheduleEndTime() {
		return scheduleEndTime;
	}

	public void setScheduleEndTime(String scheduleEndTime) {
		this.scheduleEndTime = scheduleEndTime;
	}

	public String getDeliveryStartTime() {
		return deliveryStartTime;
	}

	public void setDeliveryStartTime(String deliveryStartTime) {
		this.deliveryStartTime = deliveryStartTime;
	}

	public String getDeliveryEndTime() {
		return deliveryEndTime;
	}

	public void setDeliveryEndTime(String deliveryEndTime) {
		this.deliveryEndTime = deliveryEndTime;
	}

	public String getAppointmentNo() {
		return appointmentNo;
	}

	public void setAppointmentNo(String appointmentNo) {
		this.appointmentNo = appointmentNo;
	}

	public String getTransactionCounter() {
		return transactionCounter;
	}

	public void setTransactionCounter(String transactionCounter) {
		this.transactionCounter = transactionCounter;
	}

	public int getDslot() {
		return dslot;
	}

	public void setDslot(int dslot) {
		this.dslot = dslot;
	}

	public String getDseq() {
		return dseq;
	}

	public void setDseq(String dseq) {
		this.dseq = dseq;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getCustomerTitle() {
		return customerTitle;
	}

	public void setCustomerTitle(String customerTitle) {
		this.customerTitle = customerTitle;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getAptDesignator() {
		return aptDesignator;
	}

	public void setAptDesignator(String aptDesignator) {
		this.aptDesignator = aptDesignator;
	}

	public String getAptNumber() {
		return aptNumber;
	}

	public void setAptNumber(String aptNumber) {
		this.aptNumber = aptNumber;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getSecondaryEmailID() {
		return secondaryEmailID;
	}

	public void setSecondaryEmailID(String secondaryEmailID) {
		this.secondaryEmailID = secondaryEmailID;
	}

	public String getResidencePhone() {
		return residencePhone;
	}

	public void setResidencePhone(String residencePhone) {
		this.residencePhone = residencePhone;
	}

	public String getBusinessPhone() {
		return businessPhone;
	}

	public void setBusinessPhone(String businessPhone) {
		this.businessPhone = businessPhone;
	}

	public String getJobIdentifier() {
		return jobIdentifier;
	}

	public void setJobIdentifier(String jobIdentifier) {
		this.jobIdentifier = jobIdentifier;
	}

	public String getPlannedArrivalTime() {
		return plannedArrivalTime;
	}

	public void setPlannedArrivalTime(String plannedArrivalTime) {
		this.plannedArrivalTime = plannedArrivalTime;
	}

	public String getPlannedArrivalTimeRange() {
		return plannedArrivalTimeRange;
	}

	public void setPlannedArrivalTimeRange(String plannedArrivalTimeRange) {
		this.plannedArrivalTimeRange = plannedArrivalTimeRange;
	}

	public String getAppointmentActionType() {
		return appointmentActionType;
	}

	public void setAppointmentActionType(String appointmentActionType) {
		this.appointmentActionType = appointmentActionType;
	}

	public String getCommunicationTypeId() {
		return communicationTypeId;
	}

	public void setCommunicationTypeId(String communicationTypeId) {
		this.communicationTypeId = communicationTypeId;
	}

	public String getCommunicationType() {
		return communicationType;
	}

	public void setCommunicationType(String communicationType) {
		this.communicationType = communicationType;
	}

	public boolean isImpactByDelayAction() {
		return impactByDelayAction;
	}

	public void setImpactByDelayAction(boolean impactByDelayAction) {
		this.impactByDelayAction = impactByDelayAction;
	}

	public boolean isImpactByNewOrderAction() {
		return impactByNewOrderAction;
	}

	public void setImpactByNewOrderAction(boolean impactByNewOrderAction) {
		this.impactByNewOrderAction = impactByNewOrderAction;
	}

	public String getDelayInMinutes() {
		return delayInMinutes;
	}

	public void setDelayInMinutes(String delayInMinutes) {
		this.delayInMinutes = delayInMinutes;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

	public String[] getAppointmentCommunicated() {
		return appointmentCommunicated;
	}

	public void setAppointmentCommunicated(String[] appointmentCommunicated) {
		this.appointmentCommunicated = appointmentCommunicated;
	}

	public int getNoOfCommunicationsSent() {
		return noOfCommunicationsSent;
	}

	public void setNoOfCommunicationsSent(int noOfCommunicationsSent) {
		this.noOfCommunicationsSent = noOfCommunicationsSent;
	}

	public boolean isServAssureData() {
		return servAssureData;
	}

	public void setServAssureData(boolean servAssureData) {
		this.servAssureData = servAssureData;
	}

	public Boolean getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(Boolean travelTime) {
		this.travelTime = travelTime;
	}
	
	
	
}
