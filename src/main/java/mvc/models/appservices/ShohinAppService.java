package mvc.models.appservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import mvc.models.appservices.dtos.ShohinDto;
import mvc.models.domainobjects.VoDate;
import mvc.models.domainobjects.VoTime;
import mvc.models.domainobjects.domainservices.ShohinDomainService;
import mvc.models.domainobjects.entitys.ShohinEntity;
import mvc.models.domainobjects.interfacerepositorys.ShohinRepository;
import mvc.models.domainobjects.shohinvalueobjects.EditDateTime;
import mvc.models.domainobjects.shohinvalueobjects.Remarks;
import mvc.models.domainobjects.shohinvalueobjects.ShohinCode;
import mvc.models.domainobjects.shohinvalueobjects.ShohinName;
import mvc.models.domainobjects.shohinvalueobjects.UniqueId;

@Service
public class ShohinAppService {

    @Autowired
    ShohinRepository repository;
    private final ShohinDomainService domainService;

    public ShohinAppService(ShohinRepository shohinRepository) {
        repository = shohinRepository;
        domainService = new ShohinDomainService(repository);
    }

    public List<ShohinDto> getAllShohinList() {
        return repository.findAll(Sort.by(Direction.ASC, "uniqueId"));
    }

    public ShohinDto findByUniqueId(UniqueId uniqueId) {
        //ShohinEntityに値を格納もすること。
        return repository.findById(uniqueId.getValue()).orElse(new ShohinDto());
    }

    public ShohinDto findByShohinCode(ShohinCode shohinCode) {
        var dto = repository.findByShohinCode(shohinCode.getValue());
        var data = dto.get(0);
        var shohin = new ShohinEntity(new UniqueId(data.getUniqueId()),
                new ShohinCode(shohinCode.getValue()),
                new ShohinName(data.getShohinName()),
                new EditDateTime(new VoDate(data.getEditDate()), new VoTime(data.getEditTime())),
                new Remarks(data.getRemarks())
        );
        return data;
    }

    public void registerShohin(String shohinCode, String shohinName, String remarks) {
        var shohin = new ShohinEntity(
                new ShohinCode(shohinCode),
                new ShohinName(shohinName),
                new Remarks(remarks)
        );
        if (domainService.isRegistered(shohin)) {
            throw new BusinessAppException("商品番号：" + shohinCode + "はすでに登録されております。");
        }
        else {
            var dto = new ShohinDto(shohin);
            repository.save(dto);
        }
    }

    public void editShohin(String uniqueId, String shohinCode, String shohinName, String remarks) {
        var id = new UniqueId(uniqueId);
        var data = repository.findById(id.getValue()).orElse(new ShohinDto());
        if (data == null)
        {
            throw new BusinessAppException("商品に対するIDを見つけれませんでした。ID:" + uniqueId);
        }

        var shohin = new ShohinEntity(new UniqueId(data.getUniqueId()),
                new ShohinCode(data.getShohinCode()),
                new ShohinName(data.getShohinName()),
                new EditDateTime(new VoDate(data.getEditDate()), new VoTime(data.getEditTime())),
                new Remarks(data.getRemarks())
        );

        var code = new ShohinCode(shohinCode);
        shohin.setShohinCode(code);
        var name = new ShohinName(shohinName);
        shohin.setShohinName(name);
        shohin.setDateTime();
        var note = new Remarks(remarks);
        shohin.setRemarks(note);

        var dto = new ShohinDto(shohin);
        repository.save(dto);
    }

    public void removeShohin(String uniqueId)
    {
        var shohin = repository.findByUniqueId(uniqueId);
        if (shohin == null)
        {
            throw new BusinessAppException("商品に対するIDを見つけれませんでした。ID:" + uniqueId);
        }

        repository.deleteById(uniqueId);
    }
}