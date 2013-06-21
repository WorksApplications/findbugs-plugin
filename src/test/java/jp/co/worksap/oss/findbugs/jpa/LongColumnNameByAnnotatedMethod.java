package jp.co.worksap.oss.findbugs.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LongColumnNameByAnnotatedMethod {
    @Id
    private long id;

    private String longColumnNameByAnnotatedMethod;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column
    public String getLongColumnNameByAnnotatedMethod() {
        return longColumnNameByAnnotatedMethod;
    }

    public void setLongColumnNameByAnnotatedMethod(
            String longColumnNameByAnnotatedMethod) {
        this.longColumnNameByAnnotatedMethod = longColumnNameByAnnotatedMethod;
    }

}
