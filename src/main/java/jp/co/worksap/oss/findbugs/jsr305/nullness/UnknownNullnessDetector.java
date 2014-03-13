package jp.co.worksap.oss.findbugs.jsr305.nullness;


import java.lang.annotation.ElementType;
import java.lang.reflect.InvocationTargetException;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ReferenceType;
import org.apache.bcel.generic.Type;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.ba.XMethod;
import edu.umd.cs.findbugs.ba.jsr305.JSR305NullnessAnnotations;
import edu.umd.cs.findbugs.ba.jsr305.TypeQualifierAnnotation;
import edu.umd.cs.findbugs.ba.jsr305.TypeQualifierApplications;
import edu.umd.cs.findbugs.ba.jsr305.TypeQualifierValue;
import edu.umd.cs.findbugs.classfile.analysis.AnnotatedObject;

public class UnknownNullnessDetector extends BytecodeScanningDetector {

    private static final java.lang.reflect.Method GET_DEFAULT_ANNOTATION;
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
                TypeQualifierAnnotation defaultAnnotation = findDefaultAnnotation(getXMethod(), nullness);
                if (defaultAnnotation == null) {
                    bugReporter.reportBug(new BugInstance("UNKNOWN_NULLNESS_OF_PARAMETER", NORMAL_PRIORITY).addClassAndMethod(this));
                }
            }
        }
    }

    /**
     * <p>To avoid a bug of FindBugs, we need reflection (!) to call private method.</p>
     * @see https://sourceforge.net/p/findbugs/bugs/1194/
     */
    private TypeQualifierAnnotation findDefaultAnnotation(XMethod xMethod,
            TypeQualifierValue<?> nullness) {
        try {
            Object result = GET_DEFAULT_ANNOTATION.invoke(null, xMethod, nullness, ElementType.PARAMETER);
            if (result instanceof TypeQualifierAnnotation) {
                return (TypeQualifierAnnotation) result;
            } else {
                return null;
            }
        } catch (SecurityException e) {
            throw new IllegalStateException(e);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        } catch (InvocationTargetException e) {
            throw new IllegalArgumentException(e);
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

    static {
        try {
            GET_DEFAULT_ANNOTATION = TypeQualifierApplications.class
                    .getDeclaredMethod("getDefaultAnnotation",
                            AnnotatedObject.class, TypeQualifierValue.class, ElementType.class);
            GET_DEFAULT_ANNOTATION.setAccessible(true);
        } catch (SecurityException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException(e);
        }
    }
}
