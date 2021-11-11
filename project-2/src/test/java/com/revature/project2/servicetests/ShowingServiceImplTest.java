package com.revature.project2.servicetests;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.revature.project2.model.Showing;
import com.revature.project2.repo.ShowingRepository;
import com.revature.project2.service.ShowingService;
import com.revature.project2.service.ShowingServiceImpl;

public class ShowingServiceImplTest {
	
	//6 tests: 1 failed, 5 Passed
	//TODO: review failed test - rewrite if necessary
	
	@Mock
	private ShowingRepository showRepo;
	
	@InjectMocks
	private ShowingService showService = ShowingServiceImpl.getServ();
	
	@BeforeEach
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	@Order(1)
	public void findAllTest() {
		List<Showing> list = new ArrayList<Showing>();
		list.add(new Showing(1, 1, "10:30", "45")); // Showing(showingId, theaterId, time, currentCapacity)
		list.add(new Showing(1, 2, "12:30", "45"));
		list.add(new Showing(1, 3, "1:30", "45"));
		
		Mockito.when(showRepo.findAll()).thenReturn(list);
		List<Showing> result = showService.findAll();

		Assertions.assertEquals(3, result.size());
		verify(showRepo,times(1)).findAll();
	}
	
	
	@Test
	@Order(2)
	public void findByTheaterIdTest() {
		List<Showing> list = new ArrayList<Showing>();
		Showing temp = new Showing(1, 3, "1:30", "45");
		list.add(temp);
		
		when(showRepo.findByTheaterId(3)).thenReturn(list);
		List<Showing> test = showService.findByTheaterId(3);
		Showing target = test.get(0);
		
		Assertions.assertEquals("1:30", target.getTime());
		Assertions.assertEquals("45", target.getCurrentCapacity());
		verify(showRepo,times(1)).findByTheaterId(3);
		
	}
	
	@Test
	@Order(3)
	public void findByIdTest() {
		Optional<Showing> temp = Optional.ofNullable(new Showing(1,2,"10:30","45"));
		when(showRepo.findById(1)).thenReturn(temp);
		
		Showing test = showService.findById(1);
		
		
		Assertions.assertEquals("10:30", test.getTime());
		Assertions.assertEquals("45", test.getCurrentCapacity());

	}
	
	@Test
	@Order(4)
	public void saveTest() {
		Showing test = new Showing(1, 1, "10:30", "45");
		showService.save(test);
		
		Mockito.verify(showRepo,times(1)).save(test);
	}
	
	@Test
	@Order(5)
	public void updateTest() {
		Showing test = new Showing(1, 1, "10:30", "45");
		test.setCurrentCapacity("35");
		showService.update(1, test);
		
		Mockito.verify(showRepo,times(1)).save(test);
	}
	
	//Failed test:: Wanted but not invoked: showRepo.delete(test) - review and rewrite
	@Test
	@Order(6)
	public void deleteTest() {
		Showing test = new Showing(1, 1, "10:30", "45");
		showService.delete(1);
		
		Mockito.verify(showRepo,times(1)).delete(test);
	}
}