import flashcards.MainKt;
import org.hyperskill.hstest.stage.StageTest;
import org.hyperskill.hstest.testcase.CheckResult;
import org.hyperskill.hstest.testcase.TestCase;

import java.util.Arrays;
import java.util.List;

public class FlashcardsTest extends StageTest {
    public FlashcardsTest() {
        super(MainKt.class);
    }

    @Override
    public List<TestCase> generate() {
        return Arrays.asList(new TestCase<>());
    }

    @Override
    public CheckResult check(String reply, Object attach) {
        int LINE_COUNT = 4;
        String FIRST_LINE = "Card:";
        String THIRD_LINE = "Definition:";

        String[] lines = reply.trim().split("\n");

        if (lines.length != LINE_COUNT) {
            return new CheckResult(
                false,
                "Your program prints " + LINE_COUNT +
                    " line" + (lines.length == 1 ? "" : "s") + "\n" +
                    LINE_COUNT + " lines were expected."
            );
        }

        String firstLine = lines[0];

        if (!firstLine.equals(FIRST_LINE)) {
            return new CheckResult(
                false,
                "Your first line is \"" + firstLine + "\" " +
                    "but \"" + FIRST_LINE + "\" was expected."
            );
        }

        String thirdLine = lines[2];

        if (!thirdLine.equals(THIRD_LINE)) {
            return new CheckResult(
                false,
                "Your third line is \"" + thirdLine + "\" " +
                    "but \"" + THIRD_LINE + "\" was expected."
            );
        }

        return CheckResult.correct();
    }
}
