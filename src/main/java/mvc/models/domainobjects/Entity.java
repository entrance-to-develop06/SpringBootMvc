package mvc.models.domainobjects;

public abstract class Entity<T> {

    protected abstract boolean runEquals(T other);

    @Override
    public boolean equals(Object obj) {
        var entity = (T)obj;
        if (entity.equals(null)) {
            return false;
        }

        return runEquals(entity);
    }

    protected void isNull(Object value) {
        if(value.equals(null)) {
            throw new DomainObjectException("nullです");
        }
    }
}