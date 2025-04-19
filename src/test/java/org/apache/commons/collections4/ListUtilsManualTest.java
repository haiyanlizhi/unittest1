package org.apache.commons.collections4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListUtilsManualTest {

    private static int testCount = 0;
    private static int passCount = 0;

    public static void main(String[] args) {
        // 正常功能测试
        testListIntersection();
        testListSubtraction();
        testListPartitioning();

        // 错误注入测试
        testIntersectionErrorCases();
        testSubtractErrorCases();
        testPartitionErrorCases();

        // 生成报告
        printTestSummary();
    }

    //=== 正常功能测试 ============================================//

    private static void testListIntersection() {
        beginTest("List.intersection()");

        // 测试用例1：常规交集
        assertEqual(
                "正常列表交集",
                Arrays.asList("B", "C"),
                ListUtils.intersection(
                        Arrays.asList("A", "B", "C"),
                        Arrays.asList("B", "C", "D")
                )
        );

        // 测试用例2：空交集
        assertEqual(
                "无交集列表",
                Collections.emptyList(),
                ListUtils.intersection(
                        Arrays.asList(1, 2, 3),
                        Arrays.asList(4, 5, 6)
                )
        );
    }

    private static void testListSubtraction() {
        beginTest("List.subtract()");

        assertEqual(
                "常规元素移除",
                Arrays.asList("A", "C"),
                ListUtils.subtract(
                        Arrays.asList("A", "B", "C"),
                        Arrays.asList("B")
                )
        );

        assertEqual(
                "移除不存在的元素",
                Arrays.asList("A", "B", "C"),
                ListUtils.subtract(
                        Arrays.asList("A", "B", "C"),
                        Arrays.asList("X")
                )
        );
    }

    private static void testListPartitioning() {
        beginTest("List.partition()");

        List<List<Integer>> partitions = ListUtils.partition(
                Arrays.asList(1, 2, 3, 4, 5), 2);

        assertEqual("分区数量", 3, partitions.size());
        assertEqual("第一分区", Arrays.asList(1, 2), partitions.get(0));
        assertEqual("第二分区", Arrays.asList(3, 4), partitions.get(1));
        assertEqual("第三分区", Arrays.asList(5), partitions.get(2));
    }

    //=== 错误注入测试 ============================================//

    private static void testIntersectionErrorCases() {
        beginTest("Intersection错误场景");

        // 错误注入1：修改预期结果
        assertNotEqual(
                "错误预期检测",
                Arrays.asList("B"), // 错误预期
                ListUtils.intersection(
                        Arrays.asList("A", "B", "C"),
                        Arrays.asList("B", "C", "D")
                )
        );

        // 错误注入2：null输入
        assertThrows(
                "Null列表检测",
                NullPointerException.class,
                () -> ListUtils.intersection(null, new ArrayList<>())
        );
    }

    private static void testSubtractErrorCases() {
        beginTest("Subtract错误场景");

        assertThrows(
                "Null输入检测",
                NullPointerException.class,
                () -> ListUtils.subtract(null, Collections.emptyList())
        );
    }

    private static void testPartitionErrorCases() {
        beginTest("Partition错误场景");

        assertThrows(
                "非法分区大小",
                IllegalArgumentException.class,
                () -> ListUtils.partition(Arrays.asList(1, 2, 3), 0)
        );

        assertThrows(
                "负分区大小",
                IllegalArgumentException.class,
                () -> ListUtils.partition(Arrays.asList(1, 2, 3), -1)
        );
    }

    //=== 测试工具方法 ============================================//

    private static void beginTest(String testSuite) {
        System.out.println("\n=== 测试组: " + testSuite + " ===");
    }

    private static <T> void assertEqual(String description, T expected, T actual) {
        testCount++;
        if (expected.equals(actual)) {
            pass(description);
        } else {
            fail(description, "Expected: " + expected + ", Actual: " + actual);
        }
    }

    private static <T> void assertNotEqual(String description, T unexpected, T actual) {
        testCount++;
        if (!unexpected.equals(actual)) {
            pass(description);
        } else {
            fail(description, "Unexpected: " + unexpected + ", Actual: " + actual);
        }
    }

    private static <T extends Throwable> void assertThrows(
            String description,
            Class<T> expectedException,
            Runnable testCode
    ) {
        testCount++;
        try {
            testCode.run();
            fail(description, "Expected " + expectedException.getSimpleName() + " but no exception was thrown");
        } catch (Throwable e) {
            if (expectedException.isInstance(e)) {
                pass(description);
            } else {
                fail(description, "Expected " + expectedException.getSimpleName() + " but got " + e.getClass().getSimpleName());
            }
        }
    }

    private static void pass(String description) {
        passCount++;
        System.out.printf("[✓] %s\n", description);
    }

    private static void fail(String description, String details) {
        System.err.printf("[×] %s - %s\n", description, details);
    }

    private static void printTestSummary() {
        System.out.println("\n=== 综合测试结果 ===");
        System.out.printf("执行测试: %d\n通过测试: %d\n失败测试: %d\n",
                testCount, passCount, testCount - passCount);
        System.out.printf("通过率: %.1f%%\n", (passCount * 100.0 / testCount));
    }
}