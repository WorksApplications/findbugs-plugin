package jp.co.worksap.oss.findbugs.jpa;

import java.util.Map;

import javax.annotation.CheckReturnValue;
import javax.annotation.Nonnull;

import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.classfile.Field;
import org.apache.bcel.generic.Type;

import com.google.common.base.Objects;

import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.AnnotationDetector;
import edu.umd.cs.findbugs.internalAnnotations.DottedClassName;

abstract class AbstractColumnDetector extends AnnotationDetector {
    private final BugReporter bugReporter;

    AbstractColumnDetector(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    @Nonnull
    @CheckReturnValue
    protected final BugReporter getBugReporter() {
    	return bugReporter;
    }

    @Override
    public final void visitAnnotation(@DottedClassName String annotationClass,
            Map<String, ElementValue> map, boolean runtimeVisible) {
        if (!Objects.equal(annotationClass, "javax.persistence.Column")) {
            return;
        }

        Type columnType;
        if (visitingField()) {
            columnType = getField().getType();
        } else if (visitingMethod()) {
            Field visitingField = findFieldInVisitingMethod();
            columnType = visitingField.getType();
        } else {
            throw new IllegalStateException("@Column should annotate field or method.");
        }
        verifyColumn(columnType, map);
    }

    @Nonnull
    private Field findFieldInVisitingMethod() {
        String fieldName = VisitedFieldFinder.findFieldWhichisVisitedInVisitingMethod(this);
        Field visitingField = null;
        for (Field field : getThisClass().getFields()) {
            if (Objects.equal(field.getName(), fieldName)) {
                visitingField = field;
                break;
            }
        }
        if (visitingField == null) {
            throw new IllegalStateException("Cannot find field which named as " + fieldName + ".");
        }
        return visitingField;
    }

    protected abstract void verifyColumn(Type columnType, Map<String, ElementValue> elements);

}
