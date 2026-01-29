package com.revhire.dao;

public interface CertificationDao {

    boolean addCertification(
        int jobSeekerId,
        String certName,
        String issuedBy,
        int year
    );
}
