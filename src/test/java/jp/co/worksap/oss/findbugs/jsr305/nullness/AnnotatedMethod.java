package jp.co.worksap.oss.findbugs.jsr305.nullness;

import javax.annotation.ParametersAreNonnullByDefault;

public class AnnotatedMethod {
    @ParametersAreNonnullByDefault
    public void methodAnnotatedWithByDefault(Object value) {}
}
