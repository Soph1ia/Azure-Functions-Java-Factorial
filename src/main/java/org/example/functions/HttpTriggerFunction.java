package org.example.functions;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.sun.media.jfxmedia.logging.Logger;
import org.openjdk.jmh.runner.RunnerException;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class HttpTriggerFunction {
    /**
     * This function listens at endpoint "/api/HttpExample". Two ways to invoke it using "curl" command in bash:
     * 1. curl -d "HTTP Body" {your host}/api/HttpExample
     * 2. curl "{your host}/api/HttpExample?name=HTTP%20Query"
     */
    @FunctionName("HttpExample")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET, HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
                    HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        // Parse query parameter
        final String number_query = request.getQueryParameters().get("number");
        PrintStream out = null;

        try {
//            out = new PrintStream(new FileOutputStream("C:/Users/sophi/downloads/output.txt")); // Use this for local development
            out = new PrintStream(new FileOutputStream("D:/home/output.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.setOut(out);

        if (number_query == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST).body("Please pass a number on the query string or in the request body").build();
        } else {
            BenchMark bm = new BenchMark();
            BenchMark.MyValues.number = number_query;
            try {
                bm.main();
            } catch (RunnerException e) {
                e.printStackTrace();
            }
            return request.createResponseBuilder(HttpStatus.OK).body("The number for factorial calculation provided is: " + number_query).build();
        }
    }
}
