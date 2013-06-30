package jp.co.worksap.oss.findbugs.jsr305.nullness;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.*;
import jp.co.worksap.oss.findbugs.jsr305.nullness.UnknownNullnessDetector;
import jp.co.worksap.oss.findbugs.jsr305.nullness.annotatedpackage.AnnotatedPackage;

import org.junit.Before;
import org.junit.Ignore;
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

    @Ignore("Cannot pass: It might stand on problem of FindBugs.")
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
}
