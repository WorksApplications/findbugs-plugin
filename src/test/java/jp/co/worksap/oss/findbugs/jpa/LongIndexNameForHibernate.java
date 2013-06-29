package jp.co.worksap.oss.findbugs.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

@Entity
public class LongIndexNameForHibernate {
    @Id
    private long id;

    @Column(nullable = true)
    @Index(name = "index_name_longer_than_30_bytes")
    private String value;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
