package com.revhire.service;

import com.revhire.dao.CertificationDao;
import com.revhire.dao.CertificationDaoImpl;

public class CertificationServiceImpl implements CertificationService {

    private CertificationDao certificationDao =
            new CertificationDaoImpl();

    @Override
    public boolean addCertification(int jobSeekerId,
                                    String certName,
                                    String issuedBy,
                                    int year) {

        if (certName == null || certName.isEmpty()) {
            System.out.println("❌ Certification name cannot be empty");
            return false;
        }

        return certificationDao.addCertification(
            jobSeekerId, certName, issuedBy, year
        );
    }
}
