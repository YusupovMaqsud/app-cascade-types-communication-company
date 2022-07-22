package uz.pdp.appcascadetypescommunicationcompany.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.appcascadetypescommunicationcompany.entity.ClientMoveType;
import uz.pdp.appcascadetypescommunicationcompany.enums.ClientMoveTypeEnum;


public interface ClientMoveTypeRepository extends JpaRepository<ClientMoveType,Integer> {
    ClientMoveType findByName(ClientMoveTypeEnum name);
}
