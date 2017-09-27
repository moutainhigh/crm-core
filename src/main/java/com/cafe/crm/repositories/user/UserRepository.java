package com.cafe.crm.repositories.user;


import com.cafe.crm.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmailAndEnabledIsTrueAndCompanyId(String email, Long companyId);

	User findByPhoneAndEnabledIsTrueAndCompanyId(String phone, Long companyId);

	List<User> findByEnabledIsTrueAndCompanyId(Long companyId);

	List<User> findByPositionsIdAndEnabledIsTrueAndCompanyId(Long positionId, Long companyId);

	List<User> findByPositionsIdAndCompanyId(Long positionId, Long companyId);

	List<User> findByRolesNameAndCompanyId(String roleName, Long companyId);

	List<User> findByEmailOrPhoneAndCompanyId(String email, String phone, Long companyId);

	List<User> findByIdInAndCompanyId(long[] ids, Long companyId);

	@Query("SELECT u FROM User u where u.email = :username OR u.phone = :username")
	User findByUsername(@Param("username") String username);
}
