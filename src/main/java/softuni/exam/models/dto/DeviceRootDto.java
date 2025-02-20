package softuni.exam.models.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "devices")
@XmlAccessorType(XmlAccessType.FIELD)
public class DeviceRootDto {


    @XmlElement(name = "device")
    private List<DeviceDto> deviceDtos;

    public List<DeviceDto> getDeviceDtos() {
        return deviceDtos;
    }

    public void setDeviceDtos(List<DeviceDto> deviceDtos) {
        this.deviceDtos = deviceDtos;
    }
}
