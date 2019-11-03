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
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DatabaseModel } from '../../../models/database.model';
import { DatabaseApiService } from '../../../api/database.api.service';
import { ProfileQueryModel } from '../../../models/profile-query.model';
import { PageModel } from '../../../models/page.model';
import { ProfilingStatusModel } from '../../../models/profiling-status.model';
import { ProfilingStatusModalComponent } from './profiling-status-modal.component';

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
  private modalService: NgbModal;

  profilingStatus: ProfilingStatusModel;
  page: number;
  sortField: string;
  sortOrder: string;
  queries: PageModel<ProfileQueryModel>;

  constructor(
    databaseApiService: DatabaseApiService,
    modalService: NgbModal) {

    this.databaseApiService = databaseApiService;
    this.modalService = modalService;

    this.profilingStatus = null;
    this.queries = null;
    this.sortField = 'millis';
    this.sortOrder = '-';
    this.page = 1;
  }

  ngOnInit() {
    if (this.database) {
      this._fetchSlowQueries();
      this._fetchProfilingStatus();
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.database && !changes.database.isFirstChange()) {
      this._fetchSlowQueries();
      this._fetchProfilingStatus();
    }
  }

  openProfilingStatusModal() {
    const modalRef = this.modalService.open(ProfilingStatusModalComponent, {
      size: 'lg',
    });

    modalRef.componentInstance.profilingStatus = Object.assign({}, this.profilingStatus);
    modalRef.componentInstance.database = this.database;
    modalRef.result
      .then((profilingStatus) => this.updateProfilingStatus(profilingStatus))
      .catch(() => {});
  }

  reset() {
    this.databaseApiService.resetProfilingQueries(this.database.name).then(() => {
      this.sync();
    });
  }

  sync() {
    this.queries = null;
    this._fetchSlowQueries();
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

  goTo(page: number) {
    this.page = page;
    this._fetchSlowQueries();
  }

  getLevel(query: ProfileQueryModel) {
    if (query.millis > 100) {
      return 'danger';
    }

    if (query.millis > 50) {
      return 'warning';
    }

    return 'body';
  }

  updateProfilingStatus(profilingStatus: ProfilingStatusModel) {
    this.profilingStatus = profilingStatus;
  }

  private _fetchProfilingStatus() {
    this.databaseApiService.getProfilingStatus(this.database.name).then((profilingStatus) => (
      this.profilingStatus = profilingStatus
    ));
  }

  private _fetchSlowQueries() {
    const db = this.database.name;
    const sort = `${this.sortOrder}${this.sortField}`;
    const currentPage = this.page;
    this.databaseApiService.getProfilingQueries(db, currentPage, sort).then((pageResults) => (
      this.queries = pageResults
    ));
  }
}
