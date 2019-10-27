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
import { DatabaseModel } from '../models/database.model';
import { CollectionModel } from '../models/collection.model';
import { ProfileQueryModel } from '../models/profile-query.model';

@Injectable({
  providedIn: 'root',
})
export class DatabaseApiService {

  private http: HttpClient;

  constructor(http: HttpClient) {
    this.http = http;
  }

  get(db: string): Promise<DatabaseModel> {
    return this.http.get<DatabaseModel>(`/api/databases/${db}`).toPromise();
  }

  getProfilingQueries(db: string, sort: string = '-millis'): Promise<ProfileQueryModel[]> {
    return this.http.get<ProfileQueryModel[]>(`/api/databases/${db}/profiling/queries?sort=${sort}`).toPromise();
  }
}
