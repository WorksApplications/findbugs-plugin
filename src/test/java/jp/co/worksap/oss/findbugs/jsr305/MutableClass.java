package jp.co.worksap.oss.findbugs.jsr305;

import javax.annotation.concurrent.Immutable;

@Immutable // marked as immutable, but field is not final
public class MutableClass {
    public String value;
}
