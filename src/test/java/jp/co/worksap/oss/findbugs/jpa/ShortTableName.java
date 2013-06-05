package jp.co.worksap.oss.findbugs.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="tbl_name_shorter_than_31_bytes")
public class ShortTableName {

    @Id
    private long id;

    public ShortTableName(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

}
