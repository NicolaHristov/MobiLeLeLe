package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.DeviceDto;
import softuni.exam.models.dto.DeviceRootDto;
import softuni.exam.models.entity.Device;
import softuni.exam.models.entity.DeviceType;
import softuni.exam.models.entity.Sale;
import softuni.exam.repository.DeviceRepository;
import softuni.exam.repository.SaleRepository;
import softuni.exam.service.DeviceService;
import softuni.exam.service.SaleService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DeviceServiceImpl implements DeviceService {

    private static final String FILE_PATH = "src/main/resources/files/xml/devices.xml";
    private final DeviceRepository deviceRepository;
    private final SaleRepository saleRepository;
    private final SaleService saleService;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;

    public DeviceServiceImpl(DeviceRepository deviceRepository, SaleRepository saleRepository, SaleService saleService, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser) {
        this.deviceRepository = deviceRepository;
        this.saleRepository = saleRepository;
        this.saleService = saleService;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return deviceRepository.count() > 0;
    }

    @Override
    public String readDevicesFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importDevices() throws IOException, JAXBException {
//•	If a device with the same brand and model already exists in the DB return "Invalid device".
//•	If a device appears in a sale that doesn't exist in the DB return "Invalid device".
        StringBuilder stringBuilder = new StringBuilder();
        DeviceRootDto deviceRootDtos = xmlParser.fromFile(FILE_PATH, DeviceRootDto.class);

        for (DeviceDto deviceDto : deviceRootDtos.getDeviceDtos()) {
            Optional<Device> optionalDevice = deviceRepository.findDeviceByBrandAndModel(deviceDto.getBrand(),deviceDto.getModel());
//            Optional<Sale> optionalSale = saleRepository.findById(deviceDto.getSale());
            Sale sale = saleService.findSaleById(deviceDto.getSale());

            if(!validationUtil.isValid(deviceDto) || optionalDevice.isPresent() || sale == null){
                stringBuilder.append("Invalid device").append(System.lineSeparator());
            }else {
// Device device = modelMapper.map(deviceDto, Device.class);
//                Sale sale = saleService.findSaleById(deviceDto.getSale());
//                Set<Device> devicosToAdd = new HashSet<>();
//                devicosToAdd.add(device);
//                sale.setDevices(devicosToAdd);
////                saleService.addAndSaveAddedDevice(sale, device);
//
//                device.setSale(sale);
//
//                deviceRepository.save(device);
                stringBuilder.append(String.format("Successfully imported device of type %s with brand %s",deviceDto.getDeviceType(),deviceDto.getBrand())).append(System.lineSeparator());
//                DeviceDto deviceDto1 = modelMapper.map(deviceDto, DeviceDto.class); NE TRQBWA da e kum DTO
                Device currentDevice = modelMapper.map(deviceDto, Device.class);
                Sale currentSale = saleService.findSaleById(deviceDto.getSale());

                Set<Device> devicesToAdd = new HashSet<>();
                devicesToAdd.add(currentDevice);
                currentSale.setDevices(devicesToAdd);

               currentDevice.setSale(currentSale);

                deviceRepository.save(currentDevice);


            }

        }

        return stringBuilder.toString();
    }

    @Override
    public String exportDevices() {
        StringBuilder stringBuilder = new StringBuilder();

//     List<Device> devices = deviceRepository.findAllByDeviceTypeAndPriceLessThanAndStorageGreaterThanEqual(DeviceType.SMART_PHONE,1000.00,128);
        List<Device> devices = deviceRepository.findaAllAlabalaQueryy();

//        for (Device device : devices) {
//            stringBuilder.append(String.format("\"Device brand: %s\n" +
//                    "   *Model: %s\n" +
//                    "   **Storage: %d\n" +
//                    "   ***Price: %.2f\n" +
//                    ". . .\"\n", device.getBrand(), device.getModel(), device.getStorage(), device.getPrice())).append(System.lineSeparator());
//        }
        devices.forEach( device -> {
            stringBuilder.append(String.format("Device brand: %s\n" +
                    "   *Model: %s\n" +
                    "   **Storage: %d\n" +
                    "   ***Price: %.2f",device.getBrand(),device.getModel(),device.getStorage(),device.getPrice())).append(System.lineSeparator());
        });

        return stringBuilder.toString();
//        StringBuilder build = new StringBuilder();
//
//        List<Device> foundDevices = deviceRepository.findaAllAlabalaQueryy();
//
//        foundDevices.forEach(v -> {
//            build.append(String.format("Device brand: %s\n" +
//                                    "   *Model: %s\n" +
//                                    "   **Storage: %d\n" +
//                                    "   ***Price: %.2f",
//                            v.getBrand(),
//                            v.getModel(),
//                            v.getStorage(),
//                            v.getPrice()))
//                    .append(System.lineSeparator());
//
//        });
//        return build.toString();
//    }
    }
}
