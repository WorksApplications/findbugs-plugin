package jp.co.worksap.oss.findbugs;

import org.junit.Test;

import com.youdevise.fbplugins.tdd4fb.DetectorAssert;

import edu.umd.cs.findbugs.BugReporter;

public class ForbiddenSystemDetectorTest {

    @Test
    public void testBug() throws Exception {
        BugReporter bugReporter = DetectorAssert.bugReporterForTesting();
        ForbiddenSystemClass detector = new ForbiddenSystemClass(bugReporter);

        DetectorAssert.assertBugReported(UseSystemClass.class, detector,
                bugReporter);
    }


}
