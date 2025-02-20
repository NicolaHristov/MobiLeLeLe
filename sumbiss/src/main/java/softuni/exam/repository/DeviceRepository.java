package softuni.exam.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Device;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {


}
