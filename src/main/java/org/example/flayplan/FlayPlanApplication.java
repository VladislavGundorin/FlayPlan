package org.example.flayplan;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.example.flayplan.grpc.AirspaceAuthorityGrpcServiceImpl;
import org.example.flayplan.service.AirspaceAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlayPlanApplication implements CommandLineRunner {

    @Autowired
    private AirspaceAuthorityService airspaceAuthorityService;

    private Server grpcServer;

    public static void main(String[] args) {
        SpringApplication.run(FlayPlanApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        grpcServer = ServerBuilder.forPort(9091)
                .addService(new AirspaceAuthorityGrpcServiceImpl(airspaceAuthorityService))
                .build()
                .start();

        System.out.println("FlayPlan gRPC server started on port 9091...");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                grpcServer.shutdown().awaitTermination();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        grpcServer.awaitTermination();
    }
}
