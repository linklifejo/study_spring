package com.hanul.hello.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hanul.hello.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	 List<Customer> findByOrderByCidDesc(); //id순으로 정렬한 목록
	 List<Customer> findByOrderByNameAsc(); //name 순으로 정렬한 목록
	 Customer findByCid(int id); //선택한 id에 해당하는 고객
	 List<Customer> findByName(String name);//일치하는 이름으로 검색한 결과 목록
	 List<Customer> findByNameContaining(String name);//이름이 포함된 검색한 결과 목록
	 List<Customer> findByNameContainingOrderByName(String name);//이름이 포함된 검색한 결과 name 순으로 정렬한 목록

}
