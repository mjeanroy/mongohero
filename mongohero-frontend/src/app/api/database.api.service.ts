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
import { HttpClient, HttpResponse } from '@angular/common/http';
import { DatabaseModel } from '../models/database.model';
import { CollectionModel } from '../models/collection.model';
import { ProfileQueryModel } from '../models/profile-query.model';
import { PageModel } from '../models/page.model';
import { ProfilingStatusModel } from '../models/profiling-status.model';

@Injectable({
  providedIn: 'root',
})
export class DatabaseApiService {

  private http: HttpClient;

  constructor(http: HttpClient) {
    this.http = http;
  }

  getAll(): Promise<DatabaseModel[]> {
    return this.http.get<DatabaseModel[]>('/api/databases').toPromise();
  }

  get(db: string): Promise<DatabaseModel> {
    return this.http.get<DatabaseModel>(`/api/databases/${db}`).toPromise();
  }

  getProfilingStatus(db: string): Promise<ProfilingStatusModel> {
    return this.http.get<ProfilingStatusModel>(`/api/databases/${db}/profiling/status`).toPromise();
  }

  updateProfilingStatus(db: string, profilingStatus: ProfilingStatusModel): Promise<ProfilingStatusModel> {
    return this.http.put<ProfilingStatusModel>(`/api/databases/${db}/profiling/status`, profilingStatus).toPromise();
  }

  getProfilingQueries(db: string, filters: object, page: number, sort: string = '-millis'): Promise<PageModel<ProfileQueryModel>> {
    const queryFilters = Object.keys(filters)
      .filter((key) => !!filters[key])
      .map((key) => `${key}=${filters[key]}`)
      .join('&');

    const queryString = `page=${page}&sort=${sort}` + (queryFilters ? `&${queryFilters}` : '');

    return this.http.get<ProfileQueryModel[]>(`/api/databases/${db}/profiling/queries?${queryString}`, {observe: 'response'})
      .toPromise()
      .then((response) => ({
        results: response.body,
        page: Number(response.headers.get('X-Page')),
        pageSize: Number(response.headers.get('X-Page-Size')),
        total: Number(response.headers.get('X-Total'))
      }));
  }

  resetProfilingQueries(db: string): Promise<void> {
    return this.http.delete<void>(`/api/databases/${db}/profiling/queries`).toPromise();
  }
}
