package com.revhire.service;

public interface ResumeService {

    int createResume(int jobSeekerId, String objective);

    int getActiveResumeId(int jobSeekerId);
}
