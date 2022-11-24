/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.avro.tool;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestIdlToSchemataTool {

  @Test
  public void testSplitIdlIntoSchemata() throws Exception {
    String idl = "src/test/idl/protocol.avdl";
    String outdir = "target/test-split";

    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    List<String> arglist = Arrays.asList(idl, outdir);
    new IdlToSchemataTool().run(null, null, new PrintStream(buffer), arglist);

    String[] files = new File(outdir).list();
    assertEquals(4, files.length);

    String warnings = readPrintStreamBuffer(buffer);
    assertEquals("Warning: Line 1, char 1: Ignoring out-of-place documentation comment."
        + "\nDid you mean to use a multiline comment ( /* ... */ ) instead?", warnings);
  }

  private String readPrintStreamBuffer(ByteArrayOutputStream buffer) {
    BufferedReader reader = new BufferedReader(
        new InputStreamReader(new ByteArrayInputStream(buffer.toByteArray()), Charset.defaultCharset()));
    return reader.lines().collect(Collectors.joining("\n"));
  }
}
