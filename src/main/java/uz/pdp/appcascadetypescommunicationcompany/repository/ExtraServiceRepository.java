package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcascadetypescommunicationcompany.entity.ExtraService;
import uz.pdp.appcascadetypescommunicationcompany.entity.Filial;

import java.util.List;

@RepositoryRestResource(path = "extraservice")
public interface ExtraServiceRepository extends JpaRepository<ExtraService,Integer> {
    List<ExtraService> findAllByServiceTypeId(Integer serviceType_id);
}
