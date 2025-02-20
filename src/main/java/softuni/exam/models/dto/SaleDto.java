package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class SaleDto {

    @Expose
    private Boolean discounted;
    @Expose
    @Size(min = 7, max = 7)
    @NotNull
    private String number;
    @Expose
    @NotNull
    private String saleDate;
    @Expose
    private long seller;

    public SaleDto() {
    }

    public Boolean getDiscounted() {
        return discounted;
    }

    public void setDiscounted(Boolean discounted) {
        this.discounted = discounted;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(String saleDate) {
        this.saleDate = saleDate;
    }

    public long getSeller() {
        return seller;
    }

    public void setSeller(long seller) {
        this.seller = seller;
    }
}
