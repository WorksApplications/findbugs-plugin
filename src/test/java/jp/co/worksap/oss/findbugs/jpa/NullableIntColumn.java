package jp.co.worksap.oss.findbugs.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class NullableIntColumn {
    @Column(nullable = true)
    private int intValue;
}
