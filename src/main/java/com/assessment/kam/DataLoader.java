package com.assessment.kam;


import com.assessment.kam.dto.InteractionDTO;
import com.assessment.kam.dto.LeadDTO;
import com.assessment.kam.dto.OrderDTO;
import com.assessment.kam.dto.PointOfContactDTO;
import com.assessment.kam.enums.CallFrequency;
import com.assessment.kam.enums.LeadStatus;
import com.assessment.kam.enums.POCRole;
import com.assessment.kam.factory.CallPlannerFactory;
import com.assessment.kam.factory.InteractionFactory;
import com.assessment.kam.factory.LeadFactory;
import com.assessment.kam.factory.PointOfContactFactory;
import com.assessment.kam.model.CallPlanner;
import com.assessment.kam.model.Interaction;
import com.assessment.kam.model.Lead;
import com.assessment.kam.model.PointOfContact;
import com.assessment.kam.repository.CallPlannerRepository;
import com.assessment.kam.repository.InteractionRepository;
import com.assessment.kam.repository.LeadRepository;
import com.assessment.kam.service.CallPlannerService;
import com.assessment.kam.service.InteractionService;
import com.assessment.kam.service.LeadService;
import com.assessment.kam.service.OrderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataLoader {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private CallPlannerRepository callPlannerRepository;

    @Autowired
    private InteractionRepository interactionRepository;

    @Autowired
    private CallPlannerService callPlannerService;

    @Autowired
    private InteractionService interactionService;

    @Autowired
    private LeadService leadService;

    @Autowired
    private OrderService orderService;

    @PostConstruct
    public void init() {
        loadMockData();
    }

    private void loadMockData() {
        PointOfContactDTO pointOfContact1 = new PointOfContactDTO("POC1", POCRole.ACCOUNTS_MANAGER, "poc1@email.com", "1111111111");
        PointOfContactDTO pointOfContact2 = new PointOfContactDTO("POC2", POCRole.MANAGER, "poc2@email.com", "2222222222");
        PointOfContactDTO pointOfContact3 = new PointOfContactDTO("POC3", POCRole.PURCHASING_MANAGER, "poc3@email.com", "3333333333");
        PointOfContactDTO pointOfContact4 = new PointOfContactDTO("POC4", POCRole.CHEF, "poc4@email.com", "4444444444");
        PointOfContactDTO pointOfContact5 = new PointOfContactDTO("POC5", POCRole.OWNER, "poc5@email.com", "5555555555");
        PointOfContactDTO pointOfContact6 = new PointOfContactDTO("POC6", POCRole.MANAGER, "poc6@email.com", "6666666666");

        LeadDTO lead1 = leadService.createLead("Lead 1","Address 1", LeadStatus.NEW, Arrays.asList(pointOfContact1, pointOfContact2));
        LeadDTO lead2 = leadService.createLead("Lead 2","Address 2", LeadStatus.NEW, Arrays.asList(pointOfContact3));
        LeadDTO lead3 = leadService.createLead("Lead 3","Address 3", LeadStatus.FOLLOWUP, Arrays.asList(pointOfContact4, pointOfContact5, pointOfContact6));

        // Create sample interactions
        interactionService.createInteraction(new InteractionDTO(lead1.getId(), "Initial meeting scheduled.", LocalDateTime.now().minusDays(1)));
        interactionService.createInteraction(new InteractionDTO(lead2.getId(), "Discussed menu customization.", LocalDateTime.now().minusDays(2)));

        // Create sample orders
        orderService.createOrder(new OrderDTO(LocalDateTime.now().minusDays(1), 500.0, lead1.getId()));
        orderService.createOrder(new OrderDTO(LocalDateTime.now().minusDays(0), 1200.0, lead2.getId()));
        orderService.createOrder(new OrderDTO(LocalDateTime.now().minusDays(2), 200.0, lead2.getId()));
        orderService.createOrder(new OrderDTO(LocalDateTime.now().minusDays(1), 800.0, lead3.getId()));
        orderService.createOrder(new OrderDTO(LocalDateTime.now().minusDays(1), 690.0, lead2.getId()));
        orderService.createOrder(new OrderDTO(LocalDateTime.now().minusDays(3), 160.0, lead2.getId()));
        orderService.createOrder(new OrderDTO(LocalDateTime.now().minusDays(1), 500.0, lead1.getId()));

        System.out.println("Mock data loaded successfully!");
    }
}

