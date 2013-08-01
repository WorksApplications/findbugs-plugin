package jp.co.worksap.oss.findbugs.junit;

import java.util.Map;

import org.apache.bcel.classfile.ElementValue;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.internalAnnotations.DottedClassName;

public class UndocumentedIgnoreDetector extends BytecodeScanningDetector {

    private final BugReporter bugReporter;

    public UndocumentedIgnoreDetector(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    @Override
    public void visitAnnotation(@DottedClassName String annotationClass,
            Map<String, ElementValue> map, boolean runtimeVisible) {
        if (!"org.junit.Ignore".equals(annotationClass)) {
            return;
        }

        final ElementValue reason = map.get("value");
        if (reason == null || reason.stringifyValue().trim().isEmpty()) {
            BugInstance bugInstance = new BugInstance("UNDOCUMENTED_IGNORE",
                    HIGH_PRIORITY).addClass(this);
            if (visitingMethod()) {
                bugInstance.addMethod(this).addSourceLine(this);
            }
            bugReporter.reportBug(bugInstance);
        }
    }
}
