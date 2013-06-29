package jp.co.worksap.oss.findbugs.jpa;

import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertBugReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.assertNoBugsReported;
import static com.youdevise.fbplugins.tdd4fb.DetectorAssert.bugReporterForTesting;
import jp.co.worksap.oss.findbugs.jpa.LongIndexNameDetector;

import org.junit.Before;
import org.junit.Test;

import edu.umd.cs.findbugs.BugReporter;

public class IndexNameLengthTest {
    private BugReporter bugReporter;
    private LongIndexNameDetector detector;

    @Before
    public void setup() {
        bugReporter = bugReporterForTesting();
        detector = new LongIndexNameDetector(bugReporter);
    }

    @Test
    public void testShortNameWithHibernate() throws Exception {
        assertNoBugsReported(ShortIndexNameForHibernate.class, detector, bugReporter);
    }

    @Test
    public void testLongNameWithHibernate() throws Exception {
        assertBugReported(LongIndexNameForHibernate.class, detector, bugReporter);
    }

    @Test
    public void testShortNameWithOpenJPA() throws Exception {
        assertNoBugsReported(ShortIndexNameForOpenJPA.class, detector, bugReporter);
    }

    @Test
    public void testLongNameWithOpenJPA() throws Exception {
        assertBugReported(LongIndexNameForOpenJPA.class, detector, bugReporter);
    }
}
