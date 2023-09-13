//package com.tnt.ecommeracemarketplace.service;
//import java.lang.management.ManagementFactory;
//import java.lang.management.ThreadInfo;
//import java.lang.management.ThreadMXBean;
//
//public class DeadlockDetector {
//    public static void detectDeadlocks() {
//        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
//        long[] threadIds = threadBean.findDeadlockedThreads();
//
//        if (threadIds != null) {
//            System.out.println("Deadlock detected!");
//
//            for (long threadId : threadIds) {
//                ThreadInfo threadInfo = threadBean.getThreadInfo(threadId);
//                StackTraceElement[] stackTrace = threadInfo.getStackTrace();
//                System.out.println("Thread ID: " + threadId + " is deadlocked.");
//                for (StackTraceElement element : stackTrace) {
//                    System.out.println(element);
//                }
//            }
//        } else {
//            System.out.println("No deadlocks detected.");
//        }
//    }
//}