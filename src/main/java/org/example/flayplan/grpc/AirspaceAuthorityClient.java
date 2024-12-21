package org.example.flayplan.grpc;

import airspace.AirspaceAuthorityOuterClass;
import airspace.AirspaceAuthorityServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;


import java.util.Scanner;

public class  AirspaceAuthorityClient {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 9090)
                .usePlaintext()
                .build();

        AirspaceAuthorityServiceGrpc.AirspaceAuthorityServiceBlockingStub stub = AirspaceAuthorityServiceGrpc.newBlockingStub(channel);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите регион (или 'exit' для выхода): ");
            String region = scanner.nextLine();
            if ("exit".equalsIgnoreCase(region)) {
                break;
            }

            AirspaceAuthorityOuterClass.AirspaceFilterRequest request = AirspaceAuthorityOuterClass.AirspaceFilterRequest.newBuilder()
                    .setRegion(region)
                    .build();

            try {
                AirspaceAuthorityOuterClass.AirspaceAuthoritiesResponse response = stub.getAirspaceAuthorities(request);
                response.getAuthoritiesList().forEach(authority -> {
                    System.out.println("ID: " + authority.getId());
                    System.out.println("Name: " + authority.getName());
                    System.out.println("Region: " + authority.getRegion());
                    System.out.println("Contact Info: " + authority.getContactInfo());
                    System.out.println("------------------------------");
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        scanner.close();
        channel.shutdown();
    }
}
