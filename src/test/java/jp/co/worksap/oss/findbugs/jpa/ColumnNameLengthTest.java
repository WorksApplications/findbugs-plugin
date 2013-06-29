package jp.co.worksap.oss.findbugs.jpa;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertBugReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertNoBugsReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.bugReporterForTesting;
import jp.co.worksap.oss.findbugs.jpa.LongColumnNameDetector;

import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.findbugs.BugReporter;

public class ColumnNameLengthTest {
    private BugReporter bugReporter;
    private LongColumnNameDetector detector;

    @Before
    public void setup() {
        bugReporter = bugReporterForTesting();
        detector = new LongColumnNameDetector(bugReporter);
    }

    @Test
    public void testShortName() throws Exception {
        assertNoBugsReported(ShortColumnName.class, detector, bugReporter);
    }

    @Test
    public void testShortNameWithoutAnnotationParameter() throws Exception {
        assertNoBugsReported(ShortColumnNameWithoutAnnotationParameter.class, detector, bugReporter);
    }

    @Test
    public void testLongName() throws Exception {
        assertBugReported(LongColumnName.class, detector, bugReporter);
    }

    @Test
    public void testLongNameWithoutAnnotationParameter() throws Exception {
        assertBugReported(LongColumnNameWithoutAnnotationParameter.class, detector, bugReporter);
    }

    @Test
    public void testLongColumnNameByAnnotatedMethod() throws Exception {
        assertBugReported(LongColumnNameByAnnotatedMethod.class, detector, bugReporter);
    }
}
