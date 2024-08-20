package com.dkay229.msql.server;


import com.dkay229.msql.proto.DatabaseServiceGrpc;
import com.dkay229.msql.proto.Dbserver;
import io.grpc.Server;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

import static io.grpc.ServerBuilder.forPort;

@Slf4j
public class DatabaseServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = forPort(8080)
                .addService(new DatabaseServiceImpl())
                .build();
        log.info("DatabaseServer started, listening on port 8080");
        server.start();
        server.awaitTermination();
    }

    static class DatabaseServiceImpl extends DatabaseServiceGrpc.DatabaseServiceImplBase {
        ConnectedUsers connectedUsers = new ConnectedUsers();

        @Override
        public void executeQuery(Dbserver.ExecuteQueryRequest request, StreamObserver<Dbserver.ExecuteQueryResponse> responseObserver) {
            // Simulate a database query. In a real-world scenario, this would involve database access.
            log.info("Received SQL Query: " + request.getSql());


            Dbserver.ExecuteQueryResponse response = Dbserver.ExecuteQueryResponse.newBuilder()
                    .setRowMetadata(Dbserver.RowMetadata.newBuilder().addColumns(
                                    Dbserver.ColumnMetadata.newBuilder()
                                            .setName("column1")
                                            .setType(Dbserver.ColumnMetadata.TypeEnum.STRING)
                                            .setSize(50).build()
                            )
                            .build()).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        @Override
        public void login(Dbserver.LoginRequest loginRequest, StreamObserver<Dbserver.LoginResponse> responseObserver) {
            try {
                ConnectedUser connectedUser = connectedUsers.login(loginRequest.getUserId(), loginRequest.getToken());
                Dbserver.LoginResponse loginResponse = Dbserver.LoginResponse.newBuilder()
                        .setConnectionId(connectedUser.getConnectionId())
                        .setConnectionKey(connectedUser.getConnectionKey())
                        .build();
                responseObserver.onNext(loginResponse);
                log.info("logged in "+connectedUser);
            } catch (Exception e) {
                responseObserver.onNext(Dbserver.LoginResponse.newBuilder().setErrorMessage(e.toString()).build());
                log.error("user "+loginRequest.getUserId(),e);
            }
            responseObserver.onCompleted();
        }

    }
}

