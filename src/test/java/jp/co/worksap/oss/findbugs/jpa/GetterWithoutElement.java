package jp.co.worksap.oss.findbugs.jpa;

import javax.persistence.Column;

public class GetterWithoutElement {
    private String name;

    @Column
    public String getName() {
        return name;
    }
}
