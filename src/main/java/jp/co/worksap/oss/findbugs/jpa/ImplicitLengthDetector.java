package jp.co.worksap.oss.findbugs.jpa;

import java.util.Map;

import org.apache.bcel.classfile.ElementValue;
import org.apache.bcel.generic.Type;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;

public class ImplicitLengthDetector extends AbstractColumnDetector {

    public ImplicitLengthDetector(BugReporter bugReporter) {
        super(bugReporter);
    }

    @Override
    protected void verifyColumn(Type columnType,
            Map<String, ElementValue> elements) {
        if (verifyColumnType(columnType) && !elements.containsKey("length")) {
            BugInstance bug = new BugInstance(this, "IMPLICIT_LENGTH", HIGH_PRIORITY);
            if (visitingMethod()) {
                bug.addMethod(this);
            }
            getBugReporter().reportBug(bug);
        }
    }

    /**
     * @param columnType
     * @return true if column type requires length property.
     */
    private boolean verifyColumnType(Type columnType) {
        // TODO true if target is annotated with @Lob
        return Type.STRING.equals(columnType);
    }

}
