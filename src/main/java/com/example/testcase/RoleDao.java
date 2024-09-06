package com.example.testcase;

import com.example.crawler.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author lx
 * @data 2022/11/28 9:08
 */
@Repository
public interface RoleDao extends JpaRepository<User, Long> {


}

