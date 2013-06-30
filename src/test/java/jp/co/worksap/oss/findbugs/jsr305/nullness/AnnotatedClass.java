package jp.co.worksap.oss.findbugs.jsr305.nullness;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class AnnotatedClass {
    public void methodAnnotatedWithByDefault(Object value) {}
}
