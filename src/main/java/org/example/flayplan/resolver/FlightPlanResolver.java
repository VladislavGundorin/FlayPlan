package org.example.flayplan.resolver;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import org.example.flayplan.model.FlightPlan;
import org.example.flayplan.service.FlightPlanService;

import java.util.List;

@DgsComponent
public class FlightPlanResolver {

    private final FlightPlanService flightPlanService;

    public FlightPlanResolver(FlightPlanService flightPlanService) {
        this.flightPlanService = flightPlanService;
    }

    @DgsQuery
    public List<FlightPlan> flightPlans() {
        return flightPlanService.getAllFlightPlans();
    }
}
//query {
//    flightPlans {
//        id
//        flightNumber
//        airline
//        pilot {
//            name
//        }
//    }
//}