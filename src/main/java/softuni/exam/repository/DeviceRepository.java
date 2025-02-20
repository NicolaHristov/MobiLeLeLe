package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Device;
import softuni.exam.models.entity.DeviceType;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository  extends JpaRepository<Device,Long> {


    Optional<Device>findDeviceByBrandAndModel(String brand, String model);
    //Export all devices of type SMART_PHONE with a price less than 1000 and storage equal to or more than 128 from the Database
    //•	Extract from the database, the device brand, model, storage and price.
    //•	Filter only smartphones whose price is less than 1000 and have storage equal to or greater than 128.
    // Order the results ascending by device brand. Keep in mind that in many systems, by default,
    // uppercase letters are considered "less than" lowercase letters when sorted alphabetically because they come first in the ASCII and Unicode tables.
    // For example, 'S' (ASCII 83) is less than 'i' (ASCII 105). Think about how to do proper sorting.
    //•	You have to round the value of the price to the second decimal digit.

    List<Device> findDeviceByBrandAndModelAndPriceBeforeAndStorageBeforeOrderByDeviceType(String brand, String model, Double price, Integer storage);
//    List<Device>findDeviceByDeviceTypeAndPriceLessThanAndStorageLessThanEqual(DeviceType deviceType, Double price, Integer storage);
    List<Device>findDeviceByDeviceTypeAndPriceLessThanAndStorageGreaterThanEqual(DeviceType deviceType, Double price, Integer storage);
    List<Device> findAllByDeviceTypeAndPriceLessThanAndStorageGreaterThanEqual(DeviceType deviceType, Double price, Integer storage);
    //    List<Device> findAllByPriceLessThanAndStorageGreaterThanEqual(double price, int storage);

    @Query("SELECT d FROM Device AS d WHERE d.deviceType = 'SMART_PHONE' and d.price < 1000 and d.storage >= 128 ORDER BY d.brand")
//@Query("select d from Device d where d.price < 1000 and  d.deviceType = 'SMART_PHONE' and d.storage >= 128" +
//        " order by LOWER(d.brand) asc")
    List<Device> findaAllAlabalaQueryy();
}
