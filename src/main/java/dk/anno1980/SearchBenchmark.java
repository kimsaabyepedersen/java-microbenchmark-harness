/*
 * Copyright (c) 2014, Oracle America, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of Oracle nor the names of its contributors may be used
 *    to endorse or promote products derived from this software without
 *    specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package dk.anno1980;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

// how much warmup should be done, a warm JVM behaves differently than a cold
@Warmup(iterations = 3, time = 10)
// how much measurement should be done
@Measurement(iterations = 3, time = 10)
// should multiple JVM forks be used
@Fork(5)
// timeunit results are presented in
@OutputTimeUnit(TimeUnit.MILLISECONDS)
// what to measure, other modes include avg. time per operation
@BenchmarkMode({Mode.Throughput})
// how to treat the state in the benchmark (especially important with the -t parameter)
@State(Scope.Thread)
public class SearchBenchmark {

  // the size of the arrays to sort, generated in setup
  // the @Benchmark methods will run once per element in this array
  @Param({"1000", "1000000"})
  int size;

  // the generated array
  int[] array;
  // a random int to find in the array
  int toFind;

  @Setup
  public void setup() {
    // generate data in setup, to avoid constant folding
    // (as opposed to having it generated once and reused, a clever compiler might recognize that)
    RandomDataGenerator randomDataGenerator = new RandomDataGenerator();
    array = randomDataGenerator.nextPermutation(size * 10, size);
    Arrays.sort(array);
    toFind = randomDataGenerator.nextInt(0, size * 10);
  }

  @Benchmark
  public int binarySearch() {
    // make sure to return the result, to avoid dead code elimination
    return BinarySearch.find(array, toFind);
  }

  @Benchmark
  public int linearSearch() {
    // make sure to return the result, to avoid dead code elimination
    return LinearSearch.find(array, toFind);
  }

}
