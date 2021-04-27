package org.example.functions;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.*;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

@BenchmarkMode(Mode.Throughput)

@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class BenchMark {

    @State(Scope.Thread)
    public static class MyValues {
        public static final Logger logger = Logger.getLogger(BenchMark.class.getName());
        public static String number;
    }

    @Benchmark
    @Fork(0)
    public void factorial(Blackhole bh) {
        BigInteger result = factorial(new BigInteger(MyValues.number));
        bh.consume(result);
    }

    private BigInteger factorial(BigInteger num) {
        BigInteger total;
        if (num.equals(new BigInteger("1"))) {
            return new BigInteger("1");
        } else {
            total = num.multiply(factorial(num.subtract(new BigInteger("1"))));
        }
        return total;
    }

    public void main() throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(BenchMark.class.getSimpleName())
                .warmupIterations(10)
                .measurementIterations(20)
                .build();
        new Runner(opt).run();

        // prints out the results to console.
        MyValues.logger.log(Level.INFO, "RESULTS_OF_BENCHMARK");
        MyValues.logger.log(Level.INFO, " The Factorial benchmark has run " );
    }

/*    public void read_and_write() throws IOException {
        MyValues.logger.log(Level.INFO, " Beginning to read from console");

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        BufferedWriter out = new BufferedWriter(new FileWriter("/output.txt"));
        try {
            String inputLine = null;
            do {
                inputLine=in.readLine();
                MyValues.logger.log(Level.INFO,"The current input line is --> " + inputLine);
                out.write(inputLine);
                out.newLine();
            } while (!inputLine.contains("INFO:  The Factorial benchmark has run"));
            System.out.print("Write Successful");
        } catch(IOException e1) {
            System.out.println("Error during reading/writing");
        } finally {
            out.close();
            in.close();
        }
    }*/
}