package mvc.models.appservices.dtos;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import mvc.models.domainobjects.entitys.ShohinEntity;

@Entity
@Table(name = "`shohins`") //大文字小文字を区別するためバッククォートで囲む //, schema="public", catalog="SpringBootPostgres")
public class ShohinDto {

    @Id
    @Column(name = "`unique_id`", columnDefinition = "char(36)", length = 36, nullable = false)
    private String uniqueId;

    @Column(name = "`shohin_code`", columnDefinition = "int", nullable = false)
    private Integer shohinCode;

    @Column(name = "`shohin_name`", columnDefinition = "char(50)", length = 50)
    private String shohinName;

    @Column(name = "`updated_on`", columnDefinition = "decimal(8, 0)", precision = 8, scale = 0, nullable = false)
    private BigDecimal editDate;

    @Column(name = "`updated_at`", columnDefinition = "decimal(6, 0)", precision = 6, scale = 0, nullable = false)
    private BigDecimal editTime;

    @Column(name = "`remarks`", columnDefinition = "varchar(255)", length = 255)
    private String remarks;

    public ShohinDto() {

    }

    public ShohinDto(ShohinEntity source) {
        uniqueId = source.getUniqueId().getValue();
        shohinCode = source.getShohinCode().getValue();
        shohinName = source.getShohinName().getValueNotSpace();
        var datetime = new DateTimeDto(source.getEditDateTime());
        editDate = datetime.getEditDate();
        editTime = datetime.getEditTime();
        remarks = source.getRemarks().getValue();
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public int getShohinCode() {
        return shohinCode;
    }
    public void setShohinCode(Integer value) {
        shohinCode = value;
    }

    public String getShohinName() {
        return shohinName;
    }
    public void setShohinName(String sss) {
        shohinName = sss;
    }

    public BigDecimal getEditDate() {
        return editDate;
    }

    public BigDecimal getEditTime() {
        return editTime;
    }

    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String value) {
        remarks = value;
    }
}