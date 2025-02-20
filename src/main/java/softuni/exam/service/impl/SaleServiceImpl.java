package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SaleDto;
import softuni.exam.models.entity.Sale;
import softuni.exam.repository.SaleRepository;
import softuni.exam.service.SaleService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class SaleServiceImpl implements SaleService {

    private static final String FILE_PATH = "src/main/resources/files/json/sales.json";
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public SaleServiceImpl(SaleRepository saleRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.saleRepository = saleRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return saleRepository.count() > 0;
    }

    @Override
    public String readSalesFileContent() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importSales() throws IOException {
        SaleDto[] saleDtos = gson.fromJson(readSalesFileContent(), SaleDto[].class);

        StringBuilder stringBuilder = new StringBuilder();

        for (SaleDto saleDto : saleDtos) {
            Optional<Sale> optionalSale = saleRepository.findByNumber(saleDto.getNumber());

            if(!validationUtil.isValid(saleDto) || optionalSale.isPresent()){
                stringBuilder.append("Invalid sale").append(System.lineSeparator());
            }else{

                Sale sale = modelMapper.map(saleDto, Sale.class);

                saleRepository.save(sale);
                stringBuilder.append(String.format("Successfully imported sale with number %s",saleDto.getNumber())).append(System.lineSeparator());
            }
        }
        return stringBuilder.toString();
    }

    @Override
    public Sale findSaleById(Long saleId) {
        return saleRepository.findById(saleId).orElse(null);
    }
}
