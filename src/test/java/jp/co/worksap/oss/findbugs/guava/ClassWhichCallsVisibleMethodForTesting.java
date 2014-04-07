package jp.co.worksap.oss.findbugs.guava;

public class ClassWhichCallsVisibleMethodForTesting {
    public void method() {
        new MethodWithVisibleForTesting().method();
    }
}
