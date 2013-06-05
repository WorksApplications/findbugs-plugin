package jp.co.worksap.oss.findbugs.jsr305;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertBugReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertNoBugsReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.bugReporterForTesting;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.ofType;

import javax.annotation.meta.When;

import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.findbugs.BugReporter;

public class BrokenImmutableClassDetectorTest {

    private BrokenImmutableClassDetector detector;
    private BugReporter bugReporter;

    @Before
    public void setup() {
        bugReporter = bugReporterForTesting();
        detector = new BrokenImmutableClassDetector(bugReporter);
    }

    @Test
    public void testObjectIsImmutable() throws Exception {
        assertNoBugsReported(Object.class, detector, bugReporter);
    }

    @Test
    public void testEnumIsImmutable() throws Exception {
        assertNoBugsReported(When.class, detector, bugReporter);
    }

    @Test
    public void testMutableClass() throws Exception {
        assertBugReported(MutableClass.class, detector, bugReporter, ofType("IMMUTABLE_CLASS_SHOULD_BE_FINAL"));
        assertBugReported(MutableClass.class, detector, bugReporter, ofType("BROKEN_IMMUTABILITY"));
    }

    @Test
    public void testClassExtendsMutableClass() throws Exception {
        assertBugReported(ExtendsMutableClass.class, detector, bugReporter, ofType("BROKEN_IMMUTABILITY"));
    }
}
