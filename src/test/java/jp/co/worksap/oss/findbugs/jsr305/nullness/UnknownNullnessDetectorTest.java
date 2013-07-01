package jp.co.worksap.oss.findbugs.jsr305.nullness;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertBugReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertNoBugsReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.bugReporterForTesting;
import jp.co.worksap.oss.findbugs.jsr305.nullness.annotatedpackage.AnnotatedPackage;

import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.findbugs.BugReporter;

public class UnknownNullnessDetectorTest {
    private UnknownNullnessDetector detector;
    private BugReporter bugReporter;

    @Before
    public void setup() {
        bugReporter = bugReporterForTesting();
        detector = new UnknownNullnessDetector(bugReporter);
    }

    @Test
    public void testPrimitive() throws Exception {
        assertNoBugsReported(PrimitiveArgument.class, detector, bugReporter);
    }

    @Test
    public void testAnnotatedPackage() throws Exception {
        assertNoBugsReported(AnnotatedPackage.class, detector, bugReporter);
    }

    @Test
    public void testAnnotatedClass() throws Exception {
        assertNoBugsReported(AnnotatedClass.class, detector, bugReporter);
    }

    @Test
    public void testAnnotatedMethod() throws Exception {
        assertNoBugsReported(AnnotatedMethod.class, detector, bugReporter);
    }

    @Test
    public void testAnnotatedArgument() throws Exception {
        assertNoBugsReported(AnnotatedArgument.class, detector, bugReporter);
    }

    @Test
    public void testNoAnnotation() throws Exception {
        assertBugReported(NoAnnotation.class, detector, bugReporter);
    }

    @Test
    public void testAnnotatedReturnValue() throws Exception {
        assertNoBugsReported(AnnotatedReturnValue.class, detector, bugReporter);
    }

    @Test
    public void testUnannotatedReturnValue() throws Exception {
        assertBugReported(UnannotatedReturnValue.class, detector, bugReporter);
    }
}
