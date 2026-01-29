package com.revhire.service;

import com.revhire.dao.CompanyDao;
import com.revhire.dao.CompanyDaoImpl;
import com.revhire.model.Company;

public class CompanyServiceImpl implements CompanyService {

    private CompanyDao dao = new CompanyDaoImpl();

    @Override
    public Company getCompany(int companyId) {
        return dao.getCompanyById(companyId);
    }

    @Override
    public boolean updateCompany(Company company) {
        boolean updated = dao.updateCompany(company);
        if (updated) {
            NotificationService ns = new NotificationServiceImpl();
            ns.notifyEmployer(company.getCompanyId(), "Company profile updated successfully.");
        }
        return updated;
    }
}
