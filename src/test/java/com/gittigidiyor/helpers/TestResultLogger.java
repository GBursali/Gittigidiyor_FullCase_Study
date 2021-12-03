package com.gittigidiyor.helpers;

import com.gittigidiyor.base.BaseTest;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestResultLogger implements TestWatcher, AfterAllCallback {
    private final List<TestResultStatus> testResultsStatus = new ArrayList<>();
    public static final Logger log = Logger.getLogger(BaseTest.class);
    private enum TestResultStatus {
        SUCCESSFUL, ABORTED, FAILED, DISABLED
    }
    @Override
    public void afterAll(ExtensionContext extensionContext){
        Map<TestResultStatus, Long> summary = testResultsStatus.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        log.info(String.format("Test result summary for %s %s",
                extensionContext.getDisplayName(),
                summary.toString()));
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        log.info(String.format("%s test disabled for %s",
                context.getDisplayName(),
                reason.orElse("unknown reasons")));
        testResultsStatus.add(TestResultStatus.DISABLED);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        log.info(String.format("%s test successful", context.getDisplayName()));

        testResultsStatus.add(TestResultStatus.SUCCESSFUL);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        log.info(String.format("%s test aborted with %s: ",
                context.getDisplayName(),
                cause.getMessage()));

        testResultsStatus.add(TestResultStatus.ABORTED);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        log.info(String.format("%s test failed with %s: ",
                context.getDisplayName(),
                cause.getMessage()));

        testResultsStatus.add(TestResultStatus.FAILED);
    }
}
