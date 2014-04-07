package jp.co.worksap.oss.findbugs.guava;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertBugReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertNoBugsReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.bugReporterForTesting;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.ofType;

import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.findbugs.BugReporter;

public class UnexpectedAccessDetectorTest {

    private UnexpectedAccessDetector detector;
    private BugReporter bugReporter;

    @Before
    public void setup() {
        bugReporter = bugReporterForTesting();
        detector = new UnexpectedAccessDetector(bugReporter);
    }

    @Test
    public void testNormalMethod() throws Exception {
        assertNoBugsReported(ClassWhichCallsNormalMethod.class, detector, bugReporter);
    }

    @Test
    public void testCallingAnnotatedMethod() throws Exception {
        assertBugReported(ClassWhichCallsVisibleMethodForTesting.class, detector, bugReporter, ofType("GUAVA_UNEXPECTED_ACCESS_TO_VISIBLE_FOR_TESTING"));
    }

}
