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
import { ClusterDescriptionModel } from '../models/cluster-description.model';
import { ClusterLogModel } from '../models/server-log.model';
import { ClusterParameterModel } from '../models/server-parameter.model';

@Injectable({
  providedIn: 'root',
})
export class ClusterApiService {

  private http: HttpClient;

  constructor(http: HttpClient) {
    this.http = http;
  }

  get(): Promise<ClusterDescriptionModel> {
    return this.http.get<ClusterDescriptionModel>('/api/cluster').toPromise();
  }

  getParameters(): Promise<ClusterParameterModel> {
    return this.http.get<ClusterParameterModel>('/api/cluster/parameters').toPromise();
  }

  getLogs(): Promise<ClusterLogModel> {
    return this.http.get<ClusterLogModel>('/api/cluster/logs').toPromise();
  }
}
