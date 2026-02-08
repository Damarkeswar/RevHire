package com.revhire.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.revhire.dao.CompanyDao;
import com.revhire.model.Company;

public class CompanyServiceTest {

    @Mock
    private CompanyDao companyDao;

    @Mock
    private NotificationService notificationService;

    @InjectMocks
    private CompanyServiceImpl companyService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCompany() {
        Company mockCompany = new Company();
        mockCompany.setCompanyId(1);
        mockCompany.setCompanyName("Test Corp");

        when(companyDao.getCompanyById(1)).thenReturn(mockCompany);

        Company result = companyService.getCompany(1);
        assertNotNull(result);
        assertEquals("Test Corp", result.getCompanyName());
    }

    @Test
    public void testUpdateCompany() {
        Company company = new Company();
        company.setCompanyId(1);
        company.setCompanyName("Updated Corp");

        when(companyDao.updateCompany(company)).thenReturn(true);

        boolean result = companyService.updateCompany(company);

        assertTrue(result);
        verify(notificationService).notifyEmployer(eq(1), contains("updated successfully"));
    }
}
