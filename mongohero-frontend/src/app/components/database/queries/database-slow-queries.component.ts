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

import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { DatabaseModel } from '../../../models/database.model';
import { DatabaseApiService } from '../../../api/database.api.service';
import { ProfileQueryModel } from '../../../models/profile-query.model';

@Component({
  selector: 'app-database-slow-queries',
  templateUrl: './database-slow-queries.component.html',
  styleUrls: [
    './database-slow-queries.component.scss',
  ],
})
export class DatabaseSlowQueriesComponent implements OnInit, OnChanges {

  @Input() database: DatabaseModel;

  private databaseApiService: DatabaseApiService;

  sortField: string;
  sortOrder: string;
  queries: ProfileQueryModel[];

  constructor(databaseApiService: DatabaseApiService) {
    this.databaseApiService = databaseApiService;
    this.sortField = 'millis';
    this.sortOrder = '-';
  }

  ngOnInit(): void {
    this._fetchSlowQueries();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.database && !changes.database.isFirstChange()) {
      this._fetchSlowQueries();
    }
  }

  sort(field) {
    if (this.sortField === field) {
      this.sortOrder = this.sortOrder === '+' ? '-' : '+';
    } else {
      this.sortOrder = '+';
    }

    this.sortField = field;
    this._fetchSlowQueries();
  }

  private _fetchSlowQueries() {
    if (this.database) {
      const db = this.database.name;
      const sort = `${this.sortOrder}${this.sortField}`;
      this.databaseApiService.getProfilingQueries(db, sort).then((queries) => (
        this.queries = queries
      ));
    }
  }
}
