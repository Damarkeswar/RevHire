package com.revhire;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("RevHire Application Full Test Suite")
@SelectPackages({ "com.revhire.service", "com.revhire.model" })
public class RevHireTestSuite {
    // This class remains empty, it serves only as a holder for the above
    // annotations
}
