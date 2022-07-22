package uz.pdp.appcascadetypescommunicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import uz.pdp.appcascadetypescommunicationcompany.entity.Income;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;
@RepositoryRestResource(path = "income")
public interface IncomeRepository extends JpaRepository<Income, UUID> {
    List<Income> findAllByDateBetween(Timestamp minDate, Timestamp maxDate);
}
