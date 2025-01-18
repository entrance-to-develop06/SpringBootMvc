package mvc.models.domainobjects.domainservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mvc.models.domainobjects.entitys.ShohinEntity;
import mvc.models.domainobjects.interfacerepositorys.ShohinRepository;

/** ドメインサービス
 */
@Service
public class ShohinDomainService {

    @Autowired
    ShohinRepository repository;

    public ShohinDomainService(ShohinRepository shohinRepository) {
        repository = shohinRepository;
    }

    /** 商品番号の登録チェック
     * @param shohin
     * @return */
    public boolean isRegistered(ShohinEntity shohin) { //ShohinEntity
        var code = shohin.getShohinCode();

        //データベースに問い合わせてすでに同じ商品番号が登録されているかチェックする
        var serched = repository.findByShohinCode(code.getValue());

        var n = false;
        if(serched.size() != 0) {
            n = true;
        }

        return n;
    }
}