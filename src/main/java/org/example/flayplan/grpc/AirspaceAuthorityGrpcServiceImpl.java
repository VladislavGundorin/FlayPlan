package org.example.flayplan.grpc;

import airspace.AirspaceAuthorityOuterClass;
import airspace.AirspaceAuthorityServiceGrpc;
import io.grpc.stub.StreamObserver;
import org.example.dtos.AirspaceAuthorityDTO;
import org.example.flayplan.service.AirspaceAuthorityService;

import java.util.List;

public class AirspaceAuthorityGrpcServiceImpl extends AirspaceAuthorityServiceGrpc.AirspaceAuthorityServiceImplBase {

    private final AirspaceAuthorityService airspaceAuthorityService;

    public AirspaceAuthorityGrpcServiceImpl(AirspaceAuthorityService airspaceAuthorityService) {
        this.airspaceAuthorityService = airspaceAuthorityService;
    }

    @Override
    public void getAirspaceAuthorities(AirspaceAuthorityOuterClass.AirspaceFilterRequest request,
                                       StreamObserver<AirspaceAuthorityOuterClass.AirspaceAuthoritiesResponse> responseObserver) {
        try {
            List<AirspaceAuthorityDTO> authorities = airspaceAuthorityService.getAllAirspaceAuthorities(request.getRegion());

            AirspaceAuthorityOuterClass.AirspaceAuthoritiesResponse.Builder responseBuilder =
                    AirspaceAuthorityOuterClass.AirspaceAuthoritiesResponse.newBuilder();

            for (AirspaceAuthorityDTO authority : authorities) {
                responseBuilder.addAuthorities(
                        AirspaceAuthorityOuterClass.AirspaceAuthority.newBuilder()
                                .setId(authority.getId().toString())
                                .setName(authority.getName())
                                .setRegion(authority.getRegion())
                                .setContactInfo(authority.getContactInfo())
                                .build());
            }

            responseObserver.onNext(responseBuilder.build());
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onCompleted();
        }
    }
}
