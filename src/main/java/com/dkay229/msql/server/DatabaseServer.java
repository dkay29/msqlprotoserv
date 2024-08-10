package com.dkay229.msql.server;




import com.dkay229.msql.proto.DatabaseServiceGrpc;
import io.grpc.Server;


import com.dkay229.msql.proto.Dbserver;
import io.grpc.stub.StreamObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.grpc.ServerBuilder.forPort;


public class DatabaseServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = forPort(8080)
                .addService(new DatabaseServiceImpl())
                .build();

        System.out.println("Server started, listening on port 8080");
        server.start();
        server.awaitTermination();
    }

    static class DatabaseServiceImpl extends DatabaseServiceGrpc.DatabaseServiceImplBase {

        @Override
        public void executeQuery(Dbserver.QueryRequest request, StreamObserver<Dbserver.QueryResponse> responseObserver) {
            // Simulate a database query. In a real-world scenario, this would involve database access.
            System.out.println("Received SQL Query: " + request.getSql());

            // Simulate the result rows
            List<String> resultRows = new ArrayList<>();
            resultRows.add("Result row 1 for query: " + request.getSql());
            resultRows.add("Result row 2 for query: " + request.getSql());

            Dbserver.QueryResponse response = Dbserver.QueryResponse.newBuilder()
                    .addAllRows(resultRows)
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }

        @Override
        public void getUser(Dbserver.User request, StreamObserver<Dbserver.User> responseObserver) {
            // Simulate user retrieval. In a real-world scenario, this would involve database access.
            System.out.println("Retrieving user with ID: " + request.getId());

            Dbserver.User user = Dbserver.User.newBuilder()
                    .setId(request.getId())
                    .setName("John Doe")
                    .setEmail("john.doe@example.com")
                    .build();

            responseObserver.onNext(user);
            responseObserver.onCompleted();
        }
    }
}

