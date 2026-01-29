package com.revhire.dao;

import com.revhire.model.Company;

public interface CompanyDao {

    int createCompany(Company company);

    Company getCompanyByEmployerId(int employerId);

    boolean updateCompany(Company company);

    int getCompanyIdByUserId(int userId);

    Company getCompanyById(int companyId);
}
