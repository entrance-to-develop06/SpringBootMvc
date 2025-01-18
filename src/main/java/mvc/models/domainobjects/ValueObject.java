package mvc.models.domainobjects;

import java.io.UnsupportedEncodingException;

public abstract class ValueObject<T> {

    protected abstract boolean runEquals(T other);

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass())
            return false;
        var vo = (T)obj;
        if (vo.equals(null)) {
            return false;
        }

        return runEquals(vo);
    }

    public boolean equals2(T other) {
        if (null == other)
            return false;
        if (this == other)
            return true;

        return runEquals(other);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    protected void isNull(Object value) {
        if(value.equals(null))
            throw new DomainObjectException("nullです");
    }

    protected void isEmpty(String value) {
        if (value.equals("")) {
            throw new DomainObjectException("商品が選択されていませんのでIDを取得できませんでした。");
        }
    }

    protected void isByteOvered(String value, int maxByteLength) {

        int count = 0;
        try {
            count = value.getBytes("ISO-8859-1").length; //Utf8 oracle, Sqlserver Shift_JIS, postgres ISO-8859-1
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (count > maxByteLength)
        {
            throw new DomainObjectException(maxByteLength + "バイトを超えた文字列が代入されました。" + count);
        }
    }
}