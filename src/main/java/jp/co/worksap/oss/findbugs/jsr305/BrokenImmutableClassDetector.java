package jp.co.worksap.oss.findbugs.jsr305;

import java.util.Map;

import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.classfile.JavaClass;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Objects;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.AnnotationDetector;
import edu.umd.cs.findbugs.internalAnnotations.DottedClassName;

/**
 * <p>Detector to check immutability of class.</p>
 * <p>Note that this detector does not ensure that &quot;constructor stores deep-copied instance&quot; and
 * &quot;getter returns deep-copied instance&quot;. Use EI_EXPOSE_REP and EI_EXPOSE_REP2 to check it.</p>
 *
 * @see http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP
 * @see http://findbugs.sourceforge.net/bugDescriptions.html#EI_EXPOSE_REP2
 * @author Kengo TODA
 */
public class BrokenImmutableClassDetector extends AnnotationDetector {

    private final BugReporter reporter;

    public BrokenImmutableClassDetector(BugReporter reporter) {
        this.reporter = reporter;
    }

    @Override
    public void visitAnnotation(@DottedClassName String annotationClass,
            Map<String, ElementValue> map, boolean runtimeVisible) {
        if (!Objects.equal(annotationClass, "javax.annotation.concurrent.Immutable")) {
            return;
        }

        JavaClass targetClass = getThisClass();
        try {
            if (!targetClass.isFinal()) {
                reporter.reportBug(new BugInstance(this, "IMMUTABLE_CLASS_SHOULD_BE_FINAL", HIGH_PRIORITY).addClass(this));
            }
            checkImmutability(targetClass);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot find super class of " + targetClass.getClassName() + ". Check classpath.", e);
        } catch (NonImmutableException e) {
            reporter.reportBug(new BugInstance(this, "BROKEN_IMMUTABILITY", HIGH_PRIORITY).addClass(this));
        }
    }

    @VisibleForTesting
    void checkImmutability(JavaClass immutableClass) throws ClassNotFoundException, NonImmutableException {
        if (immutableClass == null) {
            return;
        }
        for (Field field : immutableClass.getFields()) {
            if (!field.isStatic() && !field.isFinal()) {
                throw new NonImmutableException(immutableClass, field);
            }
        }
        checkImmutability(immutableClass.getSuperClass());
    }

    @VisibleForTesting
    static class NonImmutableException extends Exception {
        private static final long serialVersionUID = 1446366298718611525L;

        private final JavaClass mutableClass;
        private final Field mutableField;

        NonImmutableException(JavaClass mutableClass, Field mutableField) {
            this.mutableClass = mutableClass;
            this.mutableField = mutableField;
        }

        @Override
        public String getMessage() {
            return String.format(
                    "Field (%s) in class (%s) should be final to make this class immutable.",
                    mutableClass.getClassName(),
                    mutableField.getName());
        }
    }
}
