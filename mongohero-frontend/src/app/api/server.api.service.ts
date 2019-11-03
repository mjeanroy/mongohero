/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Mickael Jeanroy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ServerModel } from '../models/server.model';
import { ProfilingStatusModel } from '../models/profiling-status.model';
import { ServerParameterModel } from '../models/server-parameter.model';

@Injectable({
  providedIn: 'root',
})
export class ServerApiService {

  private http: HttpClient;

  constructor(http: HttpClient) {
    this.http = http;
  }

  get(): Promise<ServerModel> {
    return this.http.get<ServerModel>('/api/server').toPromise();
  }

  getLogs(): Promise<string[]> {
    return this.http.get<string[]>('/api/server/log').toPromise();
  }

  getParameters(): Promise<ServerParameterModel[]> {
    return this.http.get<ServerParameterModel[]>('/api/server/parameters').toPromise();
  }

  updateProfilingStatus(profilingStatus: ProfilingStatusModel): Promise<ProfilingStatusModel> {
    return this.http.put<ProfilingStatusModel>('/api/profiling/status', profilingStatus).toPromise();
  }
}
