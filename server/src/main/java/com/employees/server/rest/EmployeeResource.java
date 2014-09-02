package com.employees.server.rest;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.APPLICATION_XML;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import com.employees.server.domain.entity.Employee;
import com.employees.server.domain.repository.EmployeeRepository;
import com.employees.server.domain.service.EmployeeService;
import com.employees.server.domain.vo.Function;

@Path("/employees")
public class EmployeeResource {

	@Inject
	private EmployeeRepository repository;

	@Inject
	private EmployeeService service;

	@POST
	@Consumes(value = { APPLICATION_JSON, APPLICATION_XML })
	@Produces(value = { APPLICATION_JSON, APPLICATION_XML })
	public Employee create(Employee entity) {
		return repository.put(entity);
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Long id) {
		repository.removeById(id);
	}

	@GET
	@Path("/{id}")
	@Produces(value = { APPLICATION_JSON, APPLICATION_XML })
	public Employee get(@PathParam("id") Long id) {
		return repository.getById(id);
	}

	@GET
	@Produces(value = { APPLICATION_JSON, APPLICATION_XML })
	public List<Employee> list(
			@QueryParam("firstResult") Integer firstResult,
			@QueryParam("maxResults") Integer maxResults) {
		return repository.list(firstResult, maxResults);
	}


	@PUT
	@Path("/{id}")
	@Consumes(value = { APPLICATION_JSON, APPLICATION_XML })
	@Produces(value = { APPLICATION_JSON, APPLICATION_XML })
	public Employee update(@PathParam("id") Long id, Employee entity) {
		return repository.put(entity);
	}

}
