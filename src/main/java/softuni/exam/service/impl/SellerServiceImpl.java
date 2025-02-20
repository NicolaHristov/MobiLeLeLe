package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SellerDto;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    private static final String FILE_PATH = "src/main/resources/files/json/sellers.json";
    private final SellerRepository sellerRepository;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtil validationUtil;

    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, Gson gson, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(FILE_PATH));
    }

    @Override
    public String importSellers() throws IOException {
        SellerDto[] sellerDtos = gson.fromJson(readSellersFromFile(), SellerDto[].class);
        StringBuilder stringBuilder = new StringBuilder();

        for (SellerDto sellerDto : sellerDtos) {
//If a seller with the same last name already exists in the DB or the first/last name does not meet size constraints return "Invalid seller".

            Optional<Seller> optionalSeller = sellerRepository.findByLastName(sellerDto.getLastName());
            if(!validationUtil.isValid(sellerDto) || optionalSeller.isPresent()){
                stringBuilder.append("Invalid seller").append(System.lineSeparator());
            }else{

                Seller seller = modelMapper.map(sellerDto, Seller.class);
                sellerRepository.save(seller);
                stringBuilder.append(String.format("Successfully imported seller %s %s",sellerDto.getFirstName(),sellerDto.getLastName())).append(System.lineSeparator());
            }


        }


        return stringBuilder.toString();
    }
}
