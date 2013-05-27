package jp.co.worksap.oss.findbugs;

import org.junit.Test;

import com.youdevise.fbplugins.tdd4fb.DetectorAssert;

import edu.umd.cs.findbugs.BugReporter;

public class ForbiddenSystemDetectorTest {

    @Test
    public void testUseSystemOutBug() throws Exception {
        BugReporter bugReporter = DetectorAssert.bugReporterForTesting();
        ForbiddenSystemClass detector = new ForbiddenSystemClass(bugReporter);

        DetectorAssert.assertBugReported(UseSystemOut.class, detector,
                bugReporter);
    }

    @Test
    public void testUseSystemErrBug() throws Exception {
        BugReporter bugReporter = DetectorAssert.bugReporterForTesting();
        ForbiddenSystemClass detector = new ForbiddenSystemClass(bugReporter);

        DetectorAssert.assertBugReported(UseSystemErr.class, detector,
                bugReporter);
    }

}
