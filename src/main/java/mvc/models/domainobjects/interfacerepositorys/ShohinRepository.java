package mvc.models.domainobjects.interfacerepositorys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mvc.models.appservices.dtos.ShohinDto;
import mvc.models.domainobjects.shohinvalueobjects.UniqueId;

@Repository
public interface ShohinRepository extends JpaRepository<ShohinDto, String> {
    ShohinDto findByUniqueId(String id);
    List<ShohinDto> findByShohinCode(Integer code);
}