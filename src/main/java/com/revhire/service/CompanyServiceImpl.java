package com.revhire.service;

import com.revhire.dao.CompanyDao;
import com.revhire.dao.CompanyDaoImpl;
import com.revhire.model.Company;

public class CompanyServiceImpl implements CompanyService {

    private CompanyDao dao;
    private NotificationService notificationService;

    public CompanyServiceImpl() {
        this.dao = new CompanyDaoImpl();
        this.notificationService = new NotificationServiceImpl();
    }

    public CompanyServiceImpl(CompanyDao dao, NotificationService notificationService) {
        this.dao = dao;
        this.notificationService = notificationService;
    }

    @Override
    public Company getCompany(int companyId) {
        return dao.getCompanyById(companyId);
    }

    @Override
    public boolean updateCompany(Company company) {
        boolean updated = dao.updateCompany(company);
        if (updated) {
            notificationService.notifyEmployer(company.getCompanyId(), "Company profile updated successfully.");
        }
        return updated;
    }
}
