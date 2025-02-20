package softuni.exam.models.dto;

import softuni.exam.models.entity.DeviceType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

//@XmlRootElement(name = "device")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceDto {


    @XmlElement
    @NotNull
    @Size(min = 2, max = 20)
    private String brand;
    @XmlElement(name = "device_type")
    @Enumerated(EnumType.STRING)
    private DeviceType deviceType;

    @XmlElement
    @Size(min = 1, max = 20)
    @NotNull
    private String model;
    @XmlElement
    @Positive
    private Double price;
    @XmlElement
    @Positive
    private Integer storage;
    @XmlElement(name = "sale_id")
    private long sale;

    public DeviceDto() {
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public long getSale() {
        return sale;
    }

    public void setSale(long sale) {
        this.sale = sale;
    }
}
