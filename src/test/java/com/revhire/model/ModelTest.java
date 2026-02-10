package com.revhire.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.sql.Timestamp;
import java.sql.Date;

public class ModelTest {

    @Test
    public void testUser() {
        User u = new User();
        u.setUserId(1);
        u.setEmail("test@test.com");
        u.setPassword("pass");
        u.setRole("ADMIN");
        u.setIsActive("Y");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        u.setCreatedAt(now);
        u.setSecurityQuestion("Q");
        u.setSecurityAnswer("A");

        assertEquals(1, u.getUserId());
        assertEquals("test@test.com", u.getEmail());
        assertEquals("pass", u.getPassword());
        assertEquals("ADMIN", u.getRole());
        assertEquals("Y", u.getIsActive());
        assertEquals(now, u.getCreatedAt());
        assertEquals("Q", u.getSecurityQuestion());
        assertEquals("A", u.getSecurityAnswer());
    }

    @Test
    public void testJob() {
        Job j = new Job();
        j.setJobId(1);
        j.setCompanyId(10);
        j.setTitle("Dev");
        j.setDescription("Desc");
        j.setSkills("Java");
        j.setMinExperience(2);
        j.setLocation("In");
        j.setSalaryMin(100.0);
        j.setSalaryMax(200.0);
        j.setJobType("Full");
        Date d = new Date(System.currentTimeMillis());
        j.setDeadline(d);
        j.setStatus("Open");
        j.setEducationRequired("BS");

        assertEquals(1, j.getJobId());
        assertEquals(10, j.getCompanyId());
        assertEquals("Dev", j.getTitle());
        assertEquals("Desc", j.getDescription());
        assertEquals("Java", j.getSkills());
        assertEquals(2, j.getMinExperience());
        assertEquals("In", j.getLocation());
        assertEquals(100.0, j.getSalaryMin());
        assertEquals(200.0, j.getSalaryMax());
        assertEquals("Full", j.getJobType());
        assertEquals(d, j.getDeadline());
        assertEquals("Open", j.getStatus());
        assertEquals("BS", j.getEducationRequired());
    }

    @Test
    public void testCompany() {
        Company c = new Company();
        c.setCompanyId(1);
        c.setEmployerId(2);
        c.setCompanyName("Name");
        c.setIndustry("Ind");
        c.setCompanySize(100);
        c.setDescription("Desc");
        c.setWebsite("Web");
        c.setLocation("Loc");
        Timestamp now = new Timestamp(System.currentTimeMillis());
        c.setCreatedAt(now);

        assertEquals(1, c.getCompanyId());
        assertEquals(2, c.getEmployerId());
        assertEquals("Name", c.getCompanyName());
        assertEquals("Ind", c.getIndustry());
        assertEquals(100, c.getCompanySize());
        assertEquals("Desc", c.getDescription());
        assertEquals("Web", c.getWebsite());
        assertEquals("Loc", c.getLocation());
        assertEquals(now, c.getCreatedAt());
    }

    @Test
    public void testJobSeeker() {
        JobSeeker js = new JobSeeker();
        js.setJobSeekerId(1);
        js.setUserId(2);
        js.setFullName("Full");
        js.setPhone("123");
        js.setTotalExperience(5);

        assertEquals(1, js.getJobSeekerId());
        assertEquals(2, js.getUserId());
        assertEquals("Full", js.getFullName());
        assertEquals("123", js.getPhone());
        assertEquals(5, js.getTotalExperience());
    }

    @Test
    public void testApplication() {
        Application a = new Application();
        a.setApplicationId(1);
        a.setJobId(2);
        a.setJobSeekerId(3);
        a.setResumeId(4);
        a.setStatus("Pending");
        Date d = new Date(System.currentTimeMillis());
        a.setAppliedDate(d);
        a.setEmployerComment("Comment");

        assertEquals(1, a.getApplicationId());
        assertEquals(2, a.getJobId());
        assertEquals(3, a.getJobSeekerId());
        assertEquals(4, a.getResumeId());
        assertEquals("Pending", a.getStatus());
        assertEquals(d, a.getAppliedDate());
        assertEquals("Comment", a.getEmployerComment());
    }

    @Test
    public void testEducation() {
        Education e = new Education();
        e.setEducationId(1);
        e.setJobSeekerId(2);
        e.setInstitution("Inst");
        e.setDegree("Deg");
        e.setStartYear(2010);
        e.setEndYear(2014);

        assertEquals(1, e.getEducationId());
        assertEquals(2, e.getJobSeekerId());
        assertEquals("Inst", e.getInstitution());
        assertEquals("Deg", e.getDegree());
        assertEquals(2010, e.getStartYear());
        assertEquals(2014, e.getEndYear());
    }

    @Test
    public void testExperience() {
        Experience ex = new Experience();
        ex.setExperienceId(1);
        ex.setJobSeekerId(2);
        ex.setCompanyName("Comp");
        ex.setRole("Role");
        Date start = new Date(System.currentTimeMillis());
        Date end = new Date(System.currentTimeMillis());
        ex.setStartDate(start);
        ex.setEndDate(end);
        ex.setDescription("Desc");

        assertEquals(1, ex.getExperienceId());
        assertEquals(2, ex.getJobSeekerId());
        assertEquals("Comp", ex.getCompanyName());
        assertEquals("Role", ex.getRole());
        assertEquals(start, ex.getStartDate());
        assertEquals(end, ex.getEndDate());
        assertEquals("Desc", ex.getDescription());
    }

    @Test
    public void testSkill() {
        Skill s = new Skill();
        s.setSkillId(1);
        s.setJobSeekerId(2);
        s.setSkillName("Skill");
        s.setProficiency("Prof");

        assertEquals(1, s.getSkillId());
        assertEquals(2, s.getJobSeekerId());
        assertEquals("Skill", s.getSkillName());
        assertEquals("Prof", s.getProficiency());
    }

    @Test
    public void testEmployer() {
        Employer e = new Employer();
        e.setEmployerId(1);
        e.setUserId(2);

        assertEquals(1, e.getEmployerId());
        assertEquals(2, e.getUserId());
    }
}
