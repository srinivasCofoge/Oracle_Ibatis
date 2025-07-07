package cargo.cms.edi.ei.bo;

import java.io.Serializable;
import java.util.Date;

/**
 * HawbData Business Object representing House Air Waybill data.
 * This is a stub implementation containing the most common fields.
 * In a real implementation, this would contain 100+ fields as mentioned
 * in the requirements.
 * 
 * @author Generated BO
 * @version 1.0
 */
public class HawbData implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    // Core HAWB identification fields
    private String hawbNo;
    private Date hawbDate;
    private String origin;
    private String destination;
    private Integer pieces;
    private Double weight;
    
    // Shipper information
    private String shipperName;
    private String shipperAddress1;
    private String shipperAddress2;
    private String shipperCity;
    private String shipperCountry;
    private String shipperPhone;
    
    // Consignee information
    private String consigneeName;
    private String consigneeAddress1;
    private String consigneeAddress2;
    private String consigneeCity;
    private String consigneeCountry;
    private String consigneePhone;
    
    // Cargo information
    private String goodsDescription;
    private String specialHandling;
    
    // Additional fields would be added here in real implementation
    // to reach the 100+ fields mentioned in requirements
    
    // Default constructor
    public HawbData() {
    }
    
    // Getters and setters
    public String getHawbNo() {
        return hawbNo;
    }
    
    public void setHawbNo(String hawbNo) {
        this.hawbNo = hawbNo;
    }
    
    public Date getHawbDate() {
        return hawbDate;
    }
    
    public void setHawbDate(Date hawbDate) {
        this.hawbDate = hawbDate;
    }
    
    public String getOrigin() {
        return origin;
    }
    
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    
    public String getDestination() {
        return destination;
    }
    
    public void setDestination(String destination) {
        this.destination = destination;
    }
    
    public Integer getPieces() {
        return pieces;
    }
    
    public void setPieces(Integer pieces) {
        this.pieces = pieces;
    }
    
    public Double getWeight() {
        return weight;
    }
    
    public void setWeight(Double weight) {
        this.weight = weight;
    }
    
    public String getShipperName() {
        return shipperName;
    }
    
    public void setShipperName(String shipperName) {
        this.shipperName = shipperName;
    }
    
    public String getShipperAddress1() {
        return shipperAddress1;
    }
    
    public void setShipperAddress1(String shipperAddress1) {
        this.shipperAddress1 = shipperAddress1;
    }
    
    public String getShipperAddress2() {
        return shipperAddress2;
    }
    
    public void setShipperAddress2(String shipperAddress2) {
        this.shipperAddress2 = shipperAddress2;
    }
    
    public String getShipperCity() {
        return shipperCity;
    }
    
    public void setShipperCity(String shipperCity) {
        this.shipperCity = shipperCity;
    }
    
    public String getShipperCountry() {
        return shipperCountry;
    }
    
    public void setShipperCountry(String shipperCountry) {
        this.shipperCountry = shipperCountry;
    }
    
    public String getShipperPhone() {
        return shipperPhone;
    }
    
    public void setShipperPhone(String shipperPhone) {
        this.shipperPhone = shipperPhone;
    }
    
    public String getConsigneeName() {
        return consigneeName;
    }
    
    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }
    
    public String getConsigneeAddress1() {
        return consigneeAddress1;
    }
    
    public void setConsigneeAddress1(String consigneeAddress1) {
        this.consigneeAddress1 = consigneeAddress1;
    }
    
    public String getConsigneeAddress2() {
        return consigneeAddress2;
    }
    
    public void setConsigneeAddress2(String consigneeAddress2) {
        this.consigneeAddress2 = consigneeAddress2;
    }
    
    public String getConsigneeCity() {
        return consigneeCity;
    }
    
    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }
    
    public String getConsigneeCountry() {
        return consigneeCountry;
    }
    
    public void setConsigneeCountry(String consigneeCountry) {
        this.consigneeCountry = consigneeCountry;
    }
    
    public String getConsigneePhone() {
        return consigneePhone;
    }
    
    public void setConsigneePhone(String consigneePhone) {
        this.consigneePhone = consigneePhone;
    }
    
    public String getGoodsDescription() {
        return goodsDescription;
    }
    
    public void setGoodsDescription(String goodsDescription) {
        this.goodsDescription = goodsDescription;
    }
    
    public String getSpecialHandling() {
        return specialHandling;
    }
    
    public void setSpecialHandling(String specialHandling) {
        this.specialHandling = specialHandling;
    }
    
    @Override
    public String toString() {
        return "HawbData{" +
                "hawbNo='" + hawbNo + '\'' +
                ", hawbDate=" + hawbDate +
                ", origin='" + origin + '\'' +
                ", destination='" + destination + '\'' +
                ", pieces=" + pieces +
                ", weight=" + weight +
                ", shipperName='" + shipperName + '\'' +
                ", consigneeName='" + consigneeName + '\'' +
                ", goodsDescription='" + goodsDescription + '\'' +
                '}';
    }
}