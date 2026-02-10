package com.revhire.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revhire.dao.CertificationDao;

@ExtendWith(MockitoExtension.class)
public class CertificationServiceTest {

    @Mock
    private CertificationDao certificationDao;

    private CertificationService certificationService;

    @BeforeEach
    public void setup() {
        certificationService = new CertificationServiceImpl(certificationDao);
    }

    @Test
    public void testAddCertificationSuccess() {
        int jsId = 1;
        String certName = "Java Master";
        String issuedBy = "Oracle";
        int year = 2023;

        when(certificationDao.addCertification(jsId, certName, issuedBy, year)).thenReturn(true);

        boolean result = certificationService.addCertification(jsId, certName, issuedBy, year);
        assertTrue(result);
    }

    @Test
    public void testAddCertificationEmptyName() {
        int jsId = 1;
        String certName = "";
        String issuedBy = "Oracle";
        int year = 2023;

        boolean result = certificationService.addCertification(jsId, certName, issuedBy, year);
        assertFalse(result);
    }

    @Test
    public void testAddCertificationNullName() {
        int jsId = 1;
        String certName = null;
        String issuedBy = "Oracle";
        int year = 2023;

        boolean result = certificationService.addCertification(jsId, certName, issuedBy, year);
        assertFalse(result);
    }
}
