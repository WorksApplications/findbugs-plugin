package jp.co.worksap.oss.findbugs.junit;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertBugReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertNoBugsReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.bugReporterForTesting;

import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.findbugs.BugReporter;

public class UndocumentedIgnoreDetectorTest {
    private UndocumentedIgnoreDetector detector;
    private BugReporter bugReporter;

    @Before
    public void setup() {
        bugReporter = bugReporterForTesting();
        detector = new UndocumentedIgnoreDetector(bugReporter);
    }

    @Test
    public void testIgnoreClassWithExplanation() throws Exception {
        assertNoBugsReported(IgnoreClassWithExplanation.class, detector, bugReporter);
    }

    @Test
    public void testIgnoreMethodWithExplanation() throws Exception {
        assertNoBugsReported(IgnoreMethodWithExplanation.class, detector, bugReporter);
    }

    @Test
    public void testIgnoreClassWithEmptyExplanation() throws Exception {
        assertBugReported(IgnoreClassWithEmptyExplanation.class, detector, bugReporter);
    }

    @Test
    public void testIgnoreMethodWithEmptyExplanation() throws Exception {
        assertBugReported(IgnoreMethodWithEmptyExplanation.class, detector, bugReporter);
    }

    @Test
    public void testIgnoreClassWithoutExplanation() throws Exception {
        assertBugReported(IgnoreClassWithoutExplanation.class, detector, bugReporter);
    }

    @Test
    public void testIgnoreMethodWithoutExplanation() throws Exception {
        assertBugReported(IgnoreMethodWithoutExplanation.class, detector, bugReporter);
    }
}
