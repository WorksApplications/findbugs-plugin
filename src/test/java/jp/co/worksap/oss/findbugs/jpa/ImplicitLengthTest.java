package jp.co.worksap.oss.findbugs.jpa;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertBugReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertNoBugsReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.bugReporterForTesting;

import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.findbugs.BugReporter;

public class ImplicitLengthTest {
    private BugReporter bugReporter;
    private ImplicitLengthDetector detector;

    @Before
    public void before() {
        this.bugReporter = bugReporterForTesting();
        this.detector = new ImplicitLengthDetector(bugReporter);
    }

    @Test
    public void testImplicitLength() throws Exception {
        assertNoBugsReported(ColumnWithLength.class, detector,
                bugReporter);
    }

    @Test
    public void testExplicitLength() throws Exception {
        assertBugReported(ColumnWithoutElement.class, detector,
                bugReporter);
    }
}
