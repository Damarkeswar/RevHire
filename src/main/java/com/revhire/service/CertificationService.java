package com.revhire.service;

public interface CertificationService {

    boolean addCertification(
        int jobSeekerId,
        String certName,
        String issuedBy,
        int year
    );
}
