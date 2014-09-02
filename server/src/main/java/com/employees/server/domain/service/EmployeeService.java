package com.employees.server.domain.service;

import javax.inject.Inject;

import com.employees.server.domain.entity.Employee;
import com.employees.server.domain.repository.EmployeeRepository;
import com.employees.server.domain.vo.Function;

public class EmployeeService {

	@Inject
	private EmployeeRepository dummyRepository;

	public void promove(Long id, Function function) {

		Employee employee = dummyRepository.getById(id);

		Double salary = 0d;

		switch (function) {
			case DEVELOPER:
				salary = 1000d;
				break;
			case PO:
				salary = 5000d;
				break;
			case MANAGER:
				salary = 10000d;
				break;
		}

		employee.setSalary(salary);
		employee.setFunction(function);

		dummyRepository.put(employee);

	}

}
