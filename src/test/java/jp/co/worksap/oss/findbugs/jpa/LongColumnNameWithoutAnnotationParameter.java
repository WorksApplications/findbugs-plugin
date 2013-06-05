package jp.co.worksap.oss.findbugs.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class LongColumnNameWithoutAnnotationParameter {
    @Id
    private long id;

    @Column
    private String longColumnNameWitoutAnnotationParameter;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLongColumnNameWitoutAnnotationParameter() {
        return longColumnNameWitoutAnnotationParameter;
    }

    public void setLongColumnNameWitoutAnnotationParameter(
            String longColumnNameWitoutAnnotationParameter) {
        this.longColumnNameWitoutAnnotationParameter = longColumnNameWitoutAnnotationParameter;
    }

}
