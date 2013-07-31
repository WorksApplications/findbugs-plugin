package jp.co.worksap.oss.findbugs.junit;

import org.junit.Ignore;

public class IgnoreMethodWithEmptyExplanation {
    @Ignore("")
    public void method() {
    }
}
