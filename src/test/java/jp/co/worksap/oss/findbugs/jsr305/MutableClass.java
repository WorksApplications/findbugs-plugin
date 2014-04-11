package jp.co.worksap.oss.findbugs.jsr305;

import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

@NotThreadSafe
@Immutable // marked as immutable, but field is not final
public class MutableClass {
    public String value;
}
