package com.dkay229.msql.server;




import com.dkay229.msql.proto.DatabaseServiceGrpc;
import io.grpc.Server;


import com.dkay229.msql.proto.Dbserver;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

        @Override
        public void executeQuery(Dbserver.QueryRequest request, StreamObserver<Dbserver.QueryResponse> responseObserver) {
            // Simulate a database query. In a real-world scenario, this would involve database access.
            log.info("Received SQL Query: " + request.getSql());

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
            log.info("Retrieving user with ID: " + request.getId());

            Dbserver.User user = Dbserver.User.newBuilder()
                    .setId(request.getId())
                    .setName("John Doe")
                    .setEmail("john.doe@example.com")
                    .build();

            responseObserver.onNext(user);
            responseObserver.onCompleted();
        }

        @Override
        public void getRandomSampleMessage(Dbserver.SampleRequest request, StreamObserver<Dbserver.SampleMessage> responseObserver) {
            log.info("Received request for a random sample message");
            Random random = new Random();
            Dbserver.SampleMessage sampleMessage = Dbserver.SampleMessage.newBuilder()
                    .setMyNestedMessage(Dbserver.SampleMessage.NestedMessage.newBuilder()
                            .setNestedField("nested field")
                    )
                    .setMyDouble(random.nextDouble())
                    .setMyInt32(random.nextInt())
                    .setMyFixed64(random.nextLong())
                    .setMyEnum(Dbserver.SampleMessage.MyEnum.OPTION_TWO)
                    .addAllMyRepeatedInt32(Arrays.asList(random.nextInt(), random.nextInt(), random.nextInt()))
                    .addAllMyRepeatedString(Arrays.asList("string 1", "string 2", "string 3"))
                    .setMyString("random string")
                    .build();
            responseObserver.onNext(sampleMessage);
            responseObserver.onCompleted();




        }
    }
}

