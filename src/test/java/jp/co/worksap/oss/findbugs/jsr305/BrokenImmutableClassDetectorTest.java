package jp.co.worksap.oss.findbugs.jsr305;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertBugReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertNoBugsReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.bugReporterForTesting;

import javax.annotation.meta.When;

import jp.co.worksap.oss.findbugs.jsr305.BrokenImmutableClassDetector.NonImmutableException;

import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
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

        JavaClass objectClass = Repository.lookupClass(Object.class);
        detector.checkImmutability(objectClass);
    }

    @Test
    public void testEnumIsImmutable() throws Exception {
        assertNoBugsReported(When.class, detector, bugReporter);

        JavaClass enumClass = Repository.lookupClass(When.class);
        detector.checkImmutability(enumClass);
    }

    @Test(expected = NonImmutableException.class)
    public void testMutableClass() throws Exception {
        assertBugReported(MutableClass.class, detector, bugReporter);

        JavaClass mutableClass = Repository.lookupClass(MutableClass.class);
        detector.checkImmutability(mutableClass);
    }

    @Test(expected = NonImmutableException.class)
    public void testClassExtendsMutableClass() throws Exception {
        assertBugReported(ExtendsMutableClass.class, detector, bugReporter);

        JavaClass mutableClass = Repository.lookupClass(ExtendsMutableClass.class);
        detector.checkImmutability(mutableClass);
    }
}
