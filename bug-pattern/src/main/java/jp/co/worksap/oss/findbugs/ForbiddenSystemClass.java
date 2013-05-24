package jp.co.worksap.oss.findbugs;

import org.apache.bcel.classfile.Code;

import edu.umd.cs.findbugs.BugInstance;
import edu.umd.cs.findbugs.BugReporter;
import edu.umd.cs.findbugs.bcel.OpcodeStackDetector;

public class ForbiddenSystemClass extends OpcodeStackDetector {
    private BugReporter bugReporter;

    public ForbiddenSystemClass(BugReporter bugReporter) {
        this.bugReporter = bugReporter;
    }

    @Override
    public void visit(Code obj) {
        super.visit(obj);
    }

    @Override
    public void sawOpcode(int seen) {
        if (seen == GETSTATIC) {
            if (getClassConstantOperand().equals("java/lang/System")
                    && (getNameConstantOperand().equals("out") || getNameConstantOperand()
                            .equals("err"))) {
                BugInstance bug = new BugInstance(this, "FORBIDDEN_SYSTEM",
                        NORMAL_PRIORITY).addClassAndMethod(this).addSourceLine(
                        this, getPC());
                bug.addInt(getPC());
                bugReporter.reportBug(bug);
            }
        }
    }

}
