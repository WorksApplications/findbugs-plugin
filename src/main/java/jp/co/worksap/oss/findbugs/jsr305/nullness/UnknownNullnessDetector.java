package jp.co.worksap.oss.findbugs.jsr305.nullness;


import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.ba.jsr305.JSR305NullnessAnnotations;
import edu.umd.cs.findbugs.ba.jsr305.TypeQualifierAnnotation;
import edu.umd.cs.findbugs.ba.jsr305.TypeQualifierApplications;
import edu.umd.cs.findbugs.ba.jsr305.TypeQualifierValue;

public class UnknownNullnessDetector extends BytecodeScanningDetector {

    private final BugReporter bugReporter;

    public UnknownNullnessDetector(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    @Override
    public void visitMethod(Method method) {
        TypeQualifierValue<?> nullness = TypeQualifierValue.getValue(JSR305NullnessAnnotations.NONNULL, null);
        detectUnknownNullnessOfParameter(method, nullness);
        detectUnknowNullnessOfReturnedValue(method, nullness);
    }

    private void detectUnknownNullnessOfParameter(Method method,
            TypeQualifierValue<?> nullness) {
        Type[] argumentTypes = method.getArgumentTypes();

        for (int i = 0; i < argumentTypes.length; ++i) {
            if (!(argumentTypes[i] instanceof ReferenceType)) {
                continue;
            }

            TypeQualifierAnnotation annotation = TypeQualifierApplications.getEffectiveTypeQualifierAnnotation(getXMethod(), i, nullness);
            if (annotation == null) {
                bugReporter.reportBug(new BugInstance("UNKNOWN_NULLNESS_OF_PARAMETER", NORMAL_PRIORITY).addClassAndMethod(this));
            }
        }
    }

    private void detectUnknowNullnessOfReturnedValue(Method method,
            TypeQualifierValue<?> nullness) {
        if (!(method.getReturnType() instanceof ReferenceType)) {
            return;
        }

        TypeQualifierAnnotation annotation = TypeQualifierApplications.getEffectiveTypeQualifierAnnotation(getXMethod(), nullness);
        if (annotation == null) {
            bugReporter.reportBug(new BugInstance("UNKNOWN_NULLNESS_OF_RETURNED_VALUE", NORMAL_PRIORITY).addClassAndMethod(this));
        }
    }
}
