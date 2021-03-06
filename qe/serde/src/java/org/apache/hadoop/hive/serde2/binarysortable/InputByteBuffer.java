/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hive.serde2.binarysortable;

import java.io.EOFException;
import java.io.IOException;

public class InputByteBuffer {

  byte[] data;
  int start;
  int end;

  public void reset(byte[] data, int start, int end) {
    this.data = data;
    this.start = start;
    this.end = end;
  }

  public final byte read() throws IOException {
    return read(false);
  }

  public final byte read(boolean invert) throws IOException {
    if (start >= end) {
      throw new EOFException();
    }
    if (invert) {
      return (byte) (0xff ^ data[start++]);
    } else {
      return data[start++];
    }
  }

  public final int tell() {
    return start;
  }

  public final void seek(int position) {
    start = position;
  }

  public final int getEnd() {
    return end;
  }

  public final byte[] getData() {
    return data;
  }

  public String dumpHex() {
    StringBuilder sb = new StringBuilder();
    for (int i = start; i < end; i++) {
      byte b = data[i];
      int v = (b < 0 ? 256 + b : b);
      sb.append(String.format("x%02x", v));
    }
    return sb.toString();
  }
}
