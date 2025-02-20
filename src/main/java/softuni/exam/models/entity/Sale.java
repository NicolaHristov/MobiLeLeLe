package softuni.exam.models.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "sales")
public class Sale extends BaseEntity{

    private Boolean discounted;
    @Column(unique = true, nullable = false)
    private String number;
    @Column(name = "sale_date",nullable = false)
    private LocalDateTime saleDate;

    public Set<Device> getDevices() {
        return devices;
    }

    public void setDevices(Set<Device> devices) {
        this.devices = devices;
    }

    @OneToMany
    private Set<Device> devices;


   @OneToOne
    private Seller seller;

    public Sale() {
    }

    public boolean isDiscounted() {
        return discounted;
    }

    public void setDiscounted(boolean discounted) {
        this.discounted = discounted;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Boolean getDiscounted() {
        return discounted;
    }

    public void setDiscounted(Boolean discounted) {
        this.discounted = discounted;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
}
