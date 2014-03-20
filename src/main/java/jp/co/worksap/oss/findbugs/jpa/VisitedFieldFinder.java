package jp.co.worksap.oss.findbugs.jpa;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.apache.bcel.classfile.FieldOrMethod;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.commons.EmptyVisitor;

import edu.umd.cs.findbugs.bcel.AnnotationDetector;

/**
 * <p>Simple ClassVisitor implementation to find visited field in the specified method.</p>
 * <p>To create instance, you need to provide name and descriptor to specify the target method.</p>
 *
 * @author Kengo TODA
 */
final class VisitedFieldFinder extends EmptyVisitor {
    private final String targetMethodName;
    private final String targetMethodDescriptor;

    private String visitedFieldName;

    public VisitedFieldFinder(@Nonnull String targetMethodName, @Nonnull String targetMethodDescriptor) {
        this.targetMethodName = checkNotNull(targetMethodName);
        this.targetMethodDescriptor = checkNotNull(targetMethodDescriptor);
    }

    @CheckReturnValue
    @Nullable
    private String getVisitedFieldName() {
        return visitedFieldName;
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        if (name.equals(targetMethodName) && descriptor.equals(targetMethodDescriptor)) {
            return this;
        } else {
            // We do not have to analyze this method.
            // Returning null let ASM skip parsing this method.
            return null;
        }
    }

    @Override
    public void visitFieldInsn(int code, String owner, String name, String description) {
        this.visitedFieldName = name;
    }

    @Nullable
    @CheckReturnValue
    static String findFieldWhichisVisitedInVisitingMethod(AnnotationDetector detector) {
        byte[] classByteCode = detector.getClassContext().getJavaClass().getBytes();
        ClassReader reader = new ClassReader(classByteCode);

        FieldOrMethod targetMethod = detector.getMethod();
        // note: bcel's #getSignature() method returns String like "(J)V", this is named as "descriptor" in the context of ASM.
        // This is the reason why we call `targetMethod.getSignature()` to get value for `targetMethodDescriptor` argument.
        VisitedFieldFinder visitedFieldFinder = new VisitedFieldFinder(targetMethod .getName(), targetMethod.getSignature());

        reader.accept(visitedFieldFinder, 0);
        return visitedFieldFinder.getVisitedFieldName();
    }
}
