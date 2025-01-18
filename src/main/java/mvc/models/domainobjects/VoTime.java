package mvc.models.domainobjects;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/** 値オブジェクト：時刻
 *　　不変*/
public class VoTime extends ValueObject<VoTime> {

    private final BigDecimal value;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
    private static final String ZONE_ID = "Asia/Tokyo";

    /** 完全コンストラクタ
     * */
    public VoTime(BigDecimal time) {
        isNull(time);
        try {
            formatter.format(LocalTime.parse(time.toString(), formatter));
        }
        catch (DateTimeParseException e) {
            throw new DomainObjectException(e.getMessage());
        }
        value = time;
    }

    public VoTime() {
        var time = formatter.format(LocalTime.now(ZoneId.of(ZONE_ID)));
        value = BigDecimal.valueOf(Integer.valueOf(time));
    }

    /** ゲッター
     */
    public BigDecimal getValue() {
        return value;
    }

    /** コロン付きゲッター(00:00:00)
     * @return*/
    public String getValueAndColonFormat() {
        var time = new StringBuilder(value.toString()).insert(4, ':').insert(2, ':');
        return time.toString();
    }

    /** 等値(同一オブジェクト)比較(比較するのはVoTimeではなくvalueのオブジェクト)
     */
    @Override
    protected boolean runEquals(VoTime other) {
        return value == other.getValue();
    }

    public boolean equality(VoTime other) {
        return this == other;
    }

    public boolean equivalent(VoTime other) {
        return value == other.getValue();
    }

    /** 再作成
     * @param time
     * @return*/
    public VoTime recreate(BigDecimal time) {
        return new VoTime(time);
    }

	/*@Override
	protected <T> VoTime runReNew(T value) {
		return new VoTime((BigDecimal)value);
	}*/
}