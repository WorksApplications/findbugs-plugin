package jp.co.worksap.oss.findbugs.guava;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.AnnotationEntry;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.BytecodeScanningDetector;
import edu.umd.cs.findbugs.bcel.BCELUtil;
import edu.umd.cs.findbugs.classfile.ClassDescriptor;
import edu.umd.cs.findbugs.classfile.MethodDescriptor;

/**
 * <p>A detector to ensure that implementation (class in src/main/java) doesn't call
 * package-private method in other class which is annotated by {@code @VisibleForTesting}.</p>
 *
 * @author Kengo TODA (toda_k@worksap.co.jp)
 * @see com.google.common.annotations.VisibleForTesting
 */
public class UnexpectedAccessDetector extends BytecodeScanningDetector {
    @Nonnull
    private final BugReporter bugReporter;

    public UnexpectedAccessDetector(BugReporter bugReporter) {
        this.bugReporter = checkNotNull(bugReporter);
    }

    @Override
    public void sawOpcode(int opcode) {
        if (! isInvoking(opcode)) {
            return;
        }

        ClassDescriptor currentClass = getClassDescriptor();
        ClassDescriptor invokedClass = getClassDescriptorOperand();
        if (currentClass.equals(invokedClass)) {
            // no need to check, because method is called by owner
        } else if (! currentClass.getPackageName().equals(invokedClass.getPackageName())) {
            // no need to check, because method is called by class in other package
        } else {
            MethodDescriptor invokedMethod = getMethodDescriptorOperand();

            try {
                verifyVisibility(invokedClass, invokedMethod);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    /**
     * <p>Report if specified method is package-private and annotated by {@code @VisibleForTesting}.</p>
     */
    private void verifyVisibility(ClassDescriptor invokedClass, MethodDescriptor invokedMethod) throws ClassNotFoundException {
        JavaClass bcelClass = Repository.getRepository().loadClass(invokedClass.getDottedClassName());
        Method bcelMethod = findMethod(bcelClass, invokedMethod);

        if (checkVisibility(bcelMethod) && checkAnnotated(bcelMethod)) {
            BugInstance bug = new BugInstance(this, "GUAVA_UNEXPECTED_ACCESS_TO_VISIBLE_FOR_TESTING", HIGH_PRIORITY)
                    .addCalledMethod(this).addClassAndMethod(this).addSourceLine(this);
            bugReporter.reportBug(bug);
        }
    }

    private Method findMethod(JavaClass bcelClass, MethodDescriptor invokedMethod) {
        for (Method bcelMethod : bcelClass.getMethods()) {
            MethodDescriptor methodDescriptor = BCELUtil.getMethodDescriptor(bcelClass, bcelMethod);
            if (methodDescriptor.equals(invokedMethod)) {
                return bcelMethod;
            }
        }

        throw new IllegalArgumentException("Method not found: " + invokedMethod);
    }

    /**
     * @return true if visibility of specified method is package-private.
     */
    private boolean checkVisibility(Method bcelMethod) {
        return ! (bcelMethod.isPrivate() || bcelMethod.isProtected() || bcelMethod.isPublic());
    }

    /**
     * @return true if specified method is annotated by {@code VisibleForTesting}.
     */
    private boolean checkAnnotated(Method bcelMethod) {
        for (AnnotationEntry annotation : bcelMethod.getAnnotationEntries()) {
            String type = annotation.getAnnotationType();
            if ("Lcom/google/common/annotations/VisibleForTesting;".equals(type)) {
                return true;
            }
        }
        return false;
    }

    private boolean isInvoking(int opcode) {
        return opcode == INVOKESPECIAL ||
                opcode == INVOKEINTERFACE ||
                opcode == INVOKESTATIC ||
                opcode == INVOKEVIRTUAL;
    }

}
