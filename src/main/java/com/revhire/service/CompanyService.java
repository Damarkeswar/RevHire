package com.revhire.service;

import com.revhire.model.Company;

public interface CompanyService {

    Company getCompany(int companyId);

    boolean updateCompany(Company company);
}
